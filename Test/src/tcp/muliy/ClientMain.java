package tcp.muliy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";
        int serverPort = 12345;

        Socket socket = new Socket(serverAddress, serverPort);
        System.out.println("서버에 접속되었습니다.");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line;

        while ((line = stdin.readLine()) != null) {
            out.println(line);
            String response = in.readLine();
            System.out.println("서버 응답: " + response);

            if (response.equals("quit")) {
                break;
            }
        }

        in.close();
        out.close();
        socket.close();
    }
}
