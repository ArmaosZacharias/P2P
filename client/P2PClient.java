package client;

import comServClient.*;
import java.io.*;
import java.net.*;
import java.util.*;

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
            sockComm = new Socket(ipServ, portServ);
            oos = new ObjectOutputStream(new BufferedOutputStream(sockComm.getOutputStream()));
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
                                System.out.println(paires.toString());
                            }
                            catch(ClassNotFoundException e){
                                e.printStackTrace();
                            }
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