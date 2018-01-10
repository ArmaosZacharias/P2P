package server;

import java.io.*;
import java.net.*;

/**
 * Argument à entrer en ligne de commande:
 * - numéro de port d'attachement de la socket d'écoute UDP
 *
 * - gère simultanément les requêtes de plusieurs P2PClients (multithreadée)
 * - gère la validité des arguments transmis
 * - créer une socket d'écoute TCP (avec le numéro de port d'attachement transmis en argument)
 * - gère la liste des fichiers disponibles sur les hôtes des différents P2PClients qui lui sont connectés
 * - chaque fichier est associé à la liste d'adresses de P2PClient (via ListFilesServer)
 */

public class P2PServer {
    public static void main(String[] args) {
        int portServ = 0;
        Socket sockComm = null;
        ServerSocket sockConn = null; // socket d’écoute TCP
        ThreadServer th;

        try {
            portServ = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Numero de port non valide");
            System.exit(1);
        }

        if (portServ < 1024 || portServ > 65535) {
            System.out.println("Numero de port non autorise ou non valide");
            System.exit(1);
        }

        try {
            sockConn = new ServerSocket(portServ);
        } catch (IOException e) {
            System.out.println("Problème creation serveur : " + e.toString());
            System.exit(1);
        }
        ListFileServer lsf=new ListFileServer();

        try {
            while (true) {
                sockComm = sockConn.accept();
                System.out.println("DEBUG : ip client : " + sockComm.getInetAddress().getHostAddress() + ", port client : " + sockComm.getPort());
                th = new ThreadServer(sockComm, lsf);
                th.start();
            }
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Problème connexion client : " + e.toString());
        } finally {
            try {
                if (sockConn != null) {
                    sockConn.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("Erreur IO2");
            }
        }
    }
}