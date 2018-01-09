package comServClient;
import java.net.*;
import java.io.*;

public class AddressServerTcp implements Serializable{
    private String adresse;
    private int port;
    
    public AddressServerTcp(String ip, int p){
        adresse=ip;
        port=p;
    }
    
    public String toString(){
        return adresse+":"+port;
    }
    
    public boolean equals(AddressServerTcp ast){
        return this.toString().equals(ast.toString());
    }
    
    public String getAdresse(){ return adresse; }
    public int getPort(){ return port; }
}
