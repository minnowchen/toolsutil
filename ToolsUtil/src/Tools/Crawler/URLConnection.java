package Tools.Crawler;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.poi.util.IOUtils;

public class URLConnection
{
	
	public static void main(String[] args) throws Exception {
		
		//產生出URL - http://13.75.90.92/Stock/aStock/AgentStat_Ajax?StockNo=2317&Types=3.5&StartDate=20160403&EndDate=20160409&Rows=35
		String sURL = "http://13.75.90.92/";
		String fileDir = "E:/temp" ;
		String fileName = "stock.txt";
		String filePath = fileDir +"/"+ fileName;
		
		URLConnection urlConnection = new URLConnection();
		urlConnection.doURLConnect(sURL, fileDir, fileName);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	
	//連線URL取資料，存到硬碟
	public void doURLConnect(String sURL, String filePath, String fileName){
		try {
			//建立URL，開HttpUrlConnection，把需要的request標頭放進RequestProperty
			URL url = new URL(sURL);
			HttpURLConnection URLconn = null;
			URLconn = (HttpURLConnection)url.openConnection();
//			URLconn.setRequestMethod("GET");
			//瀏覽器能接受回應的MINE型態
			URLconn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			//瀏覽器可接受的編碼列表
//			URLconn.setRequestProperty("Accept-Encoding", "gzip, deflate, sdch");
			//伺服器回傳資料可用的語言
			URLconn.setRequestProperty("Accept-Language", "zh-TW,zh;q=0.8,en-US;q=0.6,en;q=0.4");
			//
			URLconn.setRequestProperty("Accept-Charset", "utf-8;q=0.7,*;q=0.7");
			//表瀏覽器版本
			URLconn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36");
			//連線
			URLconn.connect();
			
			
			
			//建立儲存位置，資料夾確認，為路徑建起File，取得此File的路徑如果沒有就產生一個
			File f = new File(filePath);   //  "E:/temp"
			f = new File(f.getAbsolutePath()); 
			if (!f.exists())
				f.mkdirs();
			//路徑：資料夾+檔名
			f = new File(f.getAbsolutePath() + File.separator + fileName);
			
			//開啟有緩衝區的inputStream
			// 1.另一種讀取Byte方法 (可以改成回傳Byte[])
			byte[] bytes = IOUtils.toByteArray(URLconn.getInputStream());
			BufferedOutputStream bufferos2 = new BufferedOutputStream(new FileOutputStream(f));
			bufferos2.write(bytes);
			bufferos2.flush();
			bufferos2.close();
			
			/* 2.原始讀Byte方法
			BufferedInputStream bufferis = new BufferedInputStream(URLconn.getInputStream());
			BufferedOutputStream bufferos = new BufferedOutputStream(new FileOutputStream(f));
			byte[] temp = new byte[8192];
			int len;
			while( (len = bufferis.read(temp)) != -1  ){
				bufferos.write(temp, 0, len);
			}
			bufferos.flush();
			bufferos.close();
			*/
		} catch (IOException e) {
			System.out.println("Error download : " + e);
		}
	}
	
	
}
