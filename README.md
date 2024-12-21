# TP-8-POO : Programmation Réseau en Java

Ce projet contient deux exercices démontrant l'utilisation des sockets en Java pour la programmation réseau.

## Exercice 1 : Jeu de devinettes

L'exercice 1 implémente un jeu de devinettes simple entre un client et un serveur.

### Structure des fichiers

#### Client.java
```java
public class Client {
    public static void main(String[] args) {
        Socket socket = null;
        Scanner sc = new Scanner(System.in);

        try {
            socket = new Socket("localhost", 123);
            // ... Code pour la communication client
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```

Le client :
- Se connecte au serveur sur le port 123
- Permet à l'utilisateur d'entrer des nombres
- Reçoit et affiche les réponses du serveur
- Continue jusqu'à ce que le bon nombre soit trouvé

#### Server.java
```java
public class Server {
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(123);
            // ... Code pour la gestion du serveur
            int nombre = (int) (Math.random() * 100);
            // ... Code pour la communication serveur
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

Le serveur :
- Écoute sur le port 123
- Génère un nombre aléatoire entre 0 et 100
- Guide le client avec des messages "plus grand" ou "plus petit"
- Annonce la victoire quand le nombre est trouvé

## Exercice 2 : Serveur de Fichiers

L'exercice 2 implémente un serveur de fichiers simple permettant aux clients de demander des fichiers.

### Structure des fichiers

#### Client.java
```java
public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            // ... Code pour la demande de fichier
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
```

Le client :
- Se connecte au serveur sur le port 12345
- Demande le nom d'un fichier à l'utilisateur
- Reçoit et affiche le contenu du fichier ou un message d'erreur

#### ServeursFiles.java
```java
public class ServeursFiles {
    private static final int PORT = 12345;
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // ... Code pour l'acceptation des clients
        } catch (IOException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
```

Le serveur :
- Écoute sur le port 12345
- Accepte les connexions des clients
- Crée un nouveau thread pour chaque client

#### ClientConn.java
```java
public class ClientConn implements Runnable {
    private static final String FILE_DIRECTORY = "...";
    
    @Override
    public void run() {
        // ... Code pour la gestion des requêtes de fichiers
    }
}
```

La classe ClientConn :
- Gère chaque connexion client dans un thread séparé
- Lit la demande de fichier
- Vérifie l'existence du fichier
- Envoie le fichier ou un message d'erreur

## Comment exécuter les exercices

### Exercice 1
1. Démarrer le serveur en exécutant `Server.java`
2. Démarrer le client en exécutant `Client.java`
3. Suivre les instructions pour deviner le nombre

### Exercice 2
1. Démarrer le serveur en exécutant `ServeursFiles.java`
2. Démarrer le client en exécutant `Client.java`
3. Entrer le nom du fichier souhaité

## Technologies utilisées
- Java
- Sockets TCP/IP
- Threads (pour l'exercice 2)
- Entrées/Sorties (I/O) Java
