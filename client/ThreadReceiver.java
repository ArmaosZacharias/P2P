package client;

import java.io.*;
import java.net.*;
import comServClient.*;

public class ThreadReceiver extends Thread{
    AddressServerTcp ip;
    P2PFile leFichier;
    int debutMorceau, finMorceau;
    
    public ThreadReceiver(AddressServerTcp address, P2PFile fic, int d, int f){
        ip=address;
        leFichier=fic;
        debutMorceau=d;
        finMorceau=f;
    }
    
    public void run(){
        
    }
}
