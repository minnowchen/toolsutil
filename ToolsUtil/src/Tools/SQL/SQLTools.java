package Tools.SQL;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import Tools.Data.ToolsUtil;

import javax.naming.*;


public class SQLTools{
	private Connection conn = null;
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private int connectionType = 1;
	private String connectionString = null;
	
	public static final int Tomcat = 1;
	public static final int SQLServer = 2;
	public static final int Derby = 3;
	public static final int MariaDB = 4;

	public SQLTools(int connectionType, String connectionString){ //(Tomcat, "jdbc/Tariff_PH")
		this.connectionType = connectionType;
		this.connectionString = connectionString;
	}

	private Connection getConnection() throws Exception{
		try{
			switch(connectionType){
				case SQLServer:
					return getSQLServerConnection();
				case Derby:
					return getDerbyConnection();
				case MariaDB:
					return getMariaDBConnection();
				case Tomcat:
				default:
					return getTomcatConnection();
			}
		}catch(Exception e){
			throw e;
		}
	}
	
	private Connection getSQLServerConnection() throws Exception{
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			return DriverManager.getConnection(connectionString);
		}catch(Exception e){
			throw e;
		}
	}
	
	private Connection getTomcatConnection() throws Exception{
		Context initContext = new InitialContext();
		Context envContext = (Context) initContext.lookup("java:comp/env");

		DataSource ds = (DataSource) envContext.lookup(connectionString);
		if(ds == null){
			throw new Exception("Cannot get DataSource!");
		}else{
			return ds.getConnection();
		}
	}
	
	private Connection getDerbyConnection() throws Exception{
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
			return DriverManager.getConnection(connectionString);
		}catch(Exception e){
			throw e;
		}
	}
	
	private Connection getMariaDBConnection() throws Exception{
		try{
			Class.forName("org.mariadb.jdbc.Driver").newInstance();
			return DriverManager.getConnection(connectionString);
		}catch(Exception e){
			throw e;
		}
	}
	
	private void checkConnection() throws SQLException{
		try{
			if(conn == null || conn.isClosed()){
				conn = getConnection();
			}
		}catch(javax.naming.NoInitialContextException nice){
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}

	public void noCommit() throws SQLException{
		try{
			checkConnection();

			conn.setAutoCommit(false);
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}

	public void commit() throws SQLException{
		try{
			checkConnection();

			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}

	public void rollback() throws SQLException{
		try{
			checkConnection();

			conn.rollback();
			conn.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}

	public boolean execute(String sqlcmd) throws SQLException{
		boolean result = false;

		try{
			checkConnection();

			stmt = conn.createStatement();
			result = stmt.execute(sqlcmd);
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}

		return result;
	}

	/**
	 *
	 * @param sqlcmd : a SQL SELECT command
	 * @return ResultSet : the query result
	 * @throws SQLException
	 */
	public ResultSet query(String sqlcmd) throws SQLException{
		ResultSet rs = null;

		try{
			checkConnection();

			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlcmd);
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}

		return rs;
	}

	/**
	 *
	 * @param sqlcmd : a SQL INSERT, UPDATE, or DELETE command
	 * @throws SQLException
	 */
	public int write(String sqlcmd) throws SQLException{
		int rows = -1;

		try{
			checkConnection();

			stmt = conn.createStatement();
			rows = stmt.executeUpdate(sqlcmd);
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}finally{
			if(stmt != null){
				stmt.close();
			}
		}

		return rows;
	}

	/**
	 *
	 * @param sqlcmd : a SQL INSERT, UPDATE, or DELETE command
	 * @throws SQLException
	 */
	public PreparedStatement prepare(String sqlcmd) throws SQLException{
		try{
			checkConnection();

			pstmt = conn.prepareStatement(sqlcmd);
			return pstmt;
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}
	
	/**
	 *
	 * @param sqlcmd : a SQL INSERT, columnNames : column names of generated keys
	 * @return A PreparedStatement that returns generated keys
	 * @throws SQLException
	 */
	public PreparedStatement prepare(String sqlcmd, String columnNames[]) throws SQLException{
		try{
			checkConnection();

			pstmt = conn.prepareStatement(sqlcmd, columnNames);
			return pstmt;
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}
	
	/**
	 *
	 * release all database resources
	 */
	public void close() throws SQLException{
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException e){
				//e.printStackTrace();
			}
			
			stmt = null;
		}

		if(pstmt != null){
			try{
				pstmt.close();
			}catch(SQLException e){
				//e.printStackTrace();
			}
			
			pstmt = null;
		}

		closeConnection();
	}
	
	/**
	 *
	 * close the current connection
	 */
	private void closeConnection() throws SQLException{
		if(conn != null){
			try{
				if(!conn.isClosed()){
					conn.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
				conn = null;
		}
		
		//DriverManager.getConnection("jdbc:derby:;shutdown=true");
	}

	public boolean isConnected(){
		try{
			checkConnection();
		}catch(Exception e){}
		
		return (conn != null);
	}
	
	protected void finalize() throws Throwable{
			close();
	}
	
	public DatabaseMetaData getMetaData() throws SQLException{
		try{
			checkConnection();

			return conn.getMetaData();
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}
	
	public static String toDBString(String inString){
		return ToolsUtil.dbstring(inString);
	}
	
	public static ArrayList<String> getColumns(ResultSet rs){
		ArrayList<String> result = new ArrayList<String>();
		try{
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1;i <= rsmd.getColumnCount();i++){
				result.add(
						ToolsUtil.isEmpty(rsmd.getColumnLabel(i)) ? rsmd.getColumnName(i) : rsmd.getColumnLabel(i)
				);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static HashMap<String, String> getHashMap(ArrayList<String> columns, ResultSet rs){
		HashMap<String, String> result = new HashMap<String, String>();
		try{
			for(int i = 0;i < columns.size();i++){
				String column = columns.get(i);
				result.put(column, ToolsUtil.trim(rs.getString(column)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static HashMap<String, String> getHashMap(ResultSet rs){
		ArrayList<String> columns = getColumns(rs);
		HashMap<String, String> result = new HashMap<String, String>();
		try{
			for(int i = 0;i < columns.size();i++){
				String column = columns.get(i);
				result.put(column, ToolsUtil.trim(rs.getString(column)));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	public HashMap<String, String> getHashMap(String sqlcmd){
		HashMap<String, String> result = new HashMap<String, String>();
		try{
			ResultSet rs = query(sqlcmd);
			
			if(rs.next()){
				result = getHashMap(rs);
			}
			
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	public String getStringValue(String sqlcmd){
		String result = "";
		try{
			ResultSet rs = query(sqlcmd);
			
			if(rs.next()){
				result = rs.getString(1);
			}
			
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}