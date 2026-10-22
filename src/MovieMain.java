import java.util.ArrayList;
import java.util.Scanner;

import bean.Movies_Bean;
import bean.Ticket_Bean;
import dao.Movies_Dao;
import dao.Seat_Info_Dao;
import dao.Ticket_Dao;

public class MovieMain {
	Scanner sc = new Scanner(System.in);
	static Movies_Dao mdao = new Movies_Dao();
	static Seat_Info_Dao sdao = new Seat_Info_Dao();
	static Ticket_Dao tdao = new Ticket_Dao();
	
	public MovieMain() {
		init();
	}
	
	// menu
	private void init() {
		while(true) {
			
			System.out.println("1. 상영중인 전체 영화 조회");
			System.out.println("2. 상영중인 영화 검색");
			
			
			System.out.println("7. 예매티켓 전체 조회");
			System.out.println("8. 예매한 티켓 검색");
			
			System.out.println("13. 프로그램 종료");
			
			
			int menu = 0;
			
			while(true) {
				System.out.print("메뉴 선택 >> ");
				try {
					menu = sc.nextInt();
					break;
				}catch(java.util.InputMismatchException e) {
					System.out.println("숫자를 입력해 주세요");
					sc.next();
				}
			}
			
			switch(menu) {
			case 1:
				//상영중인 전체 영화 조회
				ArrayList<Movies_Bean> Alllists = mdao.movieSelectAll();
				if(Alllists.size() > 0) {
					showMovies(Alllists);
				}else {
					System.out.println("상영중인 영화가 없습니다.");
				}
				
				break;
			case 2:
				// 상영중인 영화검색
				searchMovie();
				
				break;
			case 3:
				
				break;
			case 4:
				
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
				ArrayList<Ticket_Bean> Tlists = tdao.ticketSelectAll();
				if(Tlists.size() > 0 ) {
					showTicket(Tlists);
				}else {
					System.out.println("예매 정보가 없습니다");
				}
				
				
				break;
			case 8:
				searchTicket();
				
				break;
			case 9:
				
				break;
			case 10:
				
				break;
			case 11:
				
				break;
			case 12:
				
				break;
			case 13:
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
				break;
			default:
				System.out.println("1~13번만 입력하세요");
			}
			
		}
	}
	
	private void searchTicket() {
		java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH시mm분");
		
		int ticketId = 0;
		
		while(true) {
			System.out.print("에매 번호를 입력하시오 ");
			try {
				ticketId = sc.nextInt();
				break;
			}catch(java.util.InputMismatchException e) {
				System.out.println("번호를 입력하시오.");
				sc.next();
			}
		}
		
		Ticket_Bean tb = tdao.searchTicket(ticketId);
		
		if(tb != null) {
			System.out.println("-------------------------------------");
			System.out.println(
					"영화 제목: "+tb.getMb().getTitle()+"\n"+
					"상영 시작 시간: "+tb.getMb().getMovie_start().toLocalDateTime().format(timeFormatter)+"\n"+
					"상영 시간: "+tb.getMb().getMovie_time()+"분 "+"\n"+
					"상영관: "+tb.getMb().getScreen_id()+"관 "+"\n"+
					"관람 좌석: "+tb.getSb().getSeat_row()+"행"+tb.getSb().getSeat_col()+"열 "+"\n"+
					"예매 번호: "+tb.getTicket_id()+"\n"+
					"관람 인원: "+tb.getTicket_num()
						);
			System.out.println("-------------------------------------");
		}else {
			System.out.println("조건에 맞는 예매정보가 없습니다.\n");
		}
	}

	private void showTicket(ArrayList<Ticket_Bean> Tlists) {
		System.out.println("-------------------------------------");
		java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH시mm분");
		for(Ticket_Bean x : Tlists) {
			System.out.println(
				"영화 제목: "+x.getMb().getTitle()+"\n"+
				"상영 시작 시간: "+x.getMb().getMovie_start().toLocalDateTime().format(timeFormatter)+"\n"+
				"상영 시간: "+x.getMb().getMovie_time()+"분 "+"\n"+
				"상영관: "+x.getMb().getScreen_id()+"관 "+"\n"+
				"관람 좌석: "+x.getSb().getSeat_row()+"행"+x.getSb().getSeat_col()+"열 "+"\n"+
				"예매 번호: "+x.getTicket_id()+"\n"+
				"관람 인원: "+x.getTicket_num()
					);
			System.out.println("-------------------------------------");
		}
		
		
	}

	private void searchMovie() {
		System.out.print("영화 검색: ");
		String search = sc.next();
		
		ArrayList<Movies_Bean> Searchlists = mdao.searchMovie(search);
		
		if(Searchlists.size()>0) {
			showMovies(Searchlists);
		}else {
			System.out.println("조건에 맞는 영화가 없습니다.\n");
		}
		
		
	}

	//영화 전체조회출력
	private void showMovies(ArrayList<Movies_Bean> alllists) {
		System.out.println("-------------------------------------");
		System.out.println("상영관\t영화제목\t\t시작시간\t상영시간");
		java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH시mm분");
		for(Movies_Bean x : alllists) {
			System.out.println(
					x.getScreen_id()+"\t"+
					x.getTitle() + (x.getTitle().equals("치이카와 인어섬") ? "\t" : "\t\t") +
					x.getMovie_start().toLocalDateTime().format(timeFormatter) + "\t "+
					x.getMovie_time()+"분");
		}
		System.out.println("-------------------------------------");
	}

	public static void main(String[] args) {
		MovieMain mm = new MovieMain();
		
	}

}
