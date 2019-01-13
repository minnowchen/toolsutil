package Tools.Data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class PropUtil 
{
	static InputStream input = null;
	
	/**
	 * 載入 jar 檔的  properties file
	 * Please put your property file in resources directory. 
	 * @param fileName       property file name
	 * @return               Properties
	 * @throws IOException
	 */
	public static Properties getProperty(String fileName)
	{
		Properties prop = new Properties();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		try
		{
			input = null;
			input = loader.getResourceAsStream(fileName);	
			prop.load(input);		
			input.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return prop;			
	}
	/**
	 * 讀取自訂的 prop 檔 
	 * @param fileName
	 * @return
	 */
	public static Properties getPropertySetting(String fileName)
	{
		Properties prop = new Properties();
//		InputStream input = new FileInputStream(url);
		try {
			input = new FileInputStream(fileName) ;
			prop.load(input);
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return prop ;
	}
		
	public static Properties loadProperties(String arg)
	{
		Properties prop = null;
		try
		{
			prop = new Properties();
			//first load default properties
			input = PropUtil.class.getClassLoader().getResourceAsStream(arg);
			if (input != null)
			{
				prop.load(input);
				input.close();			
				//prop.list(System.out);							
			}
			
			//查看是否有以 jvm 參數設定的 properties
			String jvmArg = System.getProperty(arg);
			if (jvmArg!=null && !jvmArg.isEmpty())
			{
				input = new FileInputStream(jvmArg);
				if (input != null)
				{
					prop.load(input);
					input.close();
					//prop.list(System.out);					
				}
			}
			
			//查看是否有命令列參數
			if (arg!=null && ! arg.isEmpty())
			{
				input = new FileInputStream(arg);
				if (input != null)
				{
					prop.load(input);
					input.close();
					//prop.list(System.out);					
				}				
			}
			
			return prop;
		}
		catch(Exception e)
		{
			//e.printStackTrace();
		}
		
		return prop;
	}//loadProperties
	
	public static String getPropertyValue(String key, String arg)
	{
		String ret = "" ;
		
		Properties p = loadProperties(arg);
		
		ret = p.getProperty(key) ; 
		
		return ret ; 
	}
	
	public static void main(String [] args)
	{
//		PropUtil.loadProperties("PH.properties");
		
//		System.out.println(PropUtil.getPropertyValue(Constants.DOWNLOAD_FILELINK) );
		
//		Properties p = getProperty("PH.properties");
//		Enumeration e = p.propertyNames();
//	    while (e.hasMoreElements()) 
//	    {
//	    	String key = (String) e.nextElement();
//	    	System.out.println(key + " -- " + p.getProperty(key));
//	    }
		
		String str = getPropertyValue("param", "PH.properties");
		System.out.println(str);
		
	}
	
}
