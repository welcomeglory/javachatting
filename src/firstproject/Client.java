package firstproject;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // 서버 주소와 포트 번호
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
            	System.out.println("==========================================");
                System.out.print("1. 목록"+"\t");
                System.out.print("2. 등록"+"\t");
                System.out.print("3. 수정"+"\t");
                System.out.print("4. 삭제"+"\t");
                System.out.println("0. 종료");
            	System.out.println("==========================================");
                System.out.print("원하는 작업을 선택하세요>> ");
                String choice = consoleReader.readLine();

                switch (choice) {
                    case "1":
                        out.println("LIST");
                        System.out.println("게시글 목록");
                        System.out.println("번호"+"\t"+"제목"+"\t"+"작성자"+"\t"+"작성일");
                    	System.out.println("==========================================");
                       String message;
                        while (!(message = in.readLine()).isEmpty()) {
                            System.out.println(message);
                        }
                        break;
                    case "2":
                        out.println("ADD");
                        System.out.print("제목>> ");
                        String title = consoleReader.readLine();
                        System.out.print("내용>> ");
                        String content = consoleReader.readLine();
                        System.out.print("작성자>> ");
                        String author = consoleReader.readLine();
                        out.println(title);
                        out.println(content);
                        out.println(author);
                        break;
                    case "3":
                        out.println("UPDATE"); // 수정 명령을 먼저 보냅니다.
                        out.println("LIST"); // 게시글 목록을 요청합니다.
                        System.out.println("게시글 목록:");
                        int i = 1;
                        String listItem;
                        while (!(listItem = in.readLine()).isEmpty()) {
                            System.out.println(i + ". " + listItem);
                            i++;
                        }
                        System.out.print("수정할 게시글 번호를 입력하세요: ");
                        int updateIndex = Integer.parseInt(consoleReader.readLine()) - 1;
                        System.out.print("수정할 항목을 선택하세요 (title/content/author): "); // 수정할 항목을 선택합니다.
                        String fieldToUpdate = consoleReader.readLine();
                        System.out.print("새로운 값을 입력하세요: ");
                        String updatedValue = consoleReader.readLine();
                        out.println(updateIndex);
                        out.println(fieldToUpdate);
                        out.println(updatedValue);
                        break;





                   
                    case "4":
                        out.println("LIST");
                        System.out.println("게시글 목록:");
                        int j = 1;
                        String deleteItem;
                        while (!(deleteItem = in.readLine()).isEmpty()) {
                            System.out.println(j + ". " + deleteItem);
                            j++;
                        }
                        System.out.print("삭제할 게시글 번호를 입력하세요: ");
                        int deleteIndex = Integer.parseInt(consoleReader.readLine()) - 1; // 사용자 입력은 1부터 시작하므로 인덱스에 -1을 해줍니다.
                        out.println("DELETE");
                        out.println(deleteIndex);
                        break;


                    case "0":
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
