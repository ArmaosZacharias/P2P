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
                int contient=-1;
                for(int i=0;i<listClientFichier.size();i++){
                    if(((P2PFile)((ArrayList)listClientFichier.get(i)).get(0)).equals(new P2PFile(f))){
                        contient=i;
                    }
                }
                if(contient==-1){
                    ArrayList ipAndFile = new ArrayList();
                    ipAndFile.add(new P2PFile(f));                    
                    ipAndFile.add(ip);
                    listClientFichier.add(ipAndFile);
                }
                else{
                    ((ArrayList)listClientFichier.get(contient)).add(ip);
                }
            }
        }
    }
    
    public void afficherList() {
        for(int i=0;i<listClientFichier.size();i++){
            P2PFile f = (P2PFile)((ArrayList)listClientFichier.get(i)).get(0);
            String owners="";
            for(int j=1;j<((ArrayList)listClientFichier.get(i)).size();j++){
                owners+=((String)((ArrayList)listClientFichier.get(i)).get(j))+";";
            }
            System.out.println("- "+f.getFile().getName()+"\t\t"+f.getTaille()+" octets\t\t"+owners);
        }
    }
    
//    public  getFileList() {
//        
//    }
}

