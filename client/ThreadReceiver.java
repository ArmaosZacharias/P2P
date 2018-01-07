package client;

import java.io.*;
import java.net.*;
import comServClient.*;

public class ThreadReceiver extends Thread{
    AddressServerTcp ip;
    
    public ThreadReceiver(AddressServerTcp address){
        ip=address;
    }
    
    public void run(){
        
    }
}
