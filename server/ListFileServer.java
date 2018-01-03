package server;

import comServClient.*;
import java.util.*;
import java.io.*;

public class ListFileServer {
    private ArrayList listClientFichier;
    
    public ListFileServer() {
        listClientFichier=new ArrayList(new ArrayList());     
        //Chaque sous-liste de la liste listClientFichier a pour premier attribut le fichier (P2PFile) et les atributs suivants sont la liste des personnes ayant le fichier
    }
    
    public void addFiles(String ip, ListFile files) {
        for (P2PFile f : files.getFileList()) {
            int contient=-1;
            for(int i=0;i<listClientFichier.size();i++){
                if(((P2PFile)((ArrayList)listClientFichier.get(i)).get(0)).equals(f)){
                    contient=i;
                }
            }
            if(contient==-1){
                ArrayList ipAndFile = new ArrayList();
                ipAndFile.add(f);                    
                ipAndFile.add(ip);
                listClientFichier.add(ipAndFile);
            }
            else{
                ((ArrayList)listClientFichier.get(contient)).add(ip); 
            }
        }
    }
    
    public void delFiles(String ip, ListFile files) {
        for (P2PFile f : files.getFileList()) {
            int contient=-1;
            for(int i=0;i<listClientFichier.size();i++){
                if(((P2PFile)((ArrayList)listClientFichier.get(i)).get(0)).equals(f)){
                    contient=i;
                }
            }
            if(contient!=-1){
                ((ArrayList)listClientFichier.get(contient)).remove(ip);
                if(((ArrayList)listClientFichier.get(contient)).size()==1){
                    listClientFichier.remove(((ArrayList)listClientFichier.get(contient)));
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
    
    public String chercherList(String pattern) {
        String res="";
        for(int i=0;i<listClientFichier.size();i++){
            P2PFile f = (P2PFile)((ArrayList)listClientFichier.get(i)).get(0);
            if(f.getFile().getName().contains(pattern)){
                String owners="";
                for(int j=1;j<((ArrayList)listClientFichier.get(i)).size();j++){
                    owners+=((String)((ArrayList)listClientFichier.get(i)).get(j))+";";
                }
                res+=i+". "+f.getFile().getName()+"\t\t"+f.getTaille()+" octets\t\t"+owners+"\n";
            }
        }
        return res;
    }
}

