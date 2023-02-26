package tcp.muliy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        
        try {
            // 서버 소켓 생성 및 포트 설정
            serverSocket = new ServerSocket(12345);
            System.out.println("Server started on port 12345");
            
            while (true) {
                // 클라이언트 연결 대기
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                // 클라이언트와 통신을 담당하는 쓰레드 생성 및 실행
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 서버 소켓 닫기
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    // 각 클라이언트와 통신을 담당하는 쓰레드
    private static class ClientHandler implements Runnable {
        
        private Socket clientSocket;
        
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        
        @Override
        public void run() {
            try {
                // 클라이언트와 통신을 위한 InputStream과 OutputStream 생성
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                
                // 클라이언트와의 통신 시작
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    String message = new String(buffer, 0, length);
                    System.out.println("Received message from " + clientSocket.getInetAddress().getHostAddress() + ": " + message);
                    
                    // 클라이언트에게 응답
                    String responseMessage = "Server response: " + message;
                    outputStream.write(responseMessage.getBytes());
                    outputStream.flush();
                    
                    // 클라이언트가 "quit"을 보낸 경우, 통신 종료
                    if (message.trim().equalsIgnoreCase("quit")) {
                        System.out.println("Client " + clientSocket.getInetAddress().getHostAddress() + " disconnected");
                        break;
                    }
                }
                
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 클라이언트 소켓 닫기
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
