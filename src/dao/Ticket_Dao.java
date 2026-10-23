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
		String sql = "select "
				+ "m.movie_id , m.screen_id, m.title ,m.movie_start , m.movie_time , count(s.seat_id) as count "
				+ "from ticket k "
				+ "join movies m on k.movie_id = m.movie_id "
				+ "join seat_info s on k.seat_id = s.seat_id "
				+ "group by m.movie_id,m.screen_id,m.title,m.movie_start,m.movie_time ";
		
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
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
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

}
