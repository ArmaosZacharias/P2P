// Alexis Armaos - Hélène Zacharias
package comServClient;

import java.io.Serializable;

/**
 * Represente une adresse P2PClient caracterise par:</br>
 * - IP de l'hote de l'app.</br>
 * - numero de port de sa socket d'ecoute</br>
 *
 * Doit redefinir:</br>
 * - equals()</br>
 * - hashcode()</br>
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
