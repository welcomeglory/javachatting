package firstproject;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
//클라이언트로 메시지를 송신하는 스레드 클래스
class Sender extends Thread {
	Socket socket; // 소켓 변수
	DataOutputStream out; // 데이터 출력 스트림
	// 생성자: 소켓을 받아와서 출력 스트림을 초기화하고 클라이언트의 이름을 설정하는 역할
	public Sender(Socket socket) {
		this.socket = socket; // 소켓 설정
		try {
			out = new DataOutputStream(socket.getOutputStream()); // 출력 스트림 초기화
		} catch (Exception e) {
			// 예외 처리: 입출력 오류가 발생할 경우
		}
	}
	// 스레드 실행 메서드
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in); // 키보드 입력을 받기 위한 Scanner 객체 생성
		while(!out.equals("그만")) { // 출력 스트림이 null이 아닌 동안 반복
			try {				
				out.writeUTF(scanner.nextLine()); 
				// 키보드로부터 입력받은 메시지를 클라이언트로 전송
			} catch (Exception e) {
				e.printStackTrace(); // 예외 처리: 입출력 오류가 발생할 경우 스택 트레이스 출력
			}
//			if(out.equals("그만")) {
//				break;
//			}
		}
	}
}
//서버로부터 메시지를 수신하는 스레드 클래스
class Receiver extends Thread {
	Socket socket; // 소켓 변수
	DataInputStream in; // 데이터 입력 스트림
	String name; // 클라이언트의 이름

	// 생성자: 소켓을 받아서 입력 스트림을 초기화하는 역할
	public Receiver(Socket socket) {
		this.socket = socket; // 소켓 설정
		try {
			in = new DataInputStream(socket.getInputStream()); // 입력 스트림 초기화
		} catch (Exception e) {
			// 예외 처리: 입출력 오류가 발생할 경우
		}
	}
	// 스레드 실행 메서드
	@Override
	public void run() {
		while (!in.equals("그만")) { // 입력 스트림이 null이 아닌 동안 반복
			try {
				System.out.println(in.readUTF()); // 서버로부터 메시지를 읽고 콘솔에 출력
			} catch (Exception e) {
				e.printStackTrace(); // 예외 처리: 입출력 오류가 발생할 경우 스택 트레이스 출력
			}
//			if(in.equals("그만")) {
//				break;
//			}
		}
	}
}
//클라이언트 역할을 하는 클래스
public class ChatClient {
	public static void main(String[] args) {
		Socket socket = null; // 소켓 변수 선언 및 초기화
		try {
			// 로컬 호스트(localhost)의 7777번 포트로 소켓을 생성하여 서버에 연결
			socket = new Socket("localhost", 7777);
			System.out.println("서버에 연결되었습니다."); // 연결 성공 메시지 출력
			// 송신 스레드와 수신 스레드 생성하여 실행
			Sender sender = new Sender(socket); // 송신 스레드 객체 생성
			Receiver receiver = new Receiver(socket); // 수신 스레드 객체 생성
			sender.start(); // 송신 스레드 시작
			receiver.start(); // 수신 스레드 시작
			
		} catch (Exception e) {
			e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
		}
	}
}

