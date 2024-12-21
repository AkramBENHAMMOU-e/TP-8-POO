import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeursFiles {

  
    private static final int PORT = 12345; // Port d'écoute du serveur
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur de fichiers démarré sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress());

                // Créer un nouveau thread pour gérer la connexion du client
                ClientConn clientHandler = new ClientConn(clientSocket);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du démarrage du serveur : " + e.getMessage());
        }
}
}
