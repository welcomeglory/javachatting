package java_0202;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
//클라이언트로 메시지를 송신하는 스레드 클래스
public class Sender extends Thread {
	Socket socket; // 소켓 변수
	DataOutputStream out; // 데이터 출력 스트림
	String name; // 클라이언트의 이름

	// 생성자: 소켓을 받아와서 출력 스트림을 초기화하고 클라이언트의 이름을 설정하는 역할
	public Sender(Socket socket) {
		this.socket = socket; // 소켓 설정
		try {
			out = new DataOutputStream(socket.getOutputStream()); // 출력 스트림 초기화
			name = "["+ socket.getInetAddress()+"."+socket.getPort()+"]"; // 클라이언트의 IP 주소와 포트 번호를 조합하여 이름 설정
		} catch (Exception e) {
			// 예외 처리: 입출력 오류가 발생할 경우
		}
	}

	// 스레드 실행 메서드
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in); // 키보드 입력을 받기 위한 Scanner 객체 생성
		while(out != null) { // 출력 스트림이 null이 아닌 동안 반복
			try {				
				out.writeUTF(name + scanner.nextLine()); 
				// 키보드로부터 입력받은 메시지를 클라이언트로 전송
			} catch (Exception e) {
				e.printStackTrace(); // 예외 처리: 입출력 오류가 발생할 경우 스택 트레이스 출력
			}
		}
	}
}
















