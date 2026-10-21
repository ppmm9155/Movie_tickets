import java.util.Scanner;

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
				mdao.movieSelectAll();
				break;
			case 2:
				
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
				
				break;
			case 8:
				
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

	public static void main(String[] args) {
		MovieMain mm = new MovieMain();
		
	}

}
