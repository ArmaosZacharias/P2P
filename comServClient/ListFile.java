package comServClient;

import java.io.*;
import java.util.*;

public class ListFile implements Serializable{
    private ArrayList<P2PFile> fileList;
    
    public ListFile(ArrayList<P2PFile> list) {
        fileList=list;
    }
    
    public ArrayList<P2PFile> getFileList() {
        return fileList;
    }
}

