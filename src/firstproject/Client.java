package firstproject;
import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("������ ����Ǿ����ϴ�.");

            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("���� ���� ����: " + e.getMessage());
                }
            }).start();

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                switch (userInput) {
                    case "1": // �Խ��� ��� ����
                        out.println("1");
                        break;
                    case "2": // �Խù� ���
                        out.println("2");
                        System.out.println("������ �Է��ϼ���:");
                        String title = consoleReader.readLine().replace("|", "\\|"); // ������ ���� ó��
                        System.out.println("������ �Է��ϼ���:");
                        String content = consoleReader.readLine().replace("|", "\\|"); // ������ ���� ó��
                        System.out.println("�ۼ��ڸ� �Է��ϼ���:");
                        String author = consoleReader.readLine().replace("|", "\\|"); // ������ ���� ó��
                        out.println(title + "|" + content + "|" + author);
                        break;
                    case "3": // �Խù� ����
                        out.println("3");
                        System.out.println("������ �Խù��� ��ȣ�� �Է��ϼ���:");
                        int editIndex = Integer.parseInt(consoleReader.readLine());
                        System.out.println("������ ������ ������ �����ϼ��� (����/����/�ۼ���):");
                        String editType = consoleReader.readLine();
                        System.out.println("������ ������ �Է��ϼ���:");
                        String editContent = consoleReader.readLine().replace("|", "\\|"); // ������ ���� ó��
                        out.println(editIndex + "|" + editType + "|" + editContent);
                        break;
                    case "4": // �Խù� ����
                        out.println("4");
                        System.out.println("������ �Խù��� ��ȣ�� �Է��ϼ���:");
                        int deleteIndex = Integer.parseInt(consoleReader.readLine());
                        out.println(deleteIndex);
                        break;
                    case "0": // ����
                        out.println("0");
                        return;
                    default:
                        System.out.println("�߸��� �Է��Դϴ�.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Ŭ���̾�Ʈ ����: " + e.getMessage());
        }
    }
}
