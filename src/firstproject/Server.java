package firstproject;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static Set<PrintWriter> clients = new HashSet<>();
    private static List<Post> posts = new ArrayList<>();

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

        public Handler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                clients.add(out);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Ŭ���̾�Ʈ �޽���: " + message);
                    String[] parts = message.split("\\|");
                    int command = Integer.parseInt(parts[0]);
                    switch (command) {
                        case 1:
                            out.println("�Խù� ���:");
                            for (int i = 0; i < posts.size(); i++) {
                                out.println((i + 1) + ". " + posts.get(i).getTitle());
                            }
                            break;
                        case 2:
                            if (parts.length >= 4) {
                                String title = parts[1];
                                String content = parts[2];
                                String author = parts[3];
                                posts.add(new Post(title, content, author));
                                out.println("�Խù��� ��ϵǾ����ϴ�.");
                            } else {
                                out.println("����, ����, �ۼ��ڸ� ��� �Է��ϼ���.");
                            }
                            break;
                        case 3:
                            if (parts.length >= 2) {
                                int postIndex = Integer.parseInt(parts[1]) - 1;
                                if (postIndex >= 0 && postIndex < posts.size()) {
                                    out.println("�Խù� ����:");
                                    out.println(posts.get(postIndex).getContent());
                                } else {
                                    out.println("�ش� ��ȣ�� �Խù��� �������� �ʽ��ϴ�.");
                                }
                            } else {
                                out.println("���� ���� �Խù� ��ȣ�� �Է��ϼ���.");
                            }
                            break;
                        case 4:
                            if (parts.length >= 2) {
                                int deleteIndex = Integer.parseInt(parts[1]) - 1;
                                if (deleteIndex >= 0 && deleteIndex < posts.size()) {
                                    posts.remove(deleteIndex);
                                    out.println("�Խù��� �����Ǿ����ϴ�.");
                                } else {
                                    out.println("�ش� ��ȣ�� �Խù��� �������� �ʽ��ϴ�.");
                                }
                            } else {
                                out.println("������ �Խù� ��ȣ�� �Է��ϼ���.");
                            }
                            break;
                        case 0:
                            out.println("���� ������ �����մϴ�.");
                            return;
                        default:
                            out.println("�߸��� ��ɾ��Դϴ�.");
                    }
                }
            } catch (IOException e) {
                System.out.println("Ŭ���̾�Ʈ ���� ����: " + e.getMessage());
            } finally {
                if (out != null) {
                    clients.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("���� �ݱ� ����: " + e.getMessage());
                }
            }
        }
    }

    private static class Post {
        private String title;
        private String content;
        private String author;

        public Post(String title, String content, String author) {
            this.title = title;
            this.content = content;
            this.author = author;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getAuthor() {
            return author;
        }
    }
}