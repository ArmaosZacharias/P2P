package comServClient;

import java.io.*;

public class P2PFile implements Comparable<P2PFile>, Serializable {
    private File file;
    private long taille;
    
    public P2PFile(File f) {
        file=f;
        taille=f.length();
    }
    
    public File getFile() {
        return file;
    }

    public long getTaille() {
        return taille;
    }
    
    public int compareTo(P2PFile f) {
        return this.getFile().compareTo(f.getFile());
    }
    
    public boolean equals(P2PFile f){
        return this.getFile().equals(f.getFile());
    }
}
