package java_0202;
import java.net.ServerSocket;
import java.net.Socket;

// ä�� ������ �����ϴ� Ŭ����
public class ChatServer {
	public static void main(String[] args) {
		ServerSocket serverSocket = null; // ���� ����
		Socket socket = null; // Ŭ���̾�Ʈ ����

		try {
			serverSocket = new ServerSocket(7777); // 7777�� ��Ʈ���� ���� ���� ����
			System.out.println("������ �۵��Ǿ����ϴ�."); // ������ �۵����� ǥ���ϴ� �޽��� ���
			socket = serverSocket.accept(); // Ŭ���̾�Ʈ�� ���� ��û�� ��ٸ��� �����Ͽ� Ŭ���̾�Ʈ ������ ����

			// �۽� ������� ���� ������ �����Ͽ� ����
			Sender sender = new Sender(socket); // �۽� ������ ��ü ����
			Receiver receiver = new Receiver(socket); // ���� ������ ��ü ����
			sender.start(); // �۽� ������ ����
			receiver.start(); // ���� ������ ����

		} catch (Exception e) {
			e.printStackTrace(); // ���� �߻� �� ���� Ʈ���̽� ���
		}
	}
}