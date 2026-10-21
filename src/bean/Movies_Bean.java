package bean;

import java.sql.Timestamp;

public class Movies_Bean {
	private int movie_id;
	private int screen_id;
	private String title;
	private Timestamp movie_start;
	private int movie_time;
	
	//getter setter
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public int getScreen_id() {
		return screen_id;
	}
	public void setScreen_id(int screen_id) {
		this.screen_id = screen_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getMovie_start() {
		return movie_start;
	}
	public void setMovie_start(Timestamp movie_start) {
		this.movie_start = movie_start;
	}
	public int getMovie_time() {
		return movie_time;
	}
	public void setMovie_time(int movie_time) {
		this.movie_time = movie_time;
	}
	
	
}
