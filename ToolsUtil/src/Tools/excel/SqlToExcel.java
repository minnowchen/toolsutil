package Tools.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Tools.Data.DateUtil;
import Tools.SQL.SQLTools;


public class SqlToExcel
{
	Workbook wb;
	
	public static void main(String[] args)
	{
		
		//EXCEL 欄位標題
		ArrayList<String> headerS = new ArrayList<>(
				Arrays.asList("itv_id","scd_id","year","End cier_code","scd_name"));
		//SQL搜尋字串
		String sql = "SELECT itv.id AS itv_id, scd.id AS scd_id, itv.year, itv.cier_code, scd.scd_name "
				+	 "FROM TB_Interview itv "
				+ 	 "LEFT OUTER JOIN TB_Schedule scd ON scd.interview_id = itv.id "
				+ 	 "WHERE itv.year = 107 "
				+ 	 "AND scd.id IS NOT NULL " ;
		//SQL連線設定
		String ip = "192.168.2.20";//"192.168.2.23";
		String user = "sa";
		String password = "Ibtech.9548";//"Ibtech.9548";
		String database = "CIER_Interview";
		String url = "jdbc:sqlserver://"+ip+":1433;"
				   + "databaseName="+database+";"
				   + "user="+user+";"
				   + "password="+password+";"
				   + "SelectMethod=cursor";
		System.out.println(url);
		
		SqlToExcel se = new SqlToExcel();
		ArrayList<Map<Integer, String>> result = se.getSQL(sql, url);
		if(result == null) {
			System.out.println("並無符合SQL條件的內容");
			return;
		}
		System.out.println(result.get(0));
		se.getSQLtoExcel(result, headerS);
	}

	
	private void getSQLtoExcel(ArrayList<Map<Integer, String>> result, ArrayList<String> headerS)
	{
		/* 1.產生一個EXCEL ====================================================================================*/
		wb = new XSSFWorkbook();
		
		/* 2.產生N個sheet ===================================================================================*/
		/* Workbook 是個Object，傳進去操作時是改變整個Object，所以也可以回傳，也可以「不回傳」，
		 * 因為Object已經整個改變了，所以也不需要用原來的wb物件承接
		 * 基本型態的變數就需要 接回回傳的值，才能改變基本型態變數的值
		 */	
		goToSheet(result, headerS);
		System.out.println("開始輸出檔案");

		
		
		/* 2.檔名＆輸出  ==================================================================================*/
		FileOutputStream out = null;
		try {
			
			//輸出格式 CSV檔  或者  EXCEL檔
			String format = "csv"; // csv or xlsx
			out = new FileOutputStream(new File("D:/"+DateUtil.dateToChangeROC(new Date(), "yyyyMMdd", "CH")+"."+format));
			
			//輸出：CSV檔  或者  EXCEL檔
			if("csv".equalsIgnoreCase(format)) {
				CSVTools csv = new CSVTools(wb);
				csv.write(out);
			}else {
				wb.write(out);
				wb.close();
			}
			
			System.out.println("輸出完畢");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out!=null){
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private void goToSheet(ArrayList<Map<Integer, String>> result, ArrayList<String> headerS)
	{
		
		/* 0.設定 字型、欄位背景色、框線色、儲存格格式 ============================================================================*/
		/* 設定欄位字型Font  - (自寫method) setFont(int style, Workbook wb, int font_size, String font_name); */
		Font font_h = ExcelUtil.setFont("header", wb, 12, "微軟正黑體"); 
		Font font = ExcelUtil.setFont("data", wb, 12, "微軟正黑體");		
		/* 設定欄位格式Cellstyle - (自寫method) setCellStyle(style, wb, cellColor, font, borderStyle, borderColor); */
		CellStyle style_h = ExcelUtil.setCellStyle("left", wb, null, font_h, "", null); //白底黑字
		CellStyle style   = ExcelUtil.setCellStyle("left", wb, null, font, "", null); //白底黑字
		
		
		/* 1-1.建立工作表 ===============================================================================*/
		Sheet sheet = wb.createSheet("工作表");
		int rowNum = 0;
		int startIndex = -1;
		int colNum = 0;
		
		/* 2.標題 ======================================================================================*/
		Row row_h = sheet.createRow(rowNum++);
		colNum = startIndex + 1;
		for(String header : headerS) {
			ExcelUtil.createCell("str", colNum++, row_h, style_h, header);
		}
		
		/* 3.內容： =====================================================================================*/
		Row row = null;
		for(Map<Integer, String> map : result) {
			
			row = sheet.createRow(rowNum++);
			colNum = startIndex + 1;
			for(int key : map.keySet()) {
				ExcelUtil.createCell("str", colNum++, row, style, map.get(key));
			}	
			
		}//end for result
		
		
		
		
		/* 4.處理格式設定 ========================================================================*/
		//凍結列 int colSplit, int rowSplit：左側從第0開始切割，上方從地2列開始切割 （index從0開始）
		sheet.createFreezePane(0, 1);
		
		/* 調整欄寬：取標題欄的欄位數，一個一個autoSize，再設定成autoSize後的兩倍寬（因為中文字是兩倍）
		 */
		for(int i = 0;i < row_h.getLastCellNum();i++){
//			if(i == 0) {
//				sheet.setColumnWidth(i, 6*256);
//			}else {
//				sheet.autoSizeColumn(i);
				sheet.setColumnWidth(i, sheet.getColumnWidth(i)*2);
				//sheet.setColumnWidth(i, Math.min(sheet.getColumnWidth(i)*1, 60*256)); //欄寬1倍 或 欄寬60 取最小
//				sheet.setColumnWidth(i, 15*256);
//			}
		}
		
		/* 設定列高 */
		row_h.setHeightInPoints((short)25);	
		
	}

	private ArrayList<Map<Integer, String>> getSQL(String sql, String url)
	{
		ArrayList<Map<Integer, String>> result = null;
		SQLTools sqltools = new SQLTools(SQLTools.SQLServer, url);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = sqltools.prepare(sql);
			rs = pstmt.executeQuery();
			result = new ArrayList<>();
			while(rs.next()) {
				ResultSetMetaData rsMetaData = rs.getMetaData();
				int numberOfColumns = rsMetaData.getColumnCount();
				Map<Integer, String> map = new TreeMap<>();
				for(int i=1; i<=numberOfColumns; i++) {
					map.put(i, rs.getString(i));
				}
				result.add(map);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			try {
				sqltools.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		
		}
		return result;
	}

}
