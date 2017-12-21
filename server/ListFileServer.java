package server;

import comServClient.P2PFile;
import java.util.TreeSet;

public class ListFileServer {
    private TreeSet<P2PFile> fileList;
    
    public ListFileServer() {
        fileList=new TreeSet<P2PFile>();
    }
    
    public void addFiles(TreeSet<P2PFile> tsf) {
        for(P2PFile f : tsf){
            fileList.add(f);
        }
    }
    
    public void afficherList() {
        for (P2PFile f : fileList) {
            System.out.println(f.getFile().getName()+"   "+f.getTaille()+" octets");
        }
    }
    
    public TreeSet<P2PFile> getFileList() {
        return fileList;
    }
}

