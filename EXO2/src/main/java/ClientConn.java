import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ClientConn implements Runnable {
    private static final String FILE_DIRECTORY = "C:\\Users\\HP\\OneDrive\\Bureau\\test\\TP-8-POO\\EXO2\\src\\main\\java\\Files";
    private final Socket clientSocket;

    public ClientConn(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            OutputStream out = clientSocket.getOutputStream()
        ) {
            String fileName = in.readLine();
            System.out.println("Demande de fichier reçue : " + fileName);

            Path filePath = Paths.get(FILE_DIRECTORY, fileName);
            File file = filePath.toFile();

            if (file.exists() && file.isFile()) {
                // Le fichier existe, on lit son contenu et on l'envoie au client
                byte[] fileContent = Files.readAllBytes(filePath);
                out.write(fileContent);
                out.flush();
                System.out.println("Fichier '" + fileName + "' envoyé au client.");
            } else {
                // Le fichier n'existe pas, on envoie un message d'erreur
                String errorMessage = "Fichier non trouvé : " + fileName;
                out.write(errorMessage.getBytes());
                out.flush();
                System.out.println(errorMessage);
            }

        } catch (IOException e) {
            System.err.println("Erreur lors de la gestion du client : " + e.getMessage());
        }

    }
}
