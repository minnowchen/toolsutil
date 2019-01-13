package Tools.excel;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import Tools.Data.ToolsUtil;


public class CSVTools {
	Workbook workbook;
	StringBuilder sb = new StringBuilder();
	
	public CSVTools(Workbook workbook){
		this.workbook = workbook;
		DataFormatter formatter = new DataFormatter();
		
		Sheet sheet = workbook.getSheetAt(0);
		
		for(int i=0; i<=sheet.getLastRowNum(); i++){
			Row row = sheet.getRow(i);
			if(row == null){
				continue;
			}
			
			for(int j=0; j<row.getLastCellNum(); j++){
				sb.append(j == 0 ? "" : ",")
				  .append( ToolsUtil.trim(	formatter.formatCellValue(row.getCell(j))
						  				 ).replace(",", "，")
						 );
			}
			
			sb.append("\n");
		}
	}
	
	public void write(OutputStream out){
		//byte[] BOM_UTF8 = { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF };
		try{
			OutputStreamWriter osw = new OutputStreamWriter(out, StandardCharsets.UTF_8);
			//out.write(BOM_UTF8);
			//out.write(sb.toString().getBytes(StandardCharsets.UTF_8));
			//out.flush();
			osw.write("\uFEFF");
			osw.write(sb.toString());
			osw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
