package java_0202;
import java.io.DataInputStream;
import java.net.Socket;

//서버로부터 메시지를 수신하는 스레드 클래스
public class Receiver extends Thread {
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
		while (in != null) { // 입력 스트림이 null이 아닌 동안 반복
			try {
				System.out.println(in.readUTF()); // 서버로부터 메시지를 읽고 콘솔에 출력
			} catch (Exception e) {
				e.printStackTrace(); // 예외 처리: 입출력 오류가 발생할 경우 스택 트레이스 출력
			}
		}
	}
}




























