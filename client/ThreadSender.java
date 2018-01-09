package client;

import java.io.*;
import java.net.*;

public class ThreadSender extends Thread{
    Socket sockComm;
    
    public ThreadSender(Socket s){
        sockComm=s;
    }
    
    public void run(){
        System.out.println("DEBUG : Connexion du client "+sockComm.getInetAddress().getHostAddress());
    }
}
