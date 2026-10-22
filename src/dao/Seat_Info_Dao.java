package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bean.Seat_Info_Bean;
import config.DBConnection;

public class Seat_Info_Dao extends DBConnection{
	
	public Seat_Info_Dao() {
		super();
	}
	
	//좌석 정보 ArrayList 에 담기
	public ArrayList<Seat_Info_Bean> SeatData(){
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		ArrayList<Seat_Info_Bean> lists = new ArrayList<Seat_Info_Bean>();
		
		String sql = "select 8 from seat_info order by seat_id asc";
		try {
			conn = getConniction();
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Seat_Info_Bean sb = new Seat_Info_Bean();
				
				sb.setSeat_id(rs.getInt("seat_id"));
				sb.setScreen_id(rs.getInt("screen_id"));
				sb.setSeat_row(rs.getString("seat_row"));
				sb.setSeat_col(rs.getInt("seat_col"));
				
				lists.add(sb);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!= null)
					rs.close();
				if(pstmt!= null)
					pstmt.close();
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return lists;
		
	}
	
	
}
