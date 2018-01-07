package client;

import comServClient.*;
import java.net.*;
import java.io.*;

public class ThreadClient extends Thread{
    private ServerSocket sockConn = null;

    public ThreadClient(ServerSocket conn) {
        this.sockConn = conn;
    }
    
    public void run(){
        try {
            Socket sockComm=new Socket();
            while (true) {
                sockComm = sockConn.accept();
                ThreadSender ts=new ThreadSender(sockComm);
                ts.start();
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