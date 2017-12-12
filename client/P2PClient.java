package client;

import java.io.*;
import java.net.*;

public class P2PClient {
    
    public static void main(String[] args) {
        String ipServ ;
        int portServ = 0 ;
        Socket sockComm = null;
        ServerSocket sockConn = null; // socket d’écoute TCP
        String requete;
        
        if (args.length != 3) {
            System.out.println("Nombre d'arguments non valide !");
            System.exit(0);
        }

        ipServ = args[0]; // IP de l'hôte de l'application P2PServer

        try {
            portServ = Integer.parseInt(args[1]); // n° de port de la socket d’écoute TCP de l’application P2PServer
        }
        catch (NumberFormatException e) {
            System.out.println("Numéro de port non valide !");
            System.exit(1);
        }

        File repertoire = new File(args[2]); // chemin vers le répertoire contenant les fichiers de l’application P2PClient
        String [] listeRepertoire = repertoire.list(); // récupération du répertoire à partir du chemin du fichier
        for (int i = 0; i <= repertoire.length(); i++) { // affiche la liste du répertoire
            System.out.println(listeRepertoire[i]);
        }

        if (!repertoire.exists()) {
            System.out.println("Le répertoire n'existe pas");
            System.exit(2);
        }

        try {
            sockComm = new Socket(ipServ, portServ);
            System.out.println("IP locale " + sockComm.getLocalAddress().getHostAddress()); // l’IP de l’hôte qui l’héberge
            System.out.println("Port local " + sockComm.getLocalPort());
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);

            do {
                System.out.println("Entrer votre requête : ");
                requete = br.readLine();
                if (requete.length() != 0){
                    String requeteTab [] = requete.split(" ");
                    if (requeteTab[0].equals("search")) {
                        String pattern = requeteTab[1];
                        // instructions de recherche du <pattern> dans la liste des fichiers
                    } else if (requeteTab[0].equals("get")) {
                        int num = Integer.parseInt(requeteTab[1]);
                        // téléchargement du fichier numéro <num> dans la liste des résultats
                        // l’application P2PClient doit commencer par vérifier si elle ne possède pas déjà le fichier ciblé
                        // Si elle ne le possède pas, elle doit envoyer une requête de « téléchargement » à l’application P2PServer.
                        // En réponse, elle reçoit une liste contenant les adresses de toutes les applications
                        // P2PClient qui possèdent le fichier à télécharger.
                    } else if (requeteTab[0].equals("list")) {
                        // affichage de la liste des fichiers
                    } else if (requeteTab[0].equals("local") && requeteTab[1].equals("list")) {
                        // affichage de la liste locale des fichiers
                    } else if (requeteTab[0].equals("quit")) {
                        System.exit(3);
                    }
                } else {
                    System.out.println("Requête vide.");
                }

            } while (requete.length() != 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}