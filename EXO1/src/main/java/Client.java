import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        Scanner sc = new Scanner(System.in);

        try {
            socket = new Socket("localhost", 123);
            System.out.println("Connected to server");

            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = new PrintWriter(outputStream, true);

            while (true) {
                System.out.println("Entrez un nombre entre 0 et 100 : ");
                int n = sc.nextInt();
                writer.println(n);
                System.out.println("Nombre envoyé au serveur");

                String response = reader.readLine();
                System.out.println("Received from server: " + response);

                if (response.equals("Bravo, vous avez trouvé le nombre")) {
                    break;
                }
            }

            // Optional: sending exit message to server when the game ends
            writer.println("exit");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
