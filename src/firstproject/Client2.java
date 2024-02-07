package firstproject;

import java.io.*;
import java.net.*;

public class Client2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // ���� �ּҿ� ��Ʈ ��ȣ
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("1. ��� ��ȸ");
                System.out.println("2. ���");
                System.out.println("3. ����");
                System.out.println("4. ����");
                System.out.println("5. ����");
                System.out.print("���ϴ� �۾��� �����ϼ���: ");
                String choice = consoleReader.readLine();

                switch (choice) {
                    case "1":
                        out.println("LIST");
                        System.out.println("�Խñ� ���:");
                        String message;
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        break;
                    case "2":
                        out.println("ADD");
                        System.out.print("������ �Է��ϼ���: ");
                        String title = consoleReader.readLine();
                        System.out.print("������ �Է��ϼ���: ");
                        String content = consoleReader.readLine();
                        System.out.print("�ۼ��ڸ� �Է��ϼ���: ");
                        String author = consoleReader.readLine();
                        out.println(title);
                        out.println(content);
                        out.println(author);
                        break;
                    case "3":
                        out.println("LIST");
                        System.out.println("�Խñ� ���:");
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        System.out.print("������ �Խñ��� �ε����� �Է��ϼ���: ");
                        int updateIndex = Integer.parseInt(consoleReader.readLine());
                        System.out.print("������ �׸��� �����ϼ��� (title/content/author): ");
                        String fieldToUpdate = consoleReader.readLine();
                        System.out.print("���ο� ���� �Է��ϼ���: ");
                        String updatedValue = consoleReader.readLine();
                        out.println("UPDATE");
                        out.println(updateIndex);
                        out.println(fieldToUpdate);
                        out.println(updatedValue);
                        break;
                    case "4":
                        out.println("LIST");
                        System.out.println("�Խñ� ���:");
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        System.out.print("������ �Խñ��� �ε����� �Է��ϼ���: ");
                        int deleteIndex = Integer.parseInt(consoleReader.readLine());
                        out.println("DELETE");
                        out.println(deleteIndex);
                        break;
                    case "5":
                        out.println("EXIT");
                        socket.close();
                        return;
                    default:
                        System.out.println("�ùٸ��� ���� �����Դϴ�.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}