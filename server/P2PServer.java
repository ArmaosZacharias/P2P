package server;

import java.io.*;
import java.net.*;

/**
 * Argument à entrer en ligne de commande:
 * - numero de port d'attachement de la socket d'ecoute UDP
 *
 * - gere simultanement les requetes de plusieurs P2PClients (multithreadee)
 * - gere la validite des arguments transmis
 * - creer une socket d'écoute TCP (avec le numero de port d'attachement transmis en argument)
 * - gere la liste des fichiers disponibles sur les hôtes des differents P2PClients qui lui sont connectes
 * - chaque fichier est associe à la liste d'adresses de P2PClient (via ListFilesServer)
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