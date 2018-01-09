package client;

import java.io.*;
import java.net.*;
import comServClient.*;

public class ThreadReceiver extends Thread{
    AddressServerTcp address;
    P2PFile leFichier;
    long debutMorceau, finMorceau;
    
    public ThreadReceiver(AddressServerTcp add, P2PFile fic, long d, long f){
        address=add;
        leFichier=fic;
        debutMorceau=d;
        finMorceau=f;
    }
    
    public void run(){
        Socket sockComm=null;
        try{
            sockComm=new Socket(address.getAdresse(), address.getPort());
            
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(sockComm.getOutputStream()));
            oos.writeObject(leFichier);
            oos.flush();
            oos.writeLong(debutMorceau);
            oos.flush();
            oos.writeLong(finMorceau);
            oos.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
