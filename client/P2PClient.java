// Alexis Armaos - Hélène Zacharias
package client;

import comServClient.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Arguments à entrer en ligne de commande:
 * - IP de l'hôte du P2PServer
 * - numéro de port de la socket d'écoute TCP du P2PServer
 * - chemin vers le répertoire contenant les fichiers du P2PServer
 *
 * - numéro de port de cette socket d'écoute: choisi automatiquement par le système de l'hôte qui l'héberge (mettre 0 en paramètre dans le ServerSocket)
 *
 * - test la validité des arguments
 * - détermine l'IP de l'hôte qui l'héberge
 * - ouvre le répertoire (voir chemin en argument) pour lister ce qu'il contient: constitue la liste des fichiers possédé par l'app.
 * - créé une socket d'écoute TCP (avec paramètre 0)
 * - se connecte au P2PServer avec adresse en argument (la connexion reste ouverte jusqu'à la fin du P2PClient)
 * - créé un thread (ThreadClient) destiné à accepter les demandes de connexion des autres P2PClients sur la socket d'écoute créée
 * - démarrer le thread
 * - transmet au P2PServer la liste de fichiers qu'elle possède
 *
 * Ensuite, entre dans le mode interface avec l'utilisateur:
 * - affiche le menu
 * - attends que l'utilisateur entre une requête
 *
 * Requêtes:
 * - search <pattern>
 * - get <num>
 * - list
 * - local list
 * - quit
 *
 * get <num>: doit vérifier si elle possède déjç le fichier ciblé
 * (si oui, recoit la liste les adresses de P2PClient avec le fichier,
 * si non, envoie une requete de téléchargement au P2PServer)
 */

public class P2PClient {
    
    public static void main(String[] args) {
        String ipServ ;
        int portServ = 0 ;
        Socket sockComm = null;
        ServerSocket sockConn = null; // socket d’écoute TCP
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        String requete;
        ListFileClient lfc;
        
        if (args.length != 3) {
            System.out.println("Nombre d'arguments non valide !");
            System.exit(0);
        }

        ipServ = args[0]; // IP de l'hôte de l'application P2PServer

        try {
            portServ = Integer.parseInt(args[1]); // n° de port de la socket d’écoute TCP de l’application P2PServer
        } catch (NumberFormatException e) {
            System.out.println("Numéro de port non valide !");
            System.exit(1);
        }

        File repertoire = new File(args[2]); // chemin vers le répertoire contenant les fichiers de l’application P2PClient

        if (!repertoire.exists()&&!repertoire.isDirectory()) {
            System.out.println("Le répertoire n'existe pas");
            System.exit(2);
        }

        lfc = new ListFileClient(repertoire);

        try {
            sockConn=new ServerSocket(0);
            ThreadClient tc=new ThreadClient(sockConn);
            tc.start();
            sockComm = new Socket(ipServ, portServ);
            oos = new ObjectOutputStream(new BufferedOutputStream(sockComm.getOutputStream()));
            oos.writeInt(sockConn.getLocalPort());
            oos.flush();
            oos.writeObject(new ListFile(lfc.getFileList()));
            oos.flush();
            ois = new ObjectInputStream(new BufferedInputStream(sockComm.getInputStream()));
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            boolean quit=false;
            do {
                System.out.println("Entrer votre requête : ");
                requete = br.readLine();
                if (!quit){
                    try {
                        oos.writeUTF(requete);
                        oos.flush();
                        
                        int reponse = ois.readInt();
                        if(reponse == 1){   //cas 'help'
                            System.out.println("Commande inconnue, essayez : \n- 'search <pattern>' pour récupérer la liste des fichiers dont le nom contient <pattern>\n- 'get <num>' pour télécharger le fichier numéro <num> de la liste obtenue vià le search\n - 'list' permet d'afficher la liste courante\n - 'local list' permet d'afficher la liste de vos fichiers\n - 'quit' vous permet de quitter");
                        } else if(reponse==2){   //cas 'search first'
                            System.out.println("Veuillez d'abord effectuer un search");
                        } else if(reponse==3){   //cas 'quit'
                            System.out.println("Vous quittez l'application...");
                            quit=true;
                            oos.writeObject(new ListFile(lfc.getFileList()));
                            oos.flush();
                        } else if(reponse==4){ //cas 'list'
                            System.out.println(ois.readUTF());
                        } else if(reponse==5){  //cas 'local list'
                            lfc.afficherList();
                        } else if(reponse==6){  //cas 'get'
                            try{
                                ArrayList<AddressServerTcp> paires=(ArrayList<AddressServerTcp>)ois.readObject();
                                P2PFile f=(P2PFile)ois.readObject();
                                long dernierMorceau=f.getTaille()%1024;
                                long k=f.getTaille()/1024;
                                if(dernierMorceau>0)k++;
                                long nbMorceauxParClient=k/paires.size();
                                long debutMorceau=0;
                                long finMorceau=nbMorceauxParClient-1;
                                 for(AddressServerTcp address : paires){
                                    if(address.equals(paires.get(paires.size()-1)))finMorceau=k-1;
                                    ThreadReceiver tr=new ThreadReceiver(address, f, debutMorceau, finMorceau);
                                    tr.start();
                                    debutMorceau+=nbMorceauxParClient;
                                    finMorceau+=nbMorceauxParClient;
                                }
                            }
                            catch(ClassNotFoundException e){
                                e.printStackTrace();
                            }
                        } else if(reponse==7){
                            System.out.println("Vous possédez déjà ce fichier");
                        }
                        else{
                            System.out.println("Erreur, réessayer");
                        }
                    } catch(IOException e){
                        System.out.println(e.getMessage());
                    }
                }
            } while (!quit);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            try {
                if (sockConn != null) {
                    sockConn.close();
                }
                if(sockComm!=null){
                    sockComm.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("Erreur IO2");
            }
        }
    }
}