import java.sql.SQLException;
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
	
	//필드
	private int menu = 0;
//	선택한 영화번호 저장
	private int selectedMovieId = 0;
	
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
				ArrayList<Ticket_Bean> Tlists =  findTicket(menu);
				if(Tlists == null) {
					System.out.println("좌석표 조회가 취소 되었습니다 \n");
					continue;
				}
				printSeat(Tlists);
				
				break;
			case 4:
				//영화별 남은 좌석 수 조회
				emptySeats();
				break;
			case 5:
				//영화 예매
				ArrayList<Ticket_Bean> NewTlists =  findTicket(menu);
				if(NewTlists == null) {
					System.out.println("예매 좌석 조회가 취소 되었습니다 \n");
					continue;
				}
				

				addTicket(selectedMovieId, NewTlists);
				
				
				break;
			case 6:
				//예매 티켓 삭제
				deleteTicket();
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
				//많이 예매한 영화조회
				
				break;
			case 10:
				//오전 영화 조회
				amSelectMovie();
				
				break;
			case 11:
				//오후 영화 조회
				pmSelectMovie();
				
				break;
			case 12:
				System.out.println("프로그램을 종료합니다");
				System.exit(0);
				break;
			default:
				System.out.println("1~12번만 입력하세요 \n");
			}
			
		}
	}
	
	private ArrayList<Ticket_Bean> addTicket(int selectedMovieId, ArrayList<Ticket_Bean> newTlists) {
		//영화예매 를 최종적으로 다하면 -> 그 다한 예매정보(상태)를 가지고 -> insert 한다
		//그전까지는 insert 일절안함
		
		
//		보통 영화 좌석표 조회나 , 영화 예매를 할때
//		남은좌석도 보여주잖아
//		전체 좌석표 조회가 아닌 -> 남은좌석표도 조회가 가능하게 
//		-> emptySeats(); 써서 해결함
		while(true) {
			//좌석출력
			printSeat(newTlists);
			
			String row = null;
			int col = 0;
			System.out.println("뒤로 가기 하려면 n을 입력하세요");
			System.out.println("메뉴로 가려면 N을 입력하세요");
			System.out.println("");
			System.out.print("예매할 좌석(행 열) 을 입력하세요 ");
//			첫번째 입력에서 A B C D E 와 N 만되게 나머지 숫자,문자,특문 안되게
			row=sc.next(); 
			
//			기존 뒤로 가기 후 영화를 다시 선택하면 메뉴로 돌아가는 문제 ->
//			AI는 이렇게 추천함
			if (row.equals("n")) {
			    // 영화를 다시 선택하고 해당 영화의 예매 목록을 조회
			    newTlists = findTicket(5);

			    // 영화 선택을 취소하면 예매 메서드 종료
			    if (newTlists == null) {
			        return null;
			    }

			    // 재선택한 영화 번호를 필드에서 가져와 매개변수에 반영
			    selectedMovieId = this.selectedMovieId;

			    // 반복문 처음으로 돌아가 새 영화의 좌석표 출력
			    continue;

			} else if (row.equals("N")) {
			    break;
			}
			
//			숫자 이기때문에 InputMismatchException 예외처리가 발생함
			while(true) {
				try {
//					
					col=sc.nextInt();
//					조건문으로 숫자 1,2,3,4,5만 입력되게함 나머지 숫자면 
					break;
				}catch(InputMismatchException e) {
					System.out.println("(열) 입력은 숫자로만 입력하세요");
					sc.next();
				}
			}
			
			
			System.out.println("선택한 좌석번호: "+row+"행"+col+"열\n");
//			Ticket_Bean tb = new Ticket_Bean();
		
//			insert 하는데 필요한 정보 movie_id , row 와 col 만으로 seat_id 조회하기
			
//			먼저 row와 col을 seat_id 로 조회
			
			int seat_id = 0;
			//movie_id 없애고 movie_id를 필드에있는걸 가져오는걸로 
//			int movie_id = 0;
			
			Seat_Info_Bean seatInfo= sdao.findSeat_id(row , col);
			
			if (seatInfo == null) {
			    System.out.println("좌석 정보를 찾을수 없습니다");
			    continue;
			}
			
			seat_id = seatInfo.getSeat_id();
			
			//
			Boolean flag = tdao.isReserved(selectedMovieId, seat_id);
			
			//조회 실패 시 예매를 하지않음
			if (flag == null) {
			    System.out.println("좌석 예매 여부를 확인하지 못했습니다\n");
			    return null;
			}

			//이미 예매된 좌석이면 -> 좌석표를 갱신하고 다시 입력
			if (flag) {
			    System.out.println("이미 예매된 좌석입니다 다시 선택하세요\n");
			    newTlists = tdao.findTicket(selectedMovieId);
			    continue;
			}
			
			
			// insert에 필요한 정보 출력 movie_id , seat_id
//			System.out.println("테스트");
//			System.out.println("movie_id: "+selectedMovieId);
//			System.out.println("seat_id: "+seat_id+'\n');
			
			// 예매 확정 전에 선택한 영화와 좌석정보 출력
			Movies_Bean movie = mdao.findMovie(selectedMovieId);

			if (movie == null) {
			    System.out.println("영화 정보를 불러오지 못했습니다 \n");
			    return null;
			}

			java.time.format.DateTimeFormatter formatter =
			        java.time.format.DateTimeFormatter.ofPattern("HH시mm분");

			System.out.println("-----------------------------------------------");
			System.out.println("영화 제목: " + movie.getTitle());
			System.out.println("상영 시작 시간: "
			        + movie.getMovie_start().toLocalDateTime().format(formatter));
			System.out.println("상영 시간: " + movie.getMovie_time() + "분");
			System.out.println("상영관: " + movie.getScreen_id() + "관");
			System.out.println("관람 인원: 1");
			System.out.println("선택 좌석: " + row + "행 " + col + "열");
			System.out.println("-----------------------------------------------");
			
			
			// 최종 예매 확인
			while (true) {
			    System.out.print(row + "행 " + col + "열을 예매하시겠습니까? (Y/N): ");
			    String answer = sc.next();

			    if (answer.equalsIgnoreCase("Y")) {
			        break;
			    } else if (answer.equalsIgnoreCase("N")) {
			        System.out.println("예매를 취소했습니다 \n");
			        return null;
			    } else {
			        System.out.println("Y 또는 N을 입력하세요.");
			    }
			}
			
			// 미리 좌석표 띄어둘 예매정보 추가 , 최종예매 여부 확인후 insert 함
			int cnt = tdao.insertTicket(selectedMovieId,seat_id);
			
			
			
//			if(cnt ==1) {
////				추가가 되면 좌석표띄우고
//				
////				좌석표 띄울때 예매정보 추가된것도 포함 안나오고 띄우는문제가 생김 
////				commit 문제인거같은데 모르겟네
////				▣
//				
//				// 예매 성공 하면 해당 영화의 예매 목록을 다시 조회
//				newTlists = tdao.findTicket(selectedMovieId);
//				// 새로 예매된 좌석까지 반영해서 출력
//			    printSeat(newTlists);
//			}else {
////				추가를 못하면 좌석표 안띄우고
//				System.out.println("예매에 실패 했습니다");
//				break;
//			}
			
			if (cnt == 1) {
			    // 저장된 예매 정보로 갱신
			    newTlists = tdao.findTicket(selectedMovieId);
			    
			    System.out.println("예매가 완료되었습니다");

			    // 선택한 영화의 예매 목록에서 해당 좌석의 예매 번호 출력
			    for (Ticket_Bean ticket : newTlists) {
			        if (ticket.getSb().getSeat_row().equals(row)
			                && ticket.getSb().getSeat_col() == col) {

			            System.out.println("예매 번호: " + ticket.getTicket_id()+"\n");
			            break;
			        }
			    }
			    
			} else {
			    System.out.println("예매에 실패했습니다.");
			    break;
			}
			
//			삭제는?
			
			
//			tdao.insertTicket();
			
//			--> 선택한 좌석번호(아직 예매아님) 좌석표 띄움
//			근데 좌석표를 띄우려면 예매정보가 필요함 
//			미리 예매 정보 insert 시키고 띄우고 
//			예매 취소 N 이나 뒤로가기n 누르면 예매 정보 delete 로 함
//			->다시 뒤로가기후 -> 좌석표 띄우고 예매할 좌석을 정하고
//			
//			예매하시겠습니까? 에서 N 이면 취소 아예 메뉴로
			
			
			
//			최종적으로 좌석표를 확정하면 -> insert 예매한거 냅둠
			
			
			break;
		}
		return newTlists;
		
		
	}

	private void pmSelectMovie() {
		ArrayList<Movies_Bean> pmlists = mdao.pmSelectMovie();
		
		if(pmlists.size()>0) {
			showMovies(pmlists);
		}else {
			System.out.println("오후에 상영중인 영화가 없습니다.");
		}
		
	}

	private void amSelectMovie() {
		ArrayList<Movies_Bean> amlists = mdao.amSelectMovie();
		
		if(amlists.size()>0) {
			showMovies(amlists);
		}else {
			System.out.println("오전에 상영중인 영화가 없습니다.");
		}
	}

	private void deleteTicket() {
		// 예매 정보가 없으면 메뉴로 돌아감
		try {
		    if (!tdao.haveTicket()) {
		        System.out.println("예매한 티켓이 없습니다\n");
		        return;
		    }
		} catch (SQLException e) {
		    System.out.println("예매 정보를 확인하는 중 오류가 발생했습니다\n");
		    e.printStackTrace();
		    return;
		}
		
		int delete = 0;
		while(true) {
			System.out.print("삭제할 티켓 번호를 입력하세요 ");
			try {
				delete = sc.nextInt();
				break;
			}catch (Exception e) {
				System.out.println("옳바른 티켓 번호를 입력하세요");
				sc.next();
			}
		}
		
		int cnt = tdao.deleteTicket(delete);
		
		if(cnt == 1) {
			System.out.println("예매 취소 했습니다 \n");
		}else {
			System.out.println("에매 취소를 실패했습니다 \n");
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
					(25-x.getSb().getSeat_id())+"/"+25);
		}
		System.out.println("-------------------------------------------------------");
		
	}

	private void ticketSelectAll() {
		ArrayList<Ticket_Bean> Tlists = tdao.ticketSelectAll();
		if(Tlists.size() > 0 ) {
			showTicket(Tlists);
		}else {
			System.out.println("예매한 티켓 정보가 없습니다\n");
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

	private ArrayList<Ticket_Bean> findTicket(int menu) {
		emptySeats(); // 먼저 남은좌석수도 있는 전체 조회 보여주고
		// 예매가능한 영화번호만 입력되게  예매가능한 1~3만 영화번호 입력되게 그걸
		//예매가능한것을 조회해야해 영화번호를 영화번호 첫값과 끝값 이렇게 조회
		// N을 입력하면 취소가능하게
		//
		
		// int라서 에초에 문자가들어오면 예외처리로감
		// String 으로해서 N이면 빠져나오고 N이아닌 (N이 아닌 다른문자를 넣으면?)
		//숫자면 영화관 번호에 포함되는지 보고 아니면 영화관 번호에 없는 번호
//		있으면 영화관번호 니까 String을 int로 바꾼후에 tdao.findTicket 에 넣자
		
		String strMovieNum = null ;
		
//		int choiceMovie = Integer.parseInt(strMovieNum); 
		int choiceMovie = 0;
		while(true) {
			// menu 3과 menu 5 의 안내문구다르게
			if(menu ==3) {
				System.out.println("조회를 취소할려면 N 을 입력하세요");
				System.out.print("좌석표 조회할 영화(번호)를 선택하세요 ");
			}else if(menu ==5) {
				System.out.println("조회를 취소할려면 N 을 입력하세요");
				System.out.print("예매할 영화(번호)를 선택하세요 ");
			}
			
//			NullPointerException
			strMovieNum=sc.next();
			if(strMovieNum.equals("N")) {
				// 따지고보면 case를 빠져나가서 menu로 가는건데
				//N 입력시 아예 메뉴선택창으로 가게 어떻게 해야하지?
				return null;
				
				// 입력한 데이터가 숫자인지 아니면 문자인지 문자열에서 판별해야함
			}else if(strMovieNum.matches("-?\\d+")) {
				choiceMovie = Integer.parseInt(strMovieNum); 
				// 입력한 번호가 영화번호에 포함되는지 , 구분
				
				boolean flag = mdao.isMovieId(choiceMovie); // 반환값을 boolean
				
//				구분후에 맞으면 break , 아니면 
				if(flag) {
//					입력한 영화 번호가 있으면 선택한 번호로
					selectedMovieId = choiceMovie;
					break;
				}else {
					System.out.println("입력한 번호가 영화번호에 없습니다\n");
				}
				
				
				
			}else {
				System.out.println("잘못된 입력입니다 숫자 또는 N 만 입력하세요 \n");
			}


			
			// 입력한 번호가 영화번호가 있는지 Movie 테이블의 sql문으로 확인후  
		}
		
		
		
		ArrayList<Ticket_Bean> Tlists = null;
		// 문자열나올시 NumberFormatException 이 발생함
		Tlists = tdao.findTicket(choiceMovie);
		

		
		
//		return null;
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
		// 예매 정보가 없으면 메뉴로 돌아감
		try {
		    if (!tdao.haveTicket()) {
		        System.out.println("예매한 티켓이 없습니다\n");
		        return;
		    }
		} catch (SQLException e) {
		    System.out.println("예매 정보를 확인하는 중 오류가 발생했습니다\n");
		    e.printStackTrace();
		    return;
		}
		
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
		String search = sc.nextLine();
		
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
