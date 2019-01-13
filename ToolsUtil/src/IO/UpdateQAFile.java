package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import Tools.SQL.SQLTools;

public class UpdateQAFile
{

	public static void main(String[] arg)
	{
		
		//讀資料夾內的File列表
		File f = null;
		File[] paths = null;
		ArrayList<File> fileS = new ArrayList<>();
		try {
			f = new File("D:/ITV-QAFile");
			paths = f.listFiles();
			fileS = new ArrayList<>(Arrays.asList(paths));  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		int count = 0;
		//讀取檔案
		for(File file : fileS) {
			System.out.println(file.getName());
			
			FileInputStream fis = null;
			SQLTools sqltools = new SQLTools(SQLTools.SQLServer, "jdbc:sqlserver://192.168.2.20:1433;DatabaseName=CIER_Interview;user=sa;password=Ibtech.9548;SelectMethod=cursor");;
			PreparedStatement pstmt = null;
			
			try {
				fis = new FileInputStream(file);
				pstmt = sqltools.prepare("UPDATE TB_CompanyFile SET file_content = ? "
									  + "WHERE filename = ? ");
				int col = 1;
				pstmt.setBinaryStream(col++, fis);
				pstmt.setString(col++, file.getName());
				pstmt.executeUpdate();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException s) {
				s.printStackTrace();
			} finally {
				if(fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
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
		
			++count;
		}
		
		System.out.println("已更新 "+count+" 筆問卷檔案");
	}


}
