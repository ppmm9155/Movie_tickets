package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Movies_Bean;
import bean.Ticket_Bean;
import config.DBConnection;

public class Ticket_Dao extends DBConnection{
	
	public Ticket_Dao() {
		super();
	}

	public void ticketSelectAll() {
		
		String sql = "select * from ticket order by ticket_id asc";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConniction();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Ticket_Bean tb = new Ticket_Bean();
				Movies_Bean mb = new Movies_Bean();
				
				tb.setTicket_id(rs.getInt("ticket_id"));
				tb.setTicket_num(rs.getInt("ticket_num"));
				// 아마 안될텐데 참조를 하는건데 이건 개별로 저장하는거니까
				// TicketBean -상속 받은 Movie_id 를 쓰는건데 
//				mb.setTitle(rs.getString("movie_title")); 
//				
//				tb.setMb(mb);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
