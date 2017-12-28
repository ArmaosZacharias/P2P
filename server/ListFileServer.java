package server;

import comServClient.P2PFile;
import java.util.*;
import java.io.*;

public class ListFileServer {
    private ArrayList listClientFichier;
    
    public ListFileServer() {
        listClientFichier=new ArrayList(new ArrayList());     //Liste d'une ip d'un client et d'un fichier (P2PFile)
    }
    
    public void addFiles(String ip, File rep) {
        for (File f : rep.listFiles()) {
            if (f.isDirectory()) {
                addFiles(ip, f);
            }
            else{
                ArrayList ipAndFile = new ArrayList();
                ipAndFile.add(ip);
                ipAndFile.add(new P2PFile(f));
                listClientFichier.add(ipAndFile);
            }
        }
    }
    
    public void afficherList() {
        for(int i=0;i<listClientFichier.size();i++){
            String ip = (String)((ArrayList)listClientFichier.get(i)).get(0);
            P2PFile f = (P2PFile)((ArrayList)listClientFichier.get(i)).get(1);
            
            System.out.println("- "+f.getFile().getName()+"\t\t"+f.getTaille()+" octets\t\t"+ip);
        }
    }
    
//    public  getFileList() {
//        
//    }
}

