package IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.douglasjose.tech.csv.Csv;
import com.douglasjose.tech.csv.CsvFactory;

import Tools.Data.DataConvert;
import Tools.SQL.SQLTools;


public class ReadCLTrade_Y_HS8
{
	static String connectionString;
	static String sqlString;
	
	public static void main(String[] args)
	{
		
		/* 提供資料夾所在位置，程式會取出所有檔案列表------------------------------------------------------- */
		
		//（決定使用的method）txt檔 or csv檔
		String format = "txt"; //csv , txt
		
		ArrayList<Map<String, String>> setting = new ArrayList<>();
		//進口設定
		Map<String, String> MCL = new HashMap<>();
		MCL.put("url",   "D:/CL/MCL");
		MCL.put("table", "MCLTrade_Y_HS8");
//		MCL.put("fileUrl", "D:/CL/mcl_csv"); //csv測試版
//		MCL.put("table", "A_test_MCL"); //csv測試版
		setting.add(MCL);
		//出口設定
		Map<String, String> XCL = new HashMap<>();
		XCL.put("url",   "D:/CL/XCL");
		XCL.put("table", "XCLTrade_Y_HS8");
//		XCL.put("fileUrl", "D:/CL/xcl_csv"); //csv測試版
//		XCL.put("table", "A_test_XCL"); //csv測試版
		setting.add(XCL);
		
		
		//SQL參數設定----------------------------------------------------------------------------
		String ip = "192.168.2.23";
		String databaseName = "temp";
		String user = "sa";
		String password = "Ibtech.9548";
		
		connectionString = "jdbc:sqlserver://"+ip+":1433;"
						 + "DatabaseName="+databaseName+";"
						 + "user="+user+";"
						 + "password="+password+";SelectMethod=cursor";
		//System.out.println(connectionString);
		//範例："jdbc:sqlserver://192.168.2.23:1433;DatabaseName=MoeaicWeb;user=sa;password=Ibtech.9548;SelectMethod=cursor"
		
		
		//讀取檔案設定----------------------------------------------------------------------------
		String year = "2017";  //（只用於輸入資料庫table）年度
		int HS_Length = 8;     //（替hs 補0用）8碼或10碼                      
		int colNo_HS8 = 0;     //（指定HS在第幾欄）index從0開始               
		int colNo_value = 4;   //（指定value在第幾欄）index從0開始

		
		
		//主程式----------------------------------------------------------------------------
		ReadCLTrade_Y_HS8 cl = new ReadCLTrade_Y_HS8();
		
		for(Map<String, String> set : setting) {
			
			//取出資料夾位置
			String url = set.get("url");
			
			//組合sql字串
			sqlString = "INSERT INTO " + set.get("table") + "(hs,Year,Value,partner) VALUES (?,?,?,?)"; 
			System.out.println("讀取 " + url + "資料夾, sql=" + sqlString);

			//取出檔案列表
			ArrayList<File> fileS = cl.readFile(url); 
			if("txt".equals(format)) {
				System.out.println( "使用txt讀檔程式，檔案數共有 " + fileS.size() + "個" );
				cl.readTxt(year, colNo_HS8, colNo_value, HS_Length, fileS);
				
			}else if("csv".equals(format)) {
				System.out.println( "使用csv讀檔程式，檔案數共有 " + fileS.size() + "個" );
				cl.readCsv(year, colNo_HS8, colNo_value, HS_Length, fileS);
			}
		}
	}
	
	private ArrayList<File> readFile(String fileUrl)
	{
		ArrayList<File> fileS = new ArrayList<>();
		if(fileUrl != null && fileUrl.trim().length()>0) {
			// 讀資料夾內的File列表
			File f = null;
			File[] files = null;
			try {
				f = new File(fileUrl);
				files = f.listFiles();
				fileS.addAll( new ArrayList<>(Arrays.asList(files)) );  
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return fileS;
	}

	private void readTxt(String year, int colNo_HS8, int colNo_value, int HS_Length, ArrayList<File> fileS)
	{
		int count = 0;
		int fileSUM = 0;

		// 讀取每個txt檔
		for(File file : fileS) {
			
//			if(count == 3) { break; } //測試階段時可以使用，指定讀取的檔案數量
			
			ArrayList<CLBean> list = new ArrayList<>();
			String partner = file.getName();
			partner = partner.substring(0, partner.indexOf(".")); //取partner
			
			// 讀取txt檔每一行
			FileReader fr = null;
			BufferedReader br = null;
			ArrayList<String> lineS = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				lineS = new ArrayList<>();
				while (br.ready()) {
					lineS.add(br.readLine());
				}
			} catch (IOException e) {
				e.printStackTrace();
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
			for(int i=1; i<lineS.size(); i++) {
				try {
					String line = lineS.get(i);
					String[] ary = line.split("\t");
					
//					System.out.println(ary[colNo_HS8] + ", "+ary[colNo_value] +", "+ary[1]);
					//System.out.println(ary[1]); //有引號包著 -->"No product description available"
					
					CLBean bean = new CLBean();
					bean.setPartner(partner);
					bean.setYear(year);
					bean.sethS(addZeroForNum(ary[colNo_HS8], HS_Length));
					bean.setValue(ary[colNo_value]);
					list.add(bean);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}//end for lineS
			insertToTable(list);
			++count;
			fileSUM = fileSUM + list.size();
			System.out.println("----- ["+file.getName()+"] insert OK_"+list.size()+"筆 ，["+count+"/"+fileS.size()+"] -----");
		}//end for fileS
		System.out.println("讀取完畢，共讀取 "+fileS.size()+"個檔案，共insert "+fileSUM+"筆資料");
		System.out.println("");
	}




	private void readCsv(String year, int colNo_HS8, int colNo_value, int HS_Length, ArrayList<File> fileS)
	{
		int count = 0;
		int fileSUM = 0;

		// 讀取每個csv檔
		for(File file : fileS) {
			
//			if(count == 3) { break; } //測試階段時可以使用，指定讀取的檔案數量
			
			ArrayList<CLBean> list = new ArrayList<>();
			String partner = file.getName();
			partner = partner.substring(0, partner.indexOf(".")); //取partner
			
			// 將csv讀入
			InputStream is = null;
			try {
				is = new FileInputStream(file);
				Csv csv = CsvFactory.createOfficeCsv();
				csv.load(is);
				//跳過第一row（標題row)
				for(int r=1; r<csv.getRows(); r++) {
//					System.out.println(csv.get(r, colNo_HS8)+", "+csv.get(r, colNo_value)+", "+ csv.get(r, 1));
					
					CLBean bean = new CLBean();
					bean.setPartner(partner);
					bean.setYear(year);
					bean.sethS(addZeroForNum(csv.get(r, colNo_HS8), HS_Length));
					bean.setValue(csv.get(r, colNo_value));
					list.add(bean);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if(is != null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			insertToTable(list);
			++count;
			fileSUM = fileSUM + list.size();
			System.out.println("----- ["+file.getName()+"] insert OK_"+list.size()+"筆 ，["+count+"/"+fileS.size()+"] -----");
		}//end for fileS
		System.out.println("讀取完畢，共讀取 "+fileS.size()+"個檔案，共insert "+fileSUM+"筆資料");
		System.out.println("");
	}

	
	private void insertToTable(ArrayList<CLBean> list)
	{
		SQLTools sqltools = new SQLTools(SQLTools.SQLServer, connectionString);
		PreparedStatement pstmt = null;
		
		try {
			pstmt = sqltools.prepare(sqlString);
			sqltools.noCommit();
			for(CLBean bean : list) {
				int col = 1;
				pstmt.setString(col++, bean.gethS());
				pstmt.setString(col++, bean.getYear());
				pstmt.setString(col++, bean.getValue());
				pstmt.setString(col++, bean.getPartner());
				pstmt.executeUpdate();
			}
			sqltools.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}
	
	
	public String addZeroForNum(String str, int strLength) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				sb.append("0").append(str);// 左補0
				// sb.append(str).append("0");//右補0
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}
	
}




class CLBean{
	
	String year;
	String hS;
	String value;
	String partner;
	public String getYear()
	{
		return year;
	}
	public void setYear(String year)
	{
		this.year = year;
	}
	public String gethS()
	{
		return hS;
	}
	public void sethS(String hS)
	{
		this.hS = hS;
	}
	public String getValue()
	{
		return value;
	}
	public void setValue(String value)
	{
		this.value = value;
	}
	public String getPartner()
	{
		return partner;
	}
	public void setPartner(String partner)
	{
		this.partner = partner;
	}
	
	
}
