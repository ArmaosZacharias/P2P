package comServClient;
import java.net.*;
import java.io.*;

public class AddressServerTcp implements Serializable{
    private String adresse;
    private int port;
    
    public AddressServerTcp(Socket s){
        adresse=s.getInetAddress().getHostAddress();
        port=s.getPort();
    }
    
    public String toString(){
        return adresse+":"+port;
    }
    
    public boolean equals(AddressServerTcp ast){
        return this.toString().equals(ast.toString());
    }
}
