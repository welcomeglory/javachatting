package firstproject;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static List<Post> posts = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); 
            System.out.println("서버가 시작되었습니다.");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("클라이언트가 접속했습니다.");

                ClientHandler clientHandler = new ClientHandler(socket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    String command = in.readLine();
                    if (command == null) break;

                    switch (command) {
                        case "LIST":
                            out.println(getPostList());
                            break;
                        case "ADD":
                            String title = in.readLine();
                            String content = in.readLine();
                            String author = in.readLine();
                            addPost(new Post(title, content, author));
                            break;
                        case "UPDATE":
                            int index = Integer.parseInt(in.readLine());
                            String fieldToUpdate = in.readLine();
                            String updatedValue = in.readLine();
                            updatePost(index, fieldToUpdate, updatedValue);
                            break;
                        case "DELETE":
                            int deleteIndex = Integer.parseInt(in.readLine());
                            deletePost(deleteIndex);
                            break;
                        case "EXIT":
                            socket.close();
                            return;
                        default:
                            out.println("Invalid command");
                            break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String getPostList() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < posts.size(); i++) {
                stringBuilder.append(i+1).append(". ").append(posts.get(i)).append("\n");
            }
            return stringBuilder.toString();
        }

        private synchronized void addPost(Post post) {
            posts.add(post);
        }

        private synchronized void updatePost(int index, String fieldToUpdate, String updatedValue) {
            if (index >= 0 && index < posts.size()) {
                Post post = posts.get(index);
                switch (fieldToUpdate) {
                    case "제목":
                        post.setTitle(updatedValue);
                        break;
                    case "내용":
                        post.setContent(updatedValue);
                        break;
                    case "작성자":
                        post.setAuthor(updatedValue);
                        break;
                    default:
                        break;
                }
            }
        }

        private synchronized void deletePost(int index) {
            if (index >= 0 && index < posts.size()) {
                posts.remove(index);
            }
        }
    }

    static class Post {
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

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        @Override
        public String toString() {
            return title + "\t" + content + "\t " + author;
        }
    }
}
