package bean;

public class Ticket_Bean {
	
	private Movies_Bean mb ;
	private Seat_Info_Bean sb ;
	
	
	private int ticket_id;
	private int ticket_num;
	public Movies_Bean getMb() {
		return mb;
	}
	public void setMb(Movies_Bean mb) {
		this.mb = mb;
	}
	public Seat_Info_Bean getSb() {
		return sb;
	}
	public void setSb(Seat_Info_Bean sb) {
		this.sb = sb;
	}
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public int getTicket_num() {
		return ticket_num;
	}
	public void setTicket_num(int ticket_num) {
		this.ticket_num = ticket_num;
	}
	
	
	
}
