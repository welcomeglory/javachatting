package firstproject;
import java.net.Socket;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;
//Ŭ���̾�Ʈ�� �޽����� �۽��ϴ� ������ Ŭ����
class Sender extends Thread {
	Socket socket; // ���� ����
	DataOutputStream out; // ������ ��� ��Ʈ��
	// ������: ������ �޾ƿͼ� ��� ��Ʈ���� �ʱ�ȭ�ϰ� Ŭ���̾�Ʈ�� �̸��� �����ϴ� ����
	public Sender(Socket socket) {
		this.socket = socket; // ���� ����
		try {
			out = new DataOutputStream(socket.getOutputStream()); // ��� ��Ʈ�� �ʱ�ȭ
		} catch (Exception e) {
			// ���� ó��: ����� ������ �߻��� ���
		}
	}
	// ������ ���� �޼���
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in); // Ű���� �Է��� �ޱ� ���� Scanner ��ü ����
		while(!out.equals("�׸�")) { // ��� ��Ʈ���� null�� �ƴ� ���� �ݺ�
			try {				
				out.writeUTF(scanner.nextLine()); 
				// Ű����κ��� �Է¹��� �޽����� Ŭ���̾�Ʈ�� ����
			} catch (Exception e) {
				e.printStackTrace(); // ���� ó��: ����� ������ �߻��� ��� ���� Ʈ���̽� ���
			}
//			if(out.equals("�׸�")) {
//				break;
//			}
		}
	}
}
//�����κ��� �޽����� �����ϴ� ������ Ŭ����
class Receiver extends Thread {
	Socket socket; // ���� ����
	DataInputStream in; // ������ �Է� ��Ʈ��
	String name; // Ŭ���̾�Ʈ�� �̸�

	// ������: ������ �޾Ƽ� �Է� ��Ʈ���� �ʱ�ȭ�ϴ� ����
	public Receiver(Socket socket) {
		this.socket = socket; // ���� ����
		try {
			in = new DataInputStream(socket.getInputStream()); // �Է� ��Ʈ�� �ʱ�ȭ
		} catch (Exception e) {
			// ���� ó��: ����� ������ �߻��� ���
		}
	}
	// ������ ���� �޼���
	@Override
	public void run() {
		while (!in.equals("�׸�")) { // �Է� ��Ʈ���� null�� �ƴ� ���� �ݺ�
			try {
				System.out.println(in.readUTF()); // �����κ��� �޽����� �а� �ֿܼ� ���
			} catch (Exception e) {
				e.printStackTrace(); // ���� ó��: ����� ������ �߻��� ��� ���� Ʈ���̽� ���
			}
//			if(in.equals("�׸�")) {
//				break;
//			}
		}
	}
}
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

