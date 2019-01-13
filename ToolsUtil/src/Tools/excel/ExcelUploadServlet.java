package Tools.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import Tools.Data.ToolsUtil;


public class ExcelUploadServlet extends HttpServlet
{
	

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ArrayList<String> list = null;
		HashMap<String, Integer> headerIndex = new HashMap<String, Integer>();
		HashMap<String, String> parameters = new HashMap<>(); //會傳入Form表單input的所有值
		String resultStr = "";
		InputStream is = null;
		
		if(ServletFileUpload.isMultipartContent(request)){

			ServletFileUpload sfu = new ServletFileUpload();
			sfu.setHeaderEncoding("UTF-8");
			try {

				/* 開啟 inputStream */
				FileItemIterator iter = sfu.getItemIterator(request);	
				while (iter.hasNext()) {

					FileItemStream fis = iter.next();
					is = fis.openStream();
					
					/* Note: normal form fields MUST be placed before file input.
					 * 通常上傳file都會放在form的最後一個，這樣前面的所有欄位都是input，
					 * 就可以先取出所有form表單內的input資料，之後才else轉入file檔案處理
					 */
					if(fis.isFormField()){
						//Streams.asString(is, "utf-8");
						parameters.put(fis.getFieldName(), Streams.asString(is, "utf-8"));
						System.out.println(Streams.asString(is, "utf-8"));
					
					}else{
						
						/* 取得Excel工作簿 */
						Workbook wb = WorkbookFactory.create(is);	
						Sheet sheet = wb.getSheetAt(0); //第1個sheet
						list = new ArrayList<>();

						/* 取得每個標頭的欄位index（此作法必須確定[標頭文字]不會改變） */
						Row header = sheet.getRow(0);
						for(int x=0; x<header.getLastCellNum(); x++){
							String cellValue =  ToolsUtil.trim(header.getCell(x).getStringCellValue());
							headerIndex.put(cellValue, x);
						}						
						
						/* 取得每一資料行：固定從row=2開始取*/
						for(int r=1; r<=sheet.getLastRowNum(); r++){
							Row row = sheet.getRow(r);
							
//							IDB_HSVersion bean = new IDB_HSVersion();
//							bean.setISO2((String)ExcelUtil.getCellValue(row, headerIndex.get("ISO2"))); //ISO2
//							bean.setName((String)ExcelUtil.getCellValue(row, headerIndex.get("name"))); //name
//							bean.setYear(Integer.valueOf(ExcelUtil.getCellValue_DataFormat(row, headerIndex.get("year")))); //year
//							bean.setHs_version((String)ExcelUtil.getCellValue(row, headerIndex.get("hs_version"))); //hs_version
//							list.add(bean);
						}
					}//end else
				}//end while
			
				
				
				
//				//存入DB
//				fileInsertService service = new fileInsertService();
//				boolean result = false;
//				if (list!=null && !list.isEmpty()) {
//					result = service.insert(list);
//				}
//				
//
//				if(result == true && list.size() != 0) {
//					resultStr = "匯入完成，共匯入 ["+list.size()+"] 筆";
//				}else {
//					resultStr = "匯入失敗，請聯繫系統管理員並提供匯入檔案。";
//				}				
				
				response.setContentType("text/plain; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(resultStr);
				out.close();
				return;
				
			} catch (Exception e) {
				
				e.printStackTrace();
				resultStr = "匯入失敗，請聯繫系統管理員並提供匯入檔案。";
				response.setContentType("text/plain; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.write(resultStr);
				out.close();
				return;
				
			} finally {
				
				if(is!=null){
					try {
						is.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		}//END if		
	}
	
}


