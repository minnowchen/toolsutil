package Tools.SQL;


public class SQL extends SQLTools{
	public SQL(){
		super(SQLTools.Tomcat, "jdbc/Tariff_PH");
//		super(SQLTools.SQLServer, "jdbc:sqlserver://10.0.1.180:1433;databaseName=Airiti140725;user=airiti0765;password=8765;SelectMethod=cursor");
	}
	
	//JNDI
	public SQL(String JNDIName){
		super(SQLTools.Tomcat, JNDIName);
	}
	
	//JDBC
	public SQL(String ip, String databaseName, String user, String password){
		//"jdbc:sqlserver://10.0.1.180:1433;databaseName=Airiti140725;user=airiti0765;password=8765;SelectMethod=cursor"
		super(SQLTools.SQLServer, getConnectionString(ip, databaseName, user, password));
	}

	private static String getConnectionString(String ip, String databaseName, String user, String password)
	{
		StringBuilder conn = new StringBuilder();
		conn.append("jdbc:sqlserver://").append(ip).append(":1433")
			.append(";databaseName=").append(databaseName)
			.append(";user=").append(user)
			.append(";password=").append(password)
			.append(";SelectMethod=cursor");		return null;
	}
}
