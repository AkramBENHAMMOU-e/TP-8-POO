import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(123);
            System.out.println("Server is listening on port 123");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            int nombre = (int) (Math.random() * 100);
            System.out.println("Le nombre à deviner est : " + nombre);

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);

            while (true) {
                String message = reader.readLine();
                if (message == null) {
                    continue;
                }

                // Check if the client sends "exit"
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Client has disconnected.");
                    break;
                }

                int n;
                try {
                    n = Integer.parseInt(message);
                } catch (NumberFormatException e) {
                    writer.println("Veuillez entrer un nombre valide");
                    continue;
                }

                System.out.println("Received from client: " + n);

                if (n > nombre) {
                    writer.println("Ce nombre est plus grand");
                } else if (n < nombre) {
                    writer.println("Ce nombre est plus petit");
                } else {
                    writer.println("Bravo, vous avez trouvé le nombre");
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
