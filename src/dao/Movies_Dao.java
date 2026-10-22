package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Movies_Bean;
import config.DBConnection;

public class Movies_Dao extends DBConnection{
	
	public Movies_Dao() {
		super();
	}
	
	//전체영화 조회

	public ArrayList<Movies_Bean> movieSelectAll() {
		
		String sql = "select * from movies order by movie_id asc";
		
		Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    ArrayList<Movies_Bean> lists = new ArrayList<Movies_Bean>();
	    
		try{
			//접속
			conn = getConniction();
			
	        pstmt = conn.prepareStatement(sql);
	        
	        rs = pstmt.executeQuery();
	        
//	        System.out.println("전체 조회 테스트");
	        while (rs.next()) {
	        	
//	            System.out.println(
//	                rs.getInt("movie_id") + " | " +
//	                rs.getString("screen_id") + " | " +
//	                rs.getString("title") + " | " +
//	                rs.getTimestamp("movie_start") + " | " +
//	                rs.getInt("movie_time") + "분"
//	            );
	        	
	        	Movies_Bean mb = new Movies_Bean();
	        	
	        	mb.setMovie_id(rs.getInt("movie_id"));
	        	mb.setScreen_id(rs.getInt("screen_id"));
	        	mb.setTitle(rs.getString("title"));
	        	mb.setMovie_start(rs.getTimestamp("movie_start"));
	        	mb.setMovie_time(rs.getInt("movie_time"));
	        	
	        	lists.add(mb);
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
		}//finally
		
		return lists;
		
	}
	
	//영화 상세 정보 조회
	public ArrayList<Movies_Bean> searchMovie() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Movies_Bean> lists = new ArrayList<Movies_Bean>();
		
		String search = null; // Main 에서 매개인자로 search 로 받아야함 지금은 에러떄문에 선언만
		
		try {
			conn = getConniction();
			
			String sql = "select * from movies where title like ? ";
			
			pstmt= conn.prepareStatement(sql);
			
			pstmt.setString(1, "%"+search+"%");
			
//			pstmt.setString(1, search);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Movies_Bean mb = new Movies_Bean();
	        	
	        	mb.setMovie_id(rs.getInt("movie_id"));
	        	mb.setScreen_id(rs.getInt("screen_id"));
	        	mb.setTitle(rs.getString("title"));
	        	mb.setMovie_start(rs.getTimestamp("movie_start"));
	        	mb.setMovie_time(rs.getInt("movie_time"));
	        	
	        	lists.add(mb);
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
		}//finally
		
		return lists;
		
	}
	
//	
	
}
