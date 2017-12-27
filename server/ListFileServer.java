package server;

import comServClient.P2PFile;
import java.util.*;
import java.io.*;

public class ListFileServer {
    private ArrayList listClientFichier;
    
    public ListFileServer() {
        listClientFichier=new ArrayList(new ArrayList());     //Liste d'une ip d'un client et d'un fichier
    }
    
    public synchronized void addFiles(String ip, File list) {
        
    }
    
    public void afficherList() {

    }
    
//    public  getFileList() {
//        
//    }
}

