// Alexis Armaos - Hélène Zacharias
package server;

import comServClient.*;

import java.util.*;

/**
 * Liste d'adresses des P2PClients</br>
 *
 * Attributs et methodes permettant de faciliter la construction des reponses aux requetes de recherche et de telechargement</br>
 *
 * Constitue une donnee partage entre les differents threads qui gerent les echanges de messages avec les P2PClients connectes</br>
 */

public class ListFileServer {
    private ArrayList listClientFichier;
    
    public ListFileServer() {
        listClientFichier=new ArrayList(new ArrayList());     
        //Chaque sous-liste de la liste listClientFichier a pour premier attribut le fichier (P2PFile) et les atributs suivants sont la liste des personnes ayant le fichier
    }
    
    public void addFiles(AddressServerTcp ast, ListFile files) {
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
                ipAndFile.add(ast);
                listClientFichier.add(ipAndFile);
            }
            else{
                ((ArrayList)listClientFichier.get(contient)).add(ast); 
            }
        }
    }
    
    public void delFiles(AddressServerTcp ast, ListFile files) {
        for (P2PFile f : files.getFileList()) {
            int contient=-1;
            for(int i=0;i<listClientFichier.size();i++){
                if(((P2PFile)((ArrayList)listClientFichier.get(i)).get(0)).equals(f)){
                    contient=i;
                }
            }
            if(contient!=-1){
                ((ArrayList)listClientFichier.get(contient)).remove(ast);
                if(((ArrayList)listClientFichier.get(contient)).size()==1){
                    listClientFichier.remove(((ArrayList)listClientFichier.get(contient)));
                }
            }
        }
    }
    
    public String chercherList(String pattern) {
        String res="";
        for(int i=0;i<listClientFichier.size();i++){
            P2PFile f = (P2PFile)((ArrayList)listClientFichier.get(i)).get(0);
            if(f.getFile().getName().contains(pattern)){
                String owners="";
                for(int j=1;j<((ArrayList)listClientFichier.get(i)).size();j++){
                    owners+=((AddressServerTcp)((ArrayList)listClientFichier.get(i)).get(j)).toString()+";";
                }
                res+=i+". "+f.getFile().getName()+"\t\t"+f.getTaille()+" octets\t\t"+owners+"\n";
            }
        }
        return res;
    }
    
    public ArrayList<AddressServerTcp> getPaires(int n){
        ArrayList<AddressServerTcp> p=new ArrayList<AddressServerTcp>();
        if(n>=listClientFichier.size()){
            return null;
        } else{
            for(int i=1;i<((ArrayList)listClientFichier.get(n)).size();i++){
                p.add(((AddressServerTcp)((ArrayList)listClientFichier.get(n)).get(i)));
            }
            return p;
        }
    }
    
    public P2PFile getFile(int numero){
        return (P2PFile)((ArrayList)listClientFichier.get(numero)).get(0);
    }
}

