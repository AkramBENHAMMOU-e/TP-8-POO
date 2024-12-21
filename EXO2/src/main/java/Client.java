import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {

            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
                 InputStream inputStream = socket.getInputStream()) {

                System.out.println("Connecté au serveur.");
                System.out.print("Entrez le nom du fichier à demander : ");
                String fileName = reader.readLine();
                writer.println(fileName);

                // Lire la réponse du serveur (contenu du fichier ou message d'erreur)
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    System.out.write(buffer, 0, bytesRead);
                }
                System.out.println("\nFin de la réponse du serveur.");

            } catch (IOException e) {
                System.err.println("Erreur lors de la communication avec le serveur : " + e.getMessage());
            }


    }


}
