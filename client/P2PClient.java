package client;
import java.io.*;
import java.net.*;

// Arguments à transmettre via la ligne de commande :
// args[0] - l’adresse IP de l’hôte de l’application server.P2PServer ;
// args[1] - le numéro de port de la socket d’écoute TCP de l’application server.P2PServer ;
// args[2] - le chemin vers le répertoire contenant les fichiers de l’application client.P2PClient.

public class P2PClient {
    public static void main(String[] args) {
        String ipServ;
        int portServ = 0;
        Socket sockComm = null;
        if (args.length != 3) {
            System.out.println("Nombre d'arguments incorrect");
            System.exit(0);
        }
        ipServ = args[0];
        try {
            portServ = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Numero de port non valide");
            System.exit(1);
        }
        try {
            // 1 - tester la validité des arguments transmis via la ligne de commande ;
            // 2 - déterminer l’IP de l’hôte qui l’héberge ;
            // 3 - ouvrir le répertoire dont le chemin lui a été transmis en argument afin de lister les fichiers qu’il contient.
            // Cette liste constitue la liste des fichiers que l’application « possède ».
            // 4 - créer une socket d’écoute TCP dont le numéro de port d’attachement doit être choisi par le système parmi les numéros de port disponibles.
            // Pour cela, il suffit de transmettre le paramètre 0 au constructeur de la classe ServerSocket (voir sa javadoc).
            // 5 - se connecter à l’application server.P2PServer dont elle a reçu l’adresse en arguments.
            // Cette connexion restera ouverte jusqu’à ce que l’application client.P2PClient se termine.
            // 6 – créer un thread nommé ThreadClient destiné à accepter les demandes de
            // connexion des autres applications client.P2PClient sur la socket d’écoute créée à l’étape 4 ; et démarrer ce thread.
            // 7 – transmettre à l’application server.P2PServer la liste de fichiers qu’elle possède.
            // 8 - …



        }
        catch(IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        finally {
            try {
                if (sockComm != null){
                    sockComm.close();
                }
            }
            catch(IOException e) {
                System.out.println("Erreur IO");
            }
        }
    }
}
