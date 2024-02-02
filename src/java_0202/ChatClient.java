package java_0202;

import java.net.Socket;

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