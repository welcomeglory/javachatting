package firstproject;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static List<String> posts = new ArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("������ ���۵Ǿ����ϴ�.");
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("���� ����: " + e.getMessage());
        }
    }

    private static class Handler extends Thread {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println(message); // Ŭ���̾�Ʈ�� ���� �޽��� ���
                    String[] parts = message.split("\\|");
                    int command;
                    try {
                        command = Integer.parseInt(parts[0]);
                    } catch (NumberFormatException e) {
                        out.println("���ڸ� �Է��ϼ���."); // ���ڸ� �Է����� �ʾ��� �� �ȳ� �޽��� ����
                        continue; // ���� ������ ����
                    }
                    switch (command) {
                        case 1: // �Խ��� ��� ����
                            out.println("�Խ��� ���:");
                            for (int i = 0; i < posts.size(); i++) {
                                out.println((i + 1) + ". " + posts.get(i));
                            }
                            break;
                        case 2: // �Խù� ���
                            out.println("������ �Է��ϼ���:");
                            String title = in.readLine().replace("|", "\\|"); // ������ ���� ó��
                            out.println("������ �Է��ϼ���:");
                            String content = in.readLine().replace("|", "\\|"); // ������ ���� ó��
                            out.println("�ۼ��ڸ� �Է��ϼ���:");
                            String author = in.readLine().replace("|", "\\|"); // ������ ���� ó��
                            posts.add(title + " | " + content + " | " + author);
                            out.println("�Խù��� ��ϵǾ����ϴ�.");
                            break;
                        case 3: // �Խù� ����
                            out.println("������ �Խù� ��ȣ�� �Է��ϼ���:");
                            int editIndex;
                            try {
                                editIndex = Integer.parseInt(in.readLine()) - 1;
                            } catch (NumberFormatException e) {
                                out.println("���ڸ� �Է��ϼ���.");
                                continue;
                            }
                            if (editIndex >= 0 && editIndex < posts.size()) {
                                out.println("������ ������ �����ϼ��� (����, ����, �ۼ���):\n");
                                String editType = in.readLine();
                                out.println("������ ������ �Է��ϼ���:");
                                String editContent = in.readLine().replace("|", "\\|"); // ������ ���� ó��
                                String[] postParts = posts.get(editIndex).split(" \\| ");
                                if (editType.equals("����")) {
                                    postParts[0] = editContent;
                                } else if (editType.equals("����")) {
                                    postParts[1] = editContent;
                                } else if (editType.equals("�ۼ���")) {
                                    postParts[2] = editContent;
                                }
                                posts.set(editIndex, postParts[0] + " | " + postParts[1] + " | " + postParts[2]);
                                out.println("�Խù��� �����Ǿ����ϴ�.");
                            } else {
                                out.println("�ش� ��ȣ�� �Խù��� �������� �ʽ��ϴ�.");
                            }
                            break;
                        case 4: // �Խù� ����
                            out.println("������ �Խù� ��ȣ�� �Է��ϼ���:");
                            int deleteIndex;
                            try {
                                deleteIndex = Integer.parseInt(in.readLine()) - 1;
                            } catch (NumberFormatException e) {
                                out.println("���ڸ� �Է��ϼ���.");
                                continue;
                            }
                            if (deleteIndex >= 0 && deleteIndex < posts.size()) {
                                posts.remove(deleteIndex);
                                out.println("�Խù��� �����Ǿ����ϴ�.");
                            } else {
                                out.println("�ش� ��ȣ�� �Խù��� �������� �ʽ��ϴ�.");
                            }
                            break;
                        case 0: // ����
                            out.println("���� ������ �����մϴ�.");
                            return;
                        default:
                            out.println("�߸��� ��ɾ��Դϴ�.");
                    }
                }
            } catch (IOException e) {
                System.out.println("Ŭ���̾�Ʈ ���� ����: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("���� �ݱ� ����: " + e.getMessage());
                }
            }
        }
    }
}