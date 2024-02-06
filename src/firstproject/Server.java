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
            System.out.println("서버가 시작되었습니다.");
            while (true) {
                new Handler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.out.println("서버 오류: " + e.getMessage());
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
                    System.out.println("클라이언트 메시지: " + message);
                    String[] parts = message.split("\\|");
                    int command = Integer.parseInt(parts[0]);
                    switch (command) {
                        case 1:
                            out.println("게시물 목록:");
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
                                out.println("게시물이 등록되었습니다.");
                            } else {
                                out.println("제목, 내용, 작성자를 모두 입력하세요.");
                            }
                            break;
                        case 3:
                            if (parts.length >= 2) {
                                int postIndex = Integer.parseInt(parts[1]) - 1;
                                if (postIndex >= 0 && postIndex < posts.size()) {
                                    out.println("게시물 내용:");
                                    out.println(posts.get(postIndex).getContent());
                                } else {
                                    out.println("해당 번호의 게시물이 존재하지 않습니다.");
                                }
                            } else {
                                out.println("보고 싶은 게시물 번호를 입력하세요.");
                            }
                            break;
                        case 4:
                            if (parts.length >= 2) {
                                int deleteIndex = Integer.parseInt(parts[1]) - 1;
                                if (deleteIndex >= 0 && deleteIndex < posts.size()) {
                                    posts.remove(deleteIndex);
                                    out.println("게시물이 삭제되었습니다.");
                                } else {
                                    out.println("해당 번호의 게시물이 존재하지 않습니다.");
                                }
                            } else {
                                out.println("삭제할 게시물 번호를 입력하세요.");
                            }
                            break;
                        case 0:
                            out.println("서버 연결을 종료합니다.");
                            return;
                        default:
                            out.println("잘못된 명령어입니다.");
                    }
                }
            } catch (IOException e) {
                System.out.println("클라이언트 연결 오류: " + e.getMessage());
            } finally {
                if (out != null) {
                    clients.remove(out);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("소켓 닫기 오류: " + e.getMessage());
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