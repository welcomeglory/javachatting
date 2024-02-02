package java_0202;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;
//Ŭ���̾�Ʈ�� �޽����� �۽��ϴ� ������ Ŭ����
public class Sender extends Thread {
	Socket socket; // ���� ����
	DataOutputStream out; // ������ ��� ��Ʈ��
	String name; // Ŭ���̾�Ʈ�� �̸�

	// ������: ������ �޾ƿͼ� ��� ��Ʈ���� �ʱ�ȭ�ϰ� Ŭ���̾�Ʈ�� �̸��� �����ϴ� ����
	public Sender(Socket socket) {
		this.socket = socket; // ���� ����
		try {
			out = new DataOutputStream(socket.getOutputStream()); // ��� ��Ʈ�� �ʱ�ȭ
			name = "["+ socket.getInetAddress()+"."+socket.getPort()+"]"; // Ŭ���̾�Ʈ�� IP �ּҿ� ��Ʈ ��ȣ�� �����Ͽ� �̸� ����
		} catch (Exception e) {
			// ���� ó��: ����� ������ �߻��� ���
		}
	}

	// ������ ���� �޼���
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in); // Ű���� �Է��� �ޱ� ���� Scanner ��ü ����
		while(out != null) { // ��� ��Ʈ���� null�� �ƴ� ���� �ݺ�
			try {				
				out.writeUTF(name + scanner.nextLine()); 
				// Ű����κ��� �Է¹��� �޽����� Ŭ���̾�Ʈ�� ����
			} catch (Exception e) {
				e.printStackTrace(); // ���� ó��: ����� ������ �߻��� ��� ���� Ʈ���̽� ���
			}
		}
	}
}
















