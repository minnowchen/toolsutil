package Tools.excel;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import Tools.Data.ToolsUtil;


public class ExcelUtil
{
	
	/* poi-3.16 (1070322 目前改為3.17)
	 * 1.設定標題字型 setFont
	 * 
	 * 2.設定儲存格格式  setCellStyle
	 * 3.產生儲存格放值  createCell
	 * 4.取得儲存格內容 getCellValue
	 * 5.在已存在的儲存格，放入內容 reSetCellValue
	 * 6.在合併的儲存格設定框線顏色 setMerginRegion
	 * 7.替數字欄位style 設定小數點、三位一撇 setCellStyle_NumFormat(wb, cellStyle, "##0");
	 * */
	
	/* 設定標題字型 ------------------------------------------------------------------------------------------
	 * 字型顏色  
	 * IndexedColors.BLACK.getIndex()  old-HSSFColor.BLACK.index (黑色)
	 * IndexedColors.WHITE.getIndex()  old-HSSFColor.WHITE.index （白色）
	 * IndexedColors.RED.getIndex()    old-HSSFColor.RED.index （紅色）     */
	public static Font setFont(String style, Workbook wb, int font_size, String font_name, short color){

		Font font = wb.createFont();
		font.setFontHeightInPoints((short) font_size);   //設定字體大小
		font.setFontName(font_name);

		/* 標題通常粗體 */
		if("header".equalsIgnoreCase(style)){
			//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗體
			font.setBold(true);
		}
		
		/* 內容通常有特定字型，不粗體 */
		else if("data".equalsIgnoreCase(style)){
		}
		
		/* 設定字型顏色 */
		font.setColor(color);
		
		return font;
	}
	
	public static Font setFont(String style, Workbook wb, int font_size, String font_name){

		Font font = wb.createFont();
		font.setFontHeightInPoints((short) font_size);   //設定字體大小
		font.setFontName(font_name);

		/* 標題通常粗體 */
		if("header".equalsIgnoreCase(style)){
			//font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗體
			font.setBold(true);
		}
		
		/* 內容通常有特定字型，不粗體 */
		else if("data".equalsIgnoreCase(style)){
		}
		
		return font;
	}
	
	
	/* 設定style */
	public static XSSFCellStyle setCellStyle(String style, Workbook wb, Map<String, Integer> cellColor, Font font, 
				String borderStyle, Map<String, Integer> borderColor)
	{
		XSSFCellStyle cellStyle = (XSSFCellStyle)(wb.createCellStyle());
		cellStyle.setFont(font); // 設定字體
		
		
		/* 靠左  換行 */
		if("leftWrap".equalsIgnoreCase(style)){
		  //cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER); //(old 3.14) 垂直置中 + 靠左對齊
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
			cellStyle.setWrapText(true); // 自動換行
		}
		/* 靠左  不換行 */
		if("left".equalsIgnoreCase(style)){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
		}
		/* 靠左  不換行 置頂 */
		if("leftWrapTop".equalsIgnoreCase(style)){
			cellStyle.setVerticalAlignment(VerticalAlignment.TOP); //水平置頂
			cellStyle.setWrapText(true); // 自動換行
		}
		
		/* 置中 換行*/
		if("centerWrap".equalsIgnoreCase(style)){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); //水平置中
		  //cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); //(old 3.14) 水平置中
			cellStyle.setWrapText(true); // 自動換行
		}
		
		/* 置中 */
		if("center".equalsIgnoreCase(style)){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
			cellStyle.setAlignment(HorizontalAlignment.CENTER); //水平置中
		}

		
		/* 靠右  換行 */
		if("rightWrap".equalsIgnoreCase(style)){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
			cellStyle.setAlignment(HorizontalAlignment.RIGHT); //水平靠右
			cellStyle.setWrapText(true); // 自動換行
		}
		
		/* 靠右 */
		if("right".equalsIgnoreCase(style)){
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); //垂直置中
			cellStyle.setAlignment(HorizontalAlignment.RIGHT); //水平靠右
		}
		
		/* 縮排 */
//		cellStyle.setIndention(indent); //縮排
		
		
		/* 背景色 ================================================================================================= 
		 * 淺藍 - 218,238,243
		 * 水藍 - 130,195,213 
		 * 藍綠 - 49,134,155
		 * 草綠 - 139,207,146
		 * 深藍 - 54,96,146
		 * */
		if(cellColor != null){
			int cell_r = cellColor.get("r");
			int cell_g = cellColor.get("g");
			int cell_b = cellColor.get("b");
		  //cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//(old)設置可填充儲存格底色
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);//設置可填充儲存格底色
			cellStyle.setFillForegroundColor(new XSSFColor( new Color(cell_r, cell_g, cell_b))); 
			/* 設定背景色：想要自訂顏色，就必須將CellStyle轉型成 XSSFCellStyle 才可以自訂顏色
			 * 原版寫法[HSSFColor.DARK_TEAL.index]   另一寫法[new XSSFColor( new Color(r, g, b))] 
			 */
		}
		
		/* 框線顏色 ================================================================================================
		 * 灰色-191,191,191
		 * 黑色-0,0,0
		 * */
		if(borderColor != null){
			
			int border_r = borderColor.get("r");
			int border_g = borderColor.get("g");
			int border_b = borderColor.get("b");
			Color color = new Color(border_r, border_g, border_b);
			XSSFColor xssfColor = new XSSFColor(color);
			cellStyle.setTopBorderColor(xssfColor);
			cellStyle.setBottomBorderColor(xssfColor);
			cellStyle.setLeftBorderColor(xssfColor);
			cellStyle.setRightBorderColor(xssfColor); //208, 206, 206
		}
		
		if("thin".equalsIgnoreCase(borderStyle)){
		  //cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);  //(old)儲存格外框 - 上方
			cellStyle.setBorderTop(BorderStyle.THIN);  //儲存格外框 - 上方
			cellStyle.setBorderBottom(BorderStyle.THIN); //儲存格外框 - 底線
			cellStyle.setBorderLeft(BorderStyle.THIN);   //儲存格外框 - 左側
			cellStyle.setBorderRight(BorderStyle.THIN);  //儲存格外框 - 右側

		}else if("thick".equals(borderStyle)){
			cellStyle.setBorderTop(BorderStyle.THICK);  //儲存格外框 - 上方
			cellStyle.setBorderBottom(BorderStyle.THICK); //儲存格外框 - 底線
			cellStyle.setBorderLeft(BorderStyle.THICK);   //儲存格外框 - 左側
			cellStyle.setBorderRight(BorderStyle.THICK);  //儲存格外框 - 右側
		}
		else if("thick-bottom".equalsIgnoreCase(borderStyle)){ //底線加粗
			cellStyle.setBorderTop(BorderStyle.THIN);  //儲存格外框 - 上方
			cellStyle.setBorderBottom(BorderStyle.THICK); //儲存格外框 - 底線
			cellStyle.setBorderLeft(BorderStyle.THIN);   //儲存格外框 - 左側
			cellStyle.setBorderRight(BorderStyle.THIN);  //儲存格外框 - 右側
		}
		
		return cellStyle;
	}
	
	
	/* 產生儲存格 */
	public static void createCell(String style, int colNum, Row row, CellStyle cellStyle, Object value){

		if("str".equalsIgnoreCase(style)){ //0
		  //Cell cell = row.createCell(colNum, Cell.CELL_TYPE_STRING); //(old 3.14)
		  //cell.setCellType(Cell.CELL_TYPE_STRING); //(old 3.14)
			Cell cell = row.createCell(colNum, CellType.STRING);
			cellStyle.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text")); //加這個會讓儲存格變文字
			cell.setCellStyle(cellStyle);
			cell.setCellValue(String.valueOf(value==null ? "" : value));
		}
		else if("num".equalsIgnoreCase(style)){ //1
		  //Cell cell = row.createCell(colNum, Cell.CELL_TYPE_NUMERIC);
			Cell cell = row.createCell(colNum, CellType.NUMERIC);
			cell.setCellStyle(cellStyle);
			if(value instanceof Integer) {
				cell.setCellValue((Integer)(value==null ? 0 : value));
			}else if(value instanceof Long) {
				cell.setCellValue((Long)(value==null ? 0 : value));
			}
		}
		else if("num_acount".equalsIgnoreCase(style)){ //2
		  //Cell cell = row.createCell(colNum, Cell.CELL_TYPE_STRING);
			Cell cell = row.createCell(colNum, CellType.STRING);
			cell.setCellStyle(cellStyle);
			
			/* value可能是String，也可能是double
			 * 廠商管理 - 資本總額&實收資本額（String），有可能輸入','在字串內，所以要replace
			 * 訪視清單 - 投資金額_台幣  & 投資金額_美金（double），所以可以不用轉String
			 * 但因為有兩種型態所以還是先轉String，再轉Double */
			if(value != null && String.valueOf(value).trim().length() != 0){
				cell.setCellValue(
					ToolsUtil.parseNumToFinancial(
							Double.valueOf( String.valueOf(value).replace(",", "") ) , "###,###,##0.0###")  );
			}else{
				cell.setCellValue("");
			}
			
		}
	}

	/**
	 * 讓儲存格裡的某些字，有特定顏色 
	 * @param colNum    儲存格欄index 
	 * @param row       儲存格列index
	 * @param cellStyle 儲存格格式設定
	 * @param valueS    儲存格顯示資料
	 * @param fonts     要變換特定顏色的內容 [文字, 字體顏色]
	 */
	public static void createCell_RichText(int colNum, Row row, CellStyle cellStyle, String valueS, Map<String, Font> fonts){
//		Cell cell = row.createCell(colNum, Cell.CELL_TYPE_STRING); //(old 3.14)
//		cell.setCellType(Cell.CELL_TYPE_STRING); //(old 3.14)
		Cell cell = row.createCell(colNum, CellType.STRING); //(3.17)
		
		cellStyle.setDataFormat((short)BuiltinFormats.getBuiltinFormat("text")); //加這個會讓儲存格變文字
		cell.setCellStyle(cellStyle);
		
		/* MAP[人名, 字體顏色]
		 * 從字串"王王王\r\n金金金\r\n千千千\r\n吳吳吳"
		 * 找出每個符合key的起始,結束index，將key這個字串變成map裡指定的value字體顏色，其餘的字體顏色就依照cellStyle顯示
		 * key是人名。 
		 */
		
		XSSFRichTextString textString = new XSSFRichTextString(valueS);
		//先全部套用儲存格設定的字體跟顏色
		textString.applyFont(cellStyle.getFontIndex());
		//再個別設定特殊字的字體顏色
		for(String key : fonts.keySet()) { 
			int startIndex = valueS.indexOf(key);
			int endIndex = startIndex + key.length(); //不減1
			textString.applyFont(startIndex, endIndex, fonts.get(key));
		}
		cell.setCellValue(textString);
		
	}
	
	/* 取得儲存格內容 
	 * 忠實呈現，如果沒有小數點就也不會出現小數點，有錢字號也會出現。
	 * （遇到 double=81019910  不會變成   8.101991E7）
	 * */
	public static String getCellValue_DataFormat(Row row, int cellNum){
		
		String cellValue = "";
		DataFormatter formatter = new DataFormatter();
		if(row.getCell(cellNum) != null){
			cellValue = formatter.formatCellValue(row.getCell(cellNum));
		}
		
		return cellValue;
	}
	
	/* 取得儲存格內容 */
	public static Object getCellValue(Row row, int cellNum){
		Object cellValue = null;
		
		if(row.getCell(cellNum) != null){
			
			//如果所有欄位都是要以String取出再轉換型態，那就強制setType
			//row.getCell(cellNum).setCellType(Cell.CELL_TYPE_STRING);
			//row.getCell(cellNum).getCellTypeEnum(CellType.STRING);
			
			//如果上方有強制setType 成 String，就不會判斷到是數字的欄位了
		  //if(row.getCell(cellNum).getCellType() == Cell.CELL_TYPE_NUMERIC){ //(old 3.14)
			if(row.getCell(cellNum).getCellTypeEnum() == CellType.NUMERIC){
				cellValue = row.getCell(cellNum).getNumericCellValue(); //回傳的是double
				/* 若double數字過大，會變成數學符號 ex: 81019910 --> 8.101991E7
				 * 取出後要做判斷如下
				 * 
				 * if(cellValue instanceof Double) {
				 *		//八碼數字 轉回 double會變數學符號，例如   81019910 -> 8.101991E7，要用DataFormat轉型
				 *		HsCode = ToolsUtil.parseNumToFinancial(cellValue, "########0.##");
				 * }
				 */
			}
			
			else if(row.getCell(cellNum).getCellTypeEnum() == CellType.STRING){
				cellValue = ToolsUtil.trim(row.getCell(cellNum).getStringCellValue());
			}
		}else{
			cellValue = "";
		}
		
		return cellValue;
		
		/* 同以下寫法 */
//		return row.getCell( column_map.get(field) ) == null ? 
//				"" : 
//				row.getCell( column_map.get(field) ).getCellType() == Cell.CELL_TYPE_NUMERIC ? 
//						String.valueOf(row.getCell(0).getNumericCellValue()) :
//						ToolsUtil.trim(row.getCell(0).getStringCellValue());
	}
	
	
	
	//已產生的儲存格，要放值進去
	public static void reSetCellValue(Sheet sheet, int rowIndex, int cellIndex, CellStyle cellStyle, String style, Object obj)
	{
		/* row_index: A, B, C, 07月, 08月, 組別/人員
		 * col_index: 甲, 乙, 組別/人員, 史惠慈, 蘇筑瑄, 李文智, 陳澤嘉, 許茵爾
		 * Row valueRow = sheet.getRow(row_index.get("組別/人員"));
		 * int valueCell = col_index.get("史惠慈"); */
		Row row = sheet.getRow(rowIndex);
		Object num = getCellValue(row, cellIndex);
		
		//重新輸入 - 數字
		if("int".equalsIgnoreCase(style)){
			int add = (Integer)(obj==null ? 0 : obj);
			int callvalue_new = 0;
			int callvalue_old = 0;
			if(num instanceof Double){ //原本是double，包成了Double物件，必須先轉成double變數才能強制轉int
				callvalue_old = (int)( (double)num );
				
			}else if(num instanceof String){
				String value = (String)num;
				if( value.trim().length() == 0 ){
					callvalue_old = 0;
				}else{
					System.out.println("num有內容可是卻是字串 ="+num);
					//callvalue_old = Integer.valueOf( value.substring(0, value.indexOf(".")) );
				}
			}
			callvalue_new = callvalue_old + add;
			createCell("num", cellIndex, row, cellStyle, callvalue_new);
		}
		//重新輸入 - 字串
		else if("string".equalsIgnoreCase(style)){
			createCell("str", cellIndex, row, cellStyle, String.valueOf(obj));
		}
		
	}
	
	
	
	//設定框線顏色：用於合併儲存格時，必須標記範圍才能畫框線
	public static void setMerginRegion(CellStyle style, Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol)
	{
		for(int i = firstRow; i <= lastRow; i++ ){
			for(int j = firstCol; j <= lastCol; j++ ){
				if(i == firstRow && j == firstCol){
					continue;
				}
				Cell cell = sheet.getRow(i).createCell(j);
				cell.setCellStyle(style);
			}
		}
		
		CellRangeAddress mergeRange = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
		sheet.addMergedRegion(mergeRange);

		
		/* 2017-09-15 
		 * 原本設定是 先create第一個儲存格，之後設定範圍就合併儲存格，
		 * 
		 * 框線形狀顏色顏色統一用以下方法set進去，						--> RegionUtil.setBottomBorderColor()
		 * 但是因為RegionUtil的setcolor只吃 int(IndexedColors)；   --> IndexedColors.RED.getIndex() (得到10)  OK 
		 * 而自訂的XSSFColor 在getIndexed()是無效的；				--> new XSSFColor(Color.RED).getIndexed() (得到0)  NO
		 * 除非原本就是setIndexedColors。 	 					--> new XSSFColor(IndexedColors.RED).getIndexed() (得到10)  OK
		 * 
		 * 若一開始進來的CellStyle，裡面設定的Color就是給予IndexedColors.RED，就可以繼續用setBottomBorderColor的寫法
		 * 但是因為大多是自訂的自訂的XSSFColor，取不到Index，所以不能使用setBottomBorderColor，
		 * 改採用先create所有要合併的儲存格，然後再merge就可以採用原本每一格所設定的儲存格格式。
		 *  
		System.out.println(new XSSFColor(Color.RED).getIndexed()); // 0
		System.out.println(new XSSFColor(IndexedColors.RED).getIndexed()); // 10
		System.out.println(IndexedColors.RED.getIndex()); // 10 
		
		三種正常寫法：（若一開始進來的CellStyle，裡面設定的Color就是給予IndexedColors.RED，則以下三種寫法都可以使用）
		XSSFCellStyle XSSFcellStyle = (XSSFCellStyle)style;
		RegionUtil.setBottomBorderColor(style.getBottomBorderColor(), mergeRange, sheet); //poi-3.14
		RegionUtil.setBottomBorderColor(XSSFcellStyle.getBottomBorderXSSFColor().getIndex(), mergeRange, sheet); //poi-3.16
		RegionUtil.setBottomBorderColor(IndexedColors.RED.getIndex(), mergeRange, sheet); //poi-3.16
		*/

		/* 原本寫法
		RegionUtil.setBorderBottom(style.getBorderBottom(), mergeRange, sheet, sheet.getWorkbook());
		RegionUtil.setBorderLeft(style.getBorderLeft(), mergeRange, sheet, sheet.getWorkbook());
		RegionUtil.setBorderRight(style.getBorderRight(), mergeRange, sheet, sheet.getWorkbook());
		RegionUtil.setBorderTop(style.getBorderTop(), mergeRange, sheet, sheet.getWorkbook());
		
		RegionUtil.setBottomBorderColor(style.getBottomBorderColor(), mergeRange, sheet, sheet.getWorkbook());
		RegionUtil.setLeftBorderColor(style.getLeftBorderColor(), mergeRange, sheet, sheet.getWorkbook());
		RegionUtil.setRightBorderColor(style.getRightBorderColor(), mergeRange, sheet, sheet.getWorkbook());
		RegionUtil.setTopBorderColor(style.getTopBorderColor(), mergeRange, sheet, sheet.getWorkbook());
		*/
	}
	
	
	
	/*替數字欄位style 設定小數點、三位一撇 (pattern = "0.0"、 "#,##0.0000") */
	public static XSSFCellStyle setCellStyle_NumFormat(Workbook wb, CellStyle style, String pattern)
	{
		DataFormat format = wb.createDataFormat();
		XSSFCellStyle cellStyle = ((XSSFCellStyle)style);
		cellStyle.setDataFormat(format.getFormat(pattern));
		return cellStyle;
	}
	
	
	public static Workbook normalSheet(Workbook wb, String sheetName, String normalMessage)
	{
		/* 1.設定 字型、欄位背景色、框線色、CellStyle ====================================================================
		 *   設定欄位字型Font  - (自寫method) setFont(int style, Workbook wb, int font_size, String font_name); 
		 */
		Font font = ExcelUtil.setFont("header", wb, 12, "微軟正黑體"); 
		//背景米黃 255,251,225
		Map<String, Integer> yellow = new HashMap<>();
		yellow.put("r", 255);  yellow.put("g", 251);  yellow.put("b", 225);
		//框線顏色  - 灰色-164,164,164
		Map<String, Integer> grayLine = new HashMap<>();
		grayLine.put("r", 164);  grayLine.put("g", 164);  grayLine.put("b", 164);
		/* 設定欄位格式Cellstyle - (自寫method) setCellStyle(style, wb, cellColor, font, borderStyle, borderColor); */
		CellStyle style = ExcelUtil.setCellStyle("leftWrap", wb, yellow, font, "thin", grayLine); //黃底黑字
		
		
		/* 2.建立工作表 ===========================================================================================
		 */
		Sheet sheet = wb.createSheet(sheetName);
		int rowNum = 0;
		int startIndex = -1; //起始欄-1
		int colNum = 0;
		
		
		/* 3.標題 ===============================================================================================
		 */
		Row row_h = sheet.createRow(rowNum++);
		colNum = startIndex + 1;
		ExcelUtil.createCell("str", colNum++, row_h, style, normalMessage);
		
		
		/* 4.處理格式設定 =========================================================================================
		 */

		/* 凍結列 int colSplit, int rowSplit：左側從第0開始切割，上方從地2列開始切割 （index從0開始）
		 * sheet.createFreezePane(0, 1); 
		 */
		
		/* 調整欄寬：取標題欄的欄位數，一個一個autoSize，再設定成autoSize後的兩倍寬（因為中文字是兩倍）
		 * 	1.固定欄寬：sheet.setColumnWidth(i, 6*256);
		 * 	2.系統調整：sheet.autoSizeColumn(i); 
		 * 	3.指定調整：sheet.setColumnWidth(i, sheet.getColumnWidth(i)*2);
		 *  3.取最小值：sheet.setColumnWidth(i, Math.min(sheet.getColumnWidth(i)*1, 60*256)); //欄寬1倍 或 欄寬60 取最小
		 */
		for(int i = 0;i < row_h.getLastCellNum();i++){
			sheet.autoSizeColumn(i);
		}
		
		/* 設定列高 */
		row_h.setHeightInPoints((short)25);				
		
		return wb;
	}
	
	
	
	//Servlet 輸出
	public static void ServletWrite() {
		
	}
}
