package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DBConnection;

public class Movies_Dao extends DBConnection{
	
	public Movies_Dao() {
		super();
	}
	
	//전체영화 조회

	public void movieSelectAll() {
		
		String sql = "select * from movies order by movie_id";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

		try{
			//접속
			conn = getConniction();
			
	        pstmt = conn.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();
	        
			while(rs.next()) {
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null)
					rs.close();
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
}
