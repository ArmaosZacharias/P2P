// Alexis Armaos - Hélène Zacharias
package comServClient;

import java.io.Serializable;

/**
 * représente une adresse P2PClient caracterise par:
 * - IP de l'hote de l'app.
 * - numero de port de sa socket d'ecoute
 *
 * doit redefinir:
 * - equals()
 * - hashcode()
 */

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
