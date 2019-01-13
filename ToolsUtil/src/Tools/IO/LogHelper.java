package Tools.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

public class LogHelper {

	public static File checkFilePath(String fileDir, String fileName) {
		//建立儲存位置，資料夾確認，為路徑建起File，取得此File的路徑如果沒有就產生一個
		File f = new File(fileDir);   //  "E:/temp"
		f = new File(f.getAbsolutePath()); 
		if (!f.exists())
			f.mkdirs();
		//路徑：資料夾+檔名
		f = new File(f.getAbsolutePath() + File.separator + fileName);
		return f;
	}
	
	public static void writeResult(String fileDir, String fileName, List<String> loglist){
		FileOutputStream os = null;
		try {
			File file = checkFilePath(fileDir, fileName);
			os = new FileOutputStream(file, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		OutputStreamWriter osw=null;
		try {
			osw = new OutputStreamWriter(os, "UTF-8");
			for(String str:loglist){
				osw.write(str);
				osw.write("\r\n");
			}
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(osw!=null){
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void writeResult(String filepath,List<String> loglist){
		FileOutputStream os = null;
		try {
			os = new FileOutputStream(filepath,true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		OutputStreamWriter osw=null;
		try {
			osw = new OutputStreamWriter(os, "UTF-8");
			for(String str:loglist){
				osw.write(str);
				osw.write("\r\n");
			}
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(osw!=null){
				try {
					osw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static void main(String[] args) throws Exception {
		
	}

}
