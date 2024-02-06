package firstproject;
import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 12345;
    private static List<String> posts = new ArrayList<>();

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
                    System.out.println(message); // 클라이언트가 보낸 메시지 출력
                    String[] parts = message.split("\\|");
                    int command;
                    try {
                        command = Integer.parseInt(parts[0]);
                    } catch (NumberFormatException e) {
                        out.println("숫자를 입력하세요."); // 숫자를 입력하지 않았을 때 안내 메시지 전송
                        continue; // 다음 루프로 진행
                    }
                    switch (command) {
                        case 1: // 게시판 목록 보기
                            out.println("게시판 목록:");
                            for (int i = 0; i < posts.size(); i++) {
                                out.println((i + 1) + ". " + posts.get(i));
                            }
                            break;
                        case 2: // 게시물 등록
                            out.println("제목을 입력하세요:");
                            String title = in.readLine().replace("|", "\\|"); // 파이프 문자 처리
                            out.println("내용을 입력하세요:");
                            String content = in.readLine().replace("|", "\\|"); // 파이프 문자 처리
                            out.println("작성자를 입력하세요:");
                            String author = in.readLine().replace("|", "\\|"); // 파이프 문자 처리
                            posts.add(title + " | " + content + " | " + author);
                            out.println("게시물이 등록되었습니다.");
                            break;
                        case 3: // 게시물 수정
                            out.println("수정할 게시물 번호를 입력하세요:");
                            int editIndex;
                            try {
                                editIndex = Integer.parseInt(in.readLine()) - 1;
                            } catch (NumberFormatException e) {
                                out.println("숫자를 입력하세요.");
                                continue;
                            }
                            if (editIndex >= 0 && editIndex < posts.size()) {
                                out.println("수정할 내용을 선택하세요 (제목, 내용, 작성자):\n");
                                String editType = in.readLine();
                                out.println("수정할 내용을 입력하세요:");
                                String editContent = in.readLine().replace("|", "\\|"); // 파이프 문자 처리
                                String[] postParts = posts.get(editIndex).split(" \\| ");
                                if (editType.equals("제목")) {
                                    postParts[0] = editContent;
                                } else if (editType.equals("내용")) {
                                    postParts[1] = editContent;
                                } else if (editType.equals("작성자")) {
                                    postParts[2] = editContent;
                                }
                                posts.set(editIndex, postParts[0] + " | " + postParts[1] + " | " + postParts[2]);
                                out.println("게시물이 수정되었습니다.");
                            } else {
                                out.println("해당 번호의 게시물이 존재하지 않습니다.");
                            }
                            break;
                        case 4: // 게시물 삭제
                            out.println("삭제할 게시물 번호를 입력하세요:");
                            int deleteIndex;
                            try {
                                deleteIndex = Integer.parseInt(in.readLine()) - 1;
                            } catch (NumberFormatException e) {
                                out.println("숫자를 입력하세요.");
                                continue;
                            }
                            if (deleteIndex >= 0 && deleteIndex < posts.size()) {
                                posts.remove(deleteIndex);
                                out.println("게시물이 삭제되었습니다.");
                            } else {
                                out.println("해당 번호의 게시물이 존재하지 않습니다.");
                            }
                            break;
                        case 0: // 종료
                            out.println("서버 연결을 종료합니다.");
                            return;
                        default:
                            out.println("잘못된 명령어입니다.");
                    }
                }
            } catch (IOException e) {
                System.out.println("클라이언트 연결 오류: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("소켓 닫기 오류: " + e.getMessage());
                }
            }
        }
    }
}