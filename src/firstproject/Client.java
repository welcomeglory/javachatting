package firstproject;

import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); 
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
            	System.out.println("==================================");
                System.out.print("1. 조회"+"\t");
                System.out.print("2. 추가"+"\t");
                System.out.print("3. 수정"+"\t");
                System.out.print("4. 삭제"+"\t");
                System.out.print("0. 종료>>");
                String choice = consoleReader.readLine();

                switch (choice) {
                    case "1":
                        out.println("LIST");
                        System.out.println("[목록]");
                        System.out.println("번호\t제목\t작성자");        
                        String message;
                        while ((message = in.readLine()) != null && !message.isEmpty()) {
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
                        out.println("LIST");
                        System.out.println("[목록]");
                        while ((message = in.readLine()) != null && !message.isEmpty()) {
                            System.out.println(message);
                        }
                        System.out.print("수정할 글 번호>> ");
                        int updateIndex = Integer.parseInt(consoleReader.readLine())-1;
                        System.out.print("수정할 항목(제목/내용/작성자)>> ");
                        String fieldToUpdate = consoleReader.readLine();
                        System.out.print("수정사항>> ");
                        String updatedValue = consoleReader.readLine();
                        out.println("UPDATE");
                        out.println(updateIndex);
                        out.println(fieldToUpdate);
                        out.println(updatedValue);
                        break;
                    case "4":
                        out.println("LIST");
                        System.out.println("[목록]");
                        while ((message = in.readLine()) != null && !message.isEmpty()) {
                            System.out.println(message);
                        }
                        System.out.print("삭제할 글 번호>> ");
                        int deleteIndex = Integer.parseInt(consoleReader.readLine())-1;
                        out.println("DELETE");
                        out.println(deleteIndex);
                        break;
                    case "0":
                        out.println("EXIT");
                        socket.close();
                        return;
                    default:
                        System.out.println("잘못된 입력");
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
