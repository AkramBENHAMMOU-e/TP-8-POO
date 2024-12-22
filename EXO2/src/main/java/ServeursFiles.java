import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ServeursFiles extends Thread {
    private static final String FILE_DIRECTORY = "C:\\Users\\HP\\OneDrive\\Bureau\\test\\TP-8-POO\\EXO2\\src\\main\\java\\Files";
    private int nbrClient = 0;

    public static void main(String[] args) {
        new ServeursFiles().start();
    }

    @Override
    public void run() {
        try (ServerSocket ss = new ServerSocket(123)) {
            System.out.println("Serveur demarre sur le port " + ss.getLocalPort());
            while (true) {
                Socket socket = ss.accept();
                nbrClient++;
                new Communication(socket, nbrClient).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class Communication extends Thread {
        private Socket socket;
        private int numClient;

        public Communication(Socket socket, int numClient) {
            this.socket = socket;
            this.numClient = numClient;
        }

        @Override
        public void run() {
            try (InputStream inputStream = socket.getInputStream();
                 InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                 BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                 OutputStream outputStream = socket.getOutputStream();
                 PrintWriter printWriter = new PrintWriter(outputStream, true)) {

                System.out.println("Client n°" + numClient + " connecte. IP = " + socket.getRemoteSocketAddress());
                printWriter.println("Bienvenue, vous etes le client numero " + numClient + " ayant l'adresse IP : " + socket.getRemoteSocketAddress());

                while (true) {
                    printWriter.println("\nEntrer le nom du fichier souhaite : ");
                    String fileName = bufferedReader.readLine();
                    if (fileName == null || fileName.isEmpty()) {
                        break;
                    }
                    System.out.println("Demande de fichier recue : " + fileName);

                    Path filePath = Paths.get(FILE_DIRECTORY, fileName);
                    File file = filePath.toFile();

                    if (file.exists() && file.isFile()) {
                        byte[] fileContent = Files.readAllBytes(filePath);
                        outputStream.write(fileContent);
                        outputStream.flush();
                        System.out.println("Fichier '" + fileName + "' envoye au client.");
                    } else {
                        String errorMessage = "Fichier non trouve : " + fileName;
                        printWriter.println(errorMessage);
                        System.out.println(errorMessage);
                    }
                }

            } catch (IOException e) {
                System.err.println("Erreur de communication avec le client num : " + numClient + ": " + e.getMessage());
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.err.println("Erreur lors de la fermeture du socket pour le client num : " + numClient + ": " + e.getMessage());
                }
            }
        }
    }
}

