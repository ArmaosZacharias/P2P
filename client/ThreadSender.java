// Alexis Armaos - Hélène Zacharias
package client;

import comServClient.P2PFile;

import java.io.*;
import java.net.*;

/**
 * envoie a destination de la socket UDP les morceaux de fichiers
 */

public class ThreadSender extends Thread{
    Socket sockComm;
    
    public ThreadSender(Socket s){
        sockComm=s;
    }
    
    public void run(){
        long debutMorceau, finMorceau;
        P2PFile leFichier;
        try{
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(sockComm.getInputStream()));
            leFichier=(P2PFile)ois.readObject();
            debutMorceau=ois.readLong();
            finMorceau=ois.readLong();
            if(sockComm!=null){
                sockComm.close();
            }
            
            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{
            try {
                if(sockComm!=null){
                    sockComm.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
