package java_0202;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer_Gui {
    private DataOutputStream out; // 데이터 전송을 위한 출력 스트림
    private String name = "Server"; // 서버의 이름 설정

    public static void main(String[] args) {
        new ChatServer_Gui().start(); // 서버 시작
    }

    public void start() {
        // 서버 창 생성
        JFrame frame = new JFrame("Chat Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫을 때 프로그램 종료 설정
        frame.setSize(400, 300); // 창 크기 설정

        JTextArea chatArea = new JTextArea(); // 대화 내용을 표시할 텍스트 영역 생성
        chatArea.setEditable(false); // 편집 불가능하도록 설정

        JTextField inputField = new JTextField(); // 메시지를 입력할 텍스트 필드 생성
        JButton sendButton = new JButton("Send"); // 전송 버튼 생성

        // Send 버튼 클릭 시 이벤트 처리
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText(); // 입력한 메시지 가져오기
                sendMessage(message, name); // 발신자 정보를 포함하여 메시지 전송
                displayMessage(message, name, chatArea); // 메시지 화면에 표시
                inputField.setText(""); // 입력 필드 초기화
            }
        });

        // 텍스트 필드에서 Enter 키 누를 시 이벤트 처리
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendButton.doClick(); // Enter 키가 눌리면 Send 버튼 클릭
                }
            }
        });

        // 입력 패널 생성 및 배치
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // 전체 패널 생성 및 배치
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        panel.add(inputPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel); // 프레임에 패널 추가
        frame.setVisible(true); // 프레임 표시

        try {
            ServerSocket serverSocket = new ServerSocket(7777); // 7777번 포트에서 서버 소켓 생성
            Socket socket = serverSocket.accept(); // 클라이언트의 연결 요청을 기다리고 수락하여 클라이언트 소켓 생성
            out = new DataOutputStream(socket.getOutputStream()); // 출력 스트림 생성
            DataInputStream in = new DataInputStream(socket.getInputStream()); // 입력 스트림 생성

            while (true) {
                String message = in.readUTF(); // 클라이언트에서 메시지 수신
                displayMessage(message, name, chatArea); // 받은 메시지를 화면에 표시
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 메시지 전송
    private void sendMessage(String message, String sender) {
        try {
            out.writeUTF(sender + ": " + message); // 발신자 정보를 메시지에 포함하여 전송
            out.flush(); // 버퍼 비우기
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 메시지 화면에 표시
    private void displayMessage(String message, String sender, JTextArea chatArea) {
        chatArea.append(sender + ": " + message + "\n"); // 받은 메시지를 화면에 표시
    }
}
