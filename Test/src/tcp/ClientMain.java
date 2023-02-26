package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

            String message;
            do {
                BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
                message = userInput.readLine();

                out.println(message);
                out.flush();

                String response = in.readLine();
                System.out.println("Server response: " + response);
            } while (!message.equals("exit"));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
