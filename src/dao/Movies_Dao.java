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
	        
	        while (rs.next()) {
	        	
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
	public ArrayList<Movies_Bean> searchMovie(String search) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Movies_Bean> lists = new ArrayList<Movies_Bean>();
		
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
	
	//오전 영화 조회
	public ArrayList<Movies_Bean> amSelectMovie() {
		
		Connection conn= null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		// 레코드 입력된게 어제 날짜인 2026-10-21임
		String sql = "select * from movies where movie_start between "
				+ "to_date(to_char('2026-10-21')|| '00:00' , 'yyyy-mm-dd hh24:mi' )  and "
				+ "to_date(to_char('2026-10-21')|| '11:59' , 'yyyy-mm-dd hh24:mi' ) ";
		
		ArrayList<Movies_Bean> lists = new ArrayList<Movies_Bean>();
		
		try {
			conn=getConniction();
			pstmt = conn.prepareStatement(sql);
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
		}
		
		return lists;
	}
	
	//오후 영화 조회
	public ArrayList<Movies_Bean> pmSelectMovie() {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		// 레코드 입력된게 어제 날짜인 2026-10-21임
		String sql = "select * from movies where movie_start between "
				+ "to_date(to_char('2026-10-21')|| '12:00' , 'yyyy-mm-dd hh24:mi' )  and "
				+ "to_date(to_char('2026-10-21')|| '23:59' , 'yyyy-mm-dd hh24:mi' ) ";
		
		ArrayList<Movies_Bean> lists = new ArrayList<Movies_Bean>();
		
		try {
			conn = getConniction();
			pstmt = conn.prepareStatement(sql);
			
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
		}
		
		return lists;
	}
	
	// 입력한 번호가 영화번호에 있는지 
	public Boolean isMovieId(int choiceMovie) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from movies where movie_id = ? ";
		
		boolean flag = false;
		
		try {
			conn = getConniction();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, choiceMovie);
			
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				flag= true;
			}else {
				flag = false;
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
		
		return flag;
		
	}
	
//	
	
}
