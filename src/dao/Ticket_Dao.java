package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Movies_Bean;
import bean.Seat_Info_Bean;
import bean.Ticket_Bean;
import config.DBConnection;

public class Ticket_Dao extends DBConnection{
	
	public Ticket_Dao() {
		super();
	}

	public ArrayList<Ticket_Bean> ticketSelectAll() {
		
		String sql = "select "
				+ "k.ticket_id , k.ticket_num , "
				+ "m.movie_id , m.screen_id, m.title ,m.movie_start , m.movie_time , "
				+ "s.seat_id , s.seat_row , s.seat_col , s.screen_id "
				+ "from ticket k "
				+ "join movies m on k.movie_id = m.movie_id "
				+ "join seat_info s on k.seat_id = s.seat_id  ";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Ticket_Bean> lists = new ArrayList<Ticket_Bean>();
		
		try {
			conn = getConniction();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Movies_Bean mb = new Movies_Bean();
				Seat_Info_Bean sb = new Seat_Info_Bean();
				
				Ticket_Bean tb = new Ticket_Bean();
				
				//티켓
				tb.setTicket_id(rs.getInt("ticket_id"));
				tb.setTicket_num(rs.getInt("ticket_num"));
				//영화
				mb.setMovie_id(rs.getInt("movie_id"));
				mb.setScreen_id(rs.getInt("screen_id"));
				mb.setTitle(rs.getString("title"));
				mb.setMovie_start(rs.getTimestamp("movie_start"));
				mb.setMovie_time(rs.getInt("movie_time"));
				tb.setMb(mb);
				
				//좌석
				sb.setSeat_id(rs.getInt("seat_id"));
				sb.setSeat_row(rs.getString("seat_row"));
				sb.setSeat_col(rs.getInt("seat_col"));
				sb.setScreen_id(rs.getInt("screen_id"));
				
				tb.setSb(sb);
				
				//ArrayList
				lists.add(tb);
				
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
		
	}//
	
	
	public Ticket_Bean searchTicket(int ticketId) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select "
				+ "k.ticket_id , k.ticket_num , "
				+ "m.movie_id , m.screen_id, m.title ,m.movie_start , m.movie_time , "
				+ "s.seat_row , s.seat_col , s.screen_id "
				+ "from ticket k "
				+ "join movies m on k.movie_id = m.movie_id "
				+ "join seat_info s on k.seat_id = s.seat_id  "
				+ "where k.ticket_id = ? " ;
		
		Ticket_Bean tb = null;
		
		try {
			conn = getConniction();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ticketId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				Movies_Bean mb = new Movies_Bean();
				Seat_Info_Bean sb = new Seat_Info_Bean();
				
				tb = new Ticket_Bean();
				
				//티켓
				tb.setTicket_id(rs.getInt("ticket_id"));
				tb.setTicket_num(rs.getInt("ticket_num"));
				//영화
				mb.setMovie_id(rs.getInt("movie_id"));
				mb.setScreen_id(rs.getInt("screen_id"));
				mb.setTitle(rs.getString("title"));
				mb.setMovie_start(rs.getTimestamp("movie_start"));
				mb.setMovie_time(rs.getInt("movie_time"));
				tb.setMb(mb);
				
				//좌석
				// screen_id 중복되고 영화티켓은 영화정보라서 screen_id 없앰
				sb.setSeat_row(rs.getString("seat_row"));
				sb.setSeat_col(rs.getInt("seat_col"));
				sb.setScreen_id(rs.getInt("screen_id"));
				
				tb.setSb(sb);
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
		
		return tb;
		
	}//
	
	
	//영화 ID로 예매정보 찾기
	public ArrayList<Ticket_Bean> findTicket(int choiceMovie) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select "
				+ "k.ticket_id , k.ticket_num , "
				+ "m.movie_id , m.screen_id, m.title ,m.movie_start , m.movie_time , "
				+ "s.seat_row , s.seat_col  "
				+ "from ticket k "
				+ "join movies m on k.movie_id = m.movie_id "
				+ "join seat_info s on k.seat_id = s.seat_id  "
				+ "where m.movie_id = ? " ;
		
		ArrayList<Ticket_Bean> lists = new ArrayList<Ticket_Bean>();
		
		Ticket_Bean tb;
		
		try {
			
			conn=getConniction();
			
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, choiceMovie);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Movies_Bean mb = new Movies_Bean();
				Seat_Info_Bean sb = new Seat_Info_Bean();
				
				tb = new Ticket_Bean();
				
				//티켓
				tb.setTicket_id(rs.getInt("ticket_id"));
				tb.setTicket_num(rs.getInt("ticket_num"));
				//영화
				mb.setMovie_id(rs.getInt("movie_id"));
				mb.setScreen_id(rs.getInt("screen_id"));
				mb.setTitle(rs.getString("title"));
				mb.setMovie_start(rs.getTimestamp("movie_start"));
				mb.setMovie_time(rs.getInt("movie_time"));
				tb.setMb(mb);
				
				//좌석
				// screen_id 중복되고 영화티켓은 영화정보라서 screen_id 없앰
				sb.setSeat_row(rs.getString("seat_row"));
				sb.setSeat_col(rs.getInt("seat_col"));
				sb.setScreen_id(rs.getInt("screen_id"));
				
				tb.setSb(sb);
				
				lists.add(tb);
				
				conn.commit();
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
	//영화별 좌석수 조회
	public ArrayList<Ticket_Bean> epmtySeats() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		//에몌정보만 있는거 조회하는거 에서 -> 예매정보도 없는 영화도 조회하게 
		String sql = "select "
		        + "m.movie_id , m.screen_id, m.title ,m.movie_start , m.movie_time , count(k.ticket_id) as count "
		        + "from movies m "
		        + "left join ticket k on k.movie_id = m.movie_id "
		        + "group by m.movie_id,m.screen_id,m.title,m.movie_start,m.movie_time "
		        + "order by m.movie_id ";
		
		ArrayList<Ticket_Bean> lists = new ArrayList<Ticket_Bean>();
		Ticket_Bean tb;
		
		try {
			conn = getConniction();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Movies_Bean mb = new Movies_Bean();
				Seat_Info_Bean sb = new Seat_Info_Bean();
				
				tb = new Ticket_Bean();
				
				//영화
				mb.setMovie_id(rs.getInt("movie_id"));
				mb.setScreen_id(rs.getInt("screen_id"));
				mb.setTitle(rs.getString("title"));
				mb.setMovie_start(rs.getTimestamp("movie_start"));
				mb.setMovie_time(rs.getInt("movie_time"));
				tb.setMb(mb);
				
				//좌석
				sb.setSeat_id(rs.getInt("count"));
				
				tb.setSb(sb);
				
				lists.add(tb);
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

	public int deleteTicket(int delete) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete from ticket where ticket_id = ? ";
		
		int cnt = 0;
		
		try {
			conn = getConniction();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, delete);
			
			cnt = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			System.out.println("오류가 발생했습니다");
		}finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return cnt;
	}

	public int insertTicket(int movie_id, int seat_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
//		insert into ticket values(ticket_seq.nextval , 3 , 13 , default ); 
		String sql = "insert into ticket values(ticket_seq.nextval , "
				+ "? , ? , default) ";
		
		int cnt = 0;
		
		try {
			conn=getConniction();
			
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, movie_id);
			pstmt.setInt(2, seat_id);
			
			cnt = pstmt.executeUpdate();
			// 미리 자석에 띄울 에매정보 추가하고 commit 시킴
			//commit 확정되면 cnt 1 로 반환되게
			conn.commit();
			
			return cnt;
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			// insert 또는 commit 실패 시 변경사항 취소
			if (conn != null) {
		        try {
		            conn.rollback();
		        } catch (SQLException rollbackError) {
		            rollbackError.printStackTrace();
		        }
		    }
			
		}finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		//저장성공 못하면 0으로 
		return 0;
		
	}
	
	public boolean haveTicket() throws SQLException{
		Connection conn = null;
		PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    
	    String sql = "select ticket_id from ticket where rownum = 1";
	    
	    boolean flag = false;
	    
	    try {
	        conn = getConniction();
	        pstmt = conn.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        // flag 있으면 true 없으면 false
	        flag = rs.next();
	        return flag;

	    }finally {
	        try {
	            if (rs != null)
	                rs.close();
	            if (pstmt != null)
	                pstmt.close();
	            if (conn != null)
	                conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	}
	
	// 해당 영화의 해당 좌석이 이미 예매됐는지 확인
	// 해당 영화의 좌석 예매 여부 확인
	public Boolean isReserved(int movieId, int seatId) {
		
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;

	    String sql = "select ticket_id from ticket "
	            + "where movie_id = ? and seat_id = ?";
	    
	    boolean flag = false;
	    
	    try {
	        conn = getConniction();
	        pstmt = conn.prepareStatement(sql);

	        pstmt.setInt(1, movieId);
	        pstmt.setInt(2, seatId);

	        rs = pstmt.executeQuery();
	        
	        flag = rs.next();
	        
	        return flag;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;

	    } finally {
	        try {
	            if (rs != null)
	                rs.close();
	            if (pstmt != null)
	                pstmt.close();
	            if (conn != null)
	                conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }//finally
	    
	}

}
