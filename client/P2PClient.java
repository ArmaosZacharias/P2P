<<<<<<< HEAD
package client;

import java.io.*;
import java.net.*;

public class P2PClient {
    
    public static void main(String[] args) {
        String ipServ ;
        int portServ = 0 ;
        Socket sockComm = null;
        ServerSocket sockConn = null;
        String line;
        
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
                line = br.readLine();
                if (line.length() != 0){
                    
                }

            } while (line.length() != 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
=======
package client;
import comServerClient.*;
import java.io.*;
import java.net.*;


public class P2PClient {
    
    public static void main(String[] args) {
        String ipServ ;
        int portServ = 0 ;
        Socket sockComm = null;
        ServerSocket sockConn = null;
        String line;
        
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
//        String [] listeRepertoire = repertoire.list(); // récupération du répertoire à partir du chemin du fichier
//        for (int i = 0; i <= repertoire.length(); i++) { // affiche la liste du répertoire
//            System.out.println(listeRepertoire[i]);
//        }         <<<<<<<<<<<<<<<<<<<Normalement on a plus besoin de ça avec les ListFileServer et Client

        if(!repertoire.isDirectory()){
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
                line = br.readLine();
                if (line.length() != 0){
                    ListFileClient lfc=new ListFileClient(repertoire);
                    lfc.afficherList();
                }

            } while (line.length() != 0);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
>>>>>>> e7a58c02e0137868f42283f1ae28713f756e8960
