package java_0202;
import java.net.ServerSocket;
import java.net.Socket;

// 채팅 서버를 시작하는 클래스
public class ChatServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null; // 서버 소켓
		Socket socket = null; // 클라이언트 소켓

		try {
			serverSocket = new ServerSocket(7777); // 7777번 포트에서 서버 소켓 생성
			System.out.println("서버가 작동되었습니다."); // 서버가 작동됨을 표시하는 메시지 출력
			socket = serverSocket.accept(); // 클라이언트의 연결 요청을 기다리고 수락하여 클라이언트 소켓을 생성

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