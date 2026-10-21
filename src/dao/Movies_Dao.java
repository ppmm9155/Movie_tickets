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
	        
	        System.out.println("전체 조회 테스트");
	        while (rs.next()) {
	        	
	            System.out.println(
	                rs.getInt("movie_id") + " | " +
	                rs.getString("screen_id") + " | " +
	                rs.getString("title") + " | " +
	                rs.getTimestamp("movie_start") + " | " +
	                rs.getInt("movie_time") + "분"
	            );
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
