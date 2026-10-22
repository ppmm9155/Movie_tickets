import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import bean.Movies_Bean;
import bean.Seat_Info_Bean;
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
			
			System.out.println("1. 전체 영화 조회");
			System.out.println("2. 상영중인 영화 검색");
			
			System.out.println("3. 영화 좌석표 조회");
			System.out.println("4. 영화별 남은 좌석 수 조회");
			
			System.out.println("5. 영화 에매");
			
			System.out.println("6. 예매 티켓 취소(삭제)");
			System.out.println("7. 예매티켓 전체 조회");
			System.out.println("8. 예매한 티켓 검색");
			
			System.out.println("9. 많이 예매한 영화 조회");
			System.out.println("10. 오전 영화 조회");
			System.out.println("11. 오후 영화 조회");
			
			System.out.println("12. 프로그램 종료");
			
			
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
				movieSelectAll();
				
				break;
			case 2:
				// 상영중인 영화검색
				searchMovie();
				
				break;
			case 3:
				// 좌석표 조회
				ArrayList<Ticket_Bean> Tlists =  findTicket();
				printSeat(Tlists);
				
				break;
			case 4:
				//영화별 남은 좌석 수 조회
				emptySeats();
				break;
			case 5:
				
				break;
			case 6:
				
				break;
			case 7:
				//예매 티켓 전체 조회
				ticketSelectAll();
				
				break;
			case 8:
				//예매한 티켓 검색
				searchTicket();
				
				break;
			case 9:
				
				break;
			case 10:
				
				break;
			case 11:
				
				break;
			case 12:
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
				break;
			default:
				System.out.println("1~13번만 입력하세요");
			}
			
		}
	}
	
	//영화별 남은 좌석수 조회
	private void emptySeats() {
//		전체좌석수 count(*) , 
//		title별  25 - count(좌석ID) = 남은좌석수
		
		ArrayList<Ticket_Bean> emptylists = tdao.epmtySeats();
		
		System.out.println("-------------------------------------------------------");
		System.out.println("영화번호\t상영관\t영화제목\t\t시작시간\t상영시간\t남은좌석");
		java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH시mm분");
		for(Ticket_Bean x : emptylists) {
			System.out.println(
					x.getMb().getMovie_id()+"\t"+
					x.getMb().getScreen_id()+"\t"+
					x.getMb().getTitle() + (x.getMb().getTitle().equals("치이카와 인어섬") ? "\t" : "\t\t") +
					x.getMb().getMovie_start().toLocalDateTime().format(timeFormatter) + "\t "+
					x.getMb().getMovie_time()+"분"+"\t"+
					(25-x.getSb().getSeat_id())+"/25");
		}
		System.out.println("-------------------------------------------------------");
		
	}

	private void ticketSelectAll() {
		ArrayList<Ticket_Bean> Tlists = tdao.ticketSelectAll();
		if(Tlists.size() > 0 ) {
			showTicket(Tlists);
		}else {
			System.out.println("예매 정보가 없습니다");
		}
		
	}

	private void movieSelectAll() {
		ArrayList<Movies_Bean> Alllists = mdao.movieSelectAll();
		if(Alllists.size() > 0) {
			showMovies(Alllists);
		}else {
			System.out.println("상영중인 영화가 없습니다.");
		}
	}

	private ArrayList<Ticket_Bean> findTicket() {
		movieSelectAll(); // 먼저 전체조회 보여주고
		
		int choiceMovie = 0 ;
		
		while(true) {
			System.out.print("좌석표 조회할 영화를(번호) 선택하세요 ");
			try {
				choiceMovie = sc.nextInt();
				break;
			}catch(InputMismatchException e) {
				System.out.println("영화 번호를 입력하세요");
				sc.next();
			}
		}
		
		
//		영화 번호와 맞는 해당 티켓 찾기
		ArrayList<Ticket_Bean> Tlists = tdao.findTicket(choiceMovie);
		return Tlists;
		
	}
	
	private void printSeat(ArrayList<Ticket_Bean> Tlists) {
		String[][] seatArray = 
			{
					{"□","□","□","□","□"},
					{"□","□","□","□","□"},
					{"□","□","□","□","□"},
					{"□","□","□","□","□"},
					{"□","□","□","□","□"}
			};
//		65 66 67 68 69
//		먼저 티켓있으면 자리채우기
//		A B C D E 대문자 아스키 , 1 2 3 4 5
		
		if(Tlists.size() > 0) {
			int row = 0;
			int col = 0;
			
			for(Ticket_Bean x : Tlists) {
				char crow = x.getSb().getSeat_row().charAt(0);
				int irow = (int)crow;
				row = irow-65;
				col = (x.getSb().getSeat_col()-1);
				
				seatArray[row][col]="■";
			}
		}
		
		// 열 번호 1 2 3 4 5 출력
		System.out.print("\t"); 
		for (int i = 1; i <= seatArray[0].length; i++) {
				System.out.print(i + "\t");
		}
		System.out.println("\n-----------------------------------------------");
		
		//채워진후 출력
		for (int i = 0; i < seatArray.length; i++) {
			char rowLabel = (char)(i + 65);
			System.out.print(rowLabel + "\t");
			for (int j = 0; j < seatArray[i].length; j++) {
		
					System.out.print(seatArray[i][j]+"\t");
				
			}
			System.out.println();
		}
		System.out.println();
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
			System.out.println("-----------------------------------------------");
			System.out.println(
					"영화 제목: "+tb.getMb().getTitle()+"\n"+
					"상영 시작 시간: "+tb.getMb().getMovie_start().toLocalDateTime().format(timeFormatter)+"\n"+
					"상영 시간: "+tb.getMb().getMovie_time()+"분 "+"\n"+
					"상영관: "+tb.getMb().getScreen_id()+"관 "+"\n"+
					"관람 좌석: "+tb.getSb().getSeat_row()+"행"+tb.getSb().getSeat_col()+"열 "+"\n"+
					"예매 번호: "+tb.getTicket_id()+"\n"+
					"관람 인원: "+tb.getTicket_num()
						);
			System.out.println("-----------------------------------------------");
		}else {
			System.out.println("조건에 맞는 예매정보가 없습니다.\n");
		}
	}

	private void showTicket(ArrayList<Ticket_Bean> Tlists) {
		System.out.println("-----------------------------------------------");
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
			System.out.println("-----------------------------------------------");
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
		System.out.println("-----------------------------------------------");
		System.out.println("영화번호\t상영관\t영화제목\t\t시작시간\t상영시간");
		java.time.format.DateTimeFormatter timeFormatter = java.time.format.DateTimeFormatter.ofPattern("HH시mm분");
		for(Movies_Bean x : alllists) {
			System.out.println(
					x.getMovie_id()+"\t"+
					x.getScreen_id()+"\t"+
					x.getTitle() + (x.getTitle().equals("치이카와 인어섬") ? "\t" : "\t\t") +
					x.getMovie_start().toLocalDateTime().format(timeFormatter) + "\t "+
					x.getMovie_time()+"분");
		}
		System.out.println("-----------------------------------------------");
	}

	public static void main(String[] args) {
		MovieMain mm = new MovieMain();
		
	}

}
