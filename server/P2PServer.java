package server;
import java.io.*;
import java.net.*;

public class P2PServer {
    public static void main(String[] args) {
        int portServ = 0;
        ServerSocket sockConn = null;
        Socket sockComm = null;
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
        try {
            while (true) {
                sockComm = sockConn.accept();
                System.out.println("DEBUG : ip client : " + sockComm.getInetAddress().getHostAddress() + ", port client : " + sockComm.getPort());


            }
        } catch(IOException e) {
            e.printStackTrace();
            System.out.println("Problème connexion client : " + e.toString());
        } finally {
            try {
                if (sockConn != null)
                    sockConn.close();
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("Erreur IO2");
            }
        }
    }
}