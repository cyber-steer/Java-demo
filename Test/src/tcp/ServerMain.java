package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            Socket clientSocket = serverSocket.accept();

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String message;
            do {
                message = in.readLine();
                System.out.println("Client message: " + message);

                out.println("Server received your message: " + message);
                out.flush();
            } while (!message.equals("exit"));

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
