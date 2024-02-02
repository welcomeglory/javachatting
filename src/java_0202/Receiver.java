package java_0202;
import java.io.DataInputStream;
import java.net.Socket;

//�����κ��� �޽����� �����ϴ� ������ Ŭ����
public class Receiver extends Thread {
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
		while (in != null) { // �Է� ��Ʈ���� null�� �ƴ� ���� �ݺ�
			try {
				System.out.println(in.readUTF()); // �����κ��� �޽����� �а� �ֿܼ� ���
			} catch (Exception e) {
				e.printStackTrace(); // ���� ó��: ����� ������ �߻��� ��� ���� Ʈ���̽� ���
			}
		}
	}
}




























