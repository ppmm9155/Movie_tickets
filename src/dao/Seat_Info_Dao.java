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
		
		String sql = "select * from seat_info order by seat_id asc";
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
	// 입력한 row 와 col 이 seat_info에 있는지 그리고 seat_id 찾기
	public Seat_Info_Bean findSeat_id(String row, int col) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select seat_id from seat_info "
				+ "where seat_row = ? and seat_col = ? ";
		
		Seat_Info_Bean sb = null;
		
		try {
			conn = getConniction();
			pstmt=conn.prepareStatement(sql);
			
			pstmt.setString(1, row);
			pstmt.setInt(2, col);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				sb = new Seat_Info_Bean();
				sb.setSeat_id(rs.getInt("seat_id"));
				
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
		
		return sb;
	}
	
	
}
