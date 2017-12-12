package client;
import comServClient.*;
import java.io.*;
import java.util.*;


public class ListFileClient {
    private TreeSet<P2PFile> fileList;
    
    public ListFileClient(File folder){
        fileList=new TreeSet<P2PFile>();
        listFilesFolder(folder);
    }
    
    private void listFilesFolder(File folder) {
        for (File f : folder.listFiles()) {
            if (f.isDirectory()) {
                listFilesFolder(f);
            }
            else{
                fileList.add(new P2PFile(f));
            }
        }
    }
    
    public void afficherList(){
        for(P2PFile f : fileList){
            System.out.println("- "+f.getFile().getName()+"\t\t"+f.getTaille()+" octets");
        }
    }
    
    public TreeSet<P2PFile> getFileList(){ return fileList; }
}

