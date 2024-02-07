package firstproject;

import java.io.*;
import java.net.*;

public class Client2 {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // 서버 주소와 포트 번호
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.println("1. 목록 조회");
                System.out.println("2. 등록");
                System.out.println("3. 수정");
                System.out.println("4. 삭제");
                System.out.println("5. 종료");
                System.out.print("원하는 작업을 선택하세요: ");
                String choice = consoleReader.readLine();

                switch (choice) {
                    case "1":
                        out.println("LIST");
                        System.out.println("게시글 목록:");
                        String message;
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        break;
                    case "2":
                        out.println("ADD");
                        System.out.print("제목을 입력하세요: ");
                        String title = consoleReader.readLine();
                        System.out.print("내용을 입력하세요: ");
                        String content = consoleReader.readLine();
                        System.out.print("작성자를 입력하세요: ");
                        String author = consoleReader.readLine();
                        out.println(title);
                        out.println(content);
                        out.println(author);
                        break;
                    case "3":
                        out.println("LIST");
                        System.out.println("게시글 목록:");
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        System.out.print("수정할 게시글의 인덱스를 입력하세요: ");
                        int updateIndex = Integer.parseInt(consoleReader.readLine());
                        System.out.print("수정할 항목을 선택하세요 (title/content/author): ");
                        String fieldToUpdate = consoleReader.readLine();
                        System.out.print("새로운 값을 입력하세요: ");
                        String updatedValue = consoleReader.readLine();
                        out.println("UPDATE");
                        out.println(updateIndex);
                        out.println(fieldToUpdate);
                        out.println(updatedValue);
                        break;
                    case "4":
                        out.println("LIST");
                        System.out.println("게시글 목록:");
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        System.out.print("삭제할 게시글의 인덱스를 입력하세요: ");
                        int deleteIndex = Integer.parseInt(consoleReader.readLine());
                        out.println("DELETE");
                        out.println(deleteIndex);
                        break;
                    case "5":
                        out.println("EXIT");
                        socket.close();
                        return;
                    default:
                        System.out.println("올바르지 않은 선택입니다.");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}