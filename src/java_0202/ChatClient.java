package java_0202;

import java.net.Socket;

//Ŭ���̾�Ʈ ������ �ϴ� Ŭ����
public class ChatClient {
	public static void main(String[] args) {
		Socket socket = null; // ���� ���� ���� �� �ʱ�ȭ
		try {
			// ���� ȣ��Ʈ(localhost)�� 7777�� ��Ʈ�� ������ �����Ͽ� ������ ����
			socket = new Socket("localhost", 7777);
			System.out.println("������ ����Ǿ����ϴ�."); // ���� ���� �޽��� ���

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