package Tools.IO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class ReadFile
{

	private String readFile(String url, String date)
	{
		String resultString = "";

		/* 讀檔：預防當天沒有檔案，出exception處理並傳回空的Map就可以往下一個日期前進 */
		FileReader fr = null;
		BufferedReader br = null;
		ArrayList<String> lineS = null;
		try {
			fr = new FileReader(url);
			br = new BufferedReader(fr);
			lineS = new ArrayList<>();
			while (br.ready()) {
				lineS.add(br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return resultString;
		} finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fr != null) {
				try {
					fr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/* 分析每一行字串 */
		for(String line : lineS) {
			try {
				resultString = readLine(line, date);
			} catch (Exception e) {
				System.out.println("此筆連線資料分析錯誤："+date+"，"+line);
				e.printStackTrace();
			}
		}
		return resultString;
	}

	
	
	private String readLine(String line, String date)
	{
		return "";
	}
}
