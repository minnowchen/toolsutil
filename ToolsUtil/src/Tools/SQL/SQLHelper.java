package Tools.SQL;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHelper {
	private ResultSet rs = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private CallableStatement cs=null;
	
	private Connection getConnection(String location){
		String user="sa";
		String password="Ibtech.9548";
		try {
			if(location.equals("TB")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://www.cierdb.idv.tw:1433;database=ChinaDB",user, password);
			}else if(location.equals("TP")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://www.cierdb.idv.tw:1433;database=Tariff_Public",user, password);
			}else if(location.equals("TT")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://www.cierdb.idv.tw:1433;database=Tariff_Trade",user, password);
			}else if(location.equals("ECFA")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://www.cierdb.idv.tw:1433;database=ECFA",user, password);
			}else if(location.equals("CO")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://www.cierdb.idv.tw:1433;database=CO",user, password);
			}else if(location.equals("PH")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://www.cierdb.idv.tw:1433;database=Tariff_PH",user, password);
			}else if(location.equals("PH_23")){
				conn = DriverManager.getConnection( "jdbc:sqlserver://192.168.2.23:1433;database=Tariff_PH",user, password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return conn;
	}
	public void noCommit(String location) throws SQLException{
		try{
			if(conn == null){
				conn = getConnection(location);
			}

			if(conn.isClosed()){
				conn = getConnection(location);
			}

			conn.setAutoCommit(false);
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}

	public void commit(String location) throws SQLException{
		try{
			if(conn == null){
				conn = getConnection(location);
			}

			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}

	public void rollback(String location) throws SQLException{
		try{
			if(conn == null){
				conn = getConnection(location);
			}

			conn.rollback();
			conn.setAutoCommit(true);
		}catch(Exception e){
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}
	public PreparedStatement prepare(String sqlcmd,String location) throws SQLException{
		try{
			if(conn == null){
				conn = getConnection(location);
			}

			if(conn.isClosed()){
				conn = getConnection(location);
			}

			pstmt = conn.prepareStatement(sqlcmd);
			return pstmt;
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}
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
	}
	public CallableStatement prepareCall(String sqlcmd,String location) throws SQLException{
		try{
			if(conn == null){
				conn = getConnection(location);
			}

			if(conn.isClosed()){
				conn = getConnection(location);
			}
			cs = conn.prepareCall(sqlcmd);
			return cs;
		}catch(Exception e){
			System.out.println("Error! SQL command:" + sqlcmd);
			e.printStackTrace();
			close();
			throw new SQLException(e.toString());
		}
	}
	public void close() throws SQLException{
		if(rs != null){
			try{
				//if(!pstmt.isClosed()){
				rs.close();
				//}
			}catch(SQLException e){
				;//e.printStackTrace();
			}
			rs = null;
		}
		if(pstmt != null){
			try{
				//if(!pstmt.isClosed()){
					pstmt.close();
				//}
			}catch(SQLException e){
				;//e.printStackTrace();
			}
				pstmt = null;
		}
		if(cs != null){
			try{
				//if(!pstmt.isClosed()){
				cs.close();
				//}
			}catch(SQLException e){
				;//e.printStackTrace();
			}
			cs = null;
		}

		closeConnection();
	}
}
