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

            System.out.println("서버에 연결되었습니다.");

            new Thread(() -> {
                String message;
                try {
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("서버 연결 오류: " + e.getMessage());
                }
            }).start();

            String userInput;
            while ((userInput = consoleReader.readLine()) != null) {
                switch (userInput) {
                    case "1": // 게시판 목록 보기
                        out.println("1");
                        break;
                    case "2": // 게시물 등록
                        out.println("2");
                        System.out.println("제목을 입력하세요:");
                        String title = consoleReader.readLine().replace("|", "\\|"); // 파이프 문자 처리
                        System.out.println("내용을 입력하세요:");
                        String content = consoleReader.readLine().replace("|", "\\|"); // 파이프 문자 처리
                        System.out.println("작성자를 입력하세요:");
                        String author = consoleReader.readLine().replace("|", "\\|"); // 파이프 문자 처리
                        out.println(title + "|" + content + "|" + author);
                        break;
                    case "3": // 게시물 수정
                        out.println("3");
                        System.out.println("수정할 게시물의 번호를 입력하세요:");
                        int editIndex = Integer.parseInt(consoleReader.readLine());
                        System.out.println("수정할 내용의 종류를 선택하세요 (제목/내용/작성자):");
                        String editType = consoleReader.readLine();
                        System.out.println("수정할 내용을 입력하세요:");
                        String editContent = consoleReader.readLine().replace("|", "\\|"); // 파이프 문자 처리
                        out.println(editIndex + "|" + editType + "|" + editContent);
                        break;
                    case "4": // 게시물 삭제
                        out.println("4");
                        System.out.println("삭제할 게시물의 번호를 입력하세요:");
                        int deleteIndex = Integer.parseInt(consoleReader.readLine());
                        out.println(deleteIndex);
                        break;
                    case "0": // 종료
                        out.println("0");
                        return;
                    default:
                        System.out.println("잘못된 입력입니다.");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("클라이언트 오류: " + e.getMessage());
        }
    }
}
