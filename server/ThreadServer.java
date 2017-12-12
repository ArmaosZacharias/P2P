package server;
import comServClient.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ThreadServer extends Thread {
    private Socket sockComm = null;

    public ThreadServer(Socket comm) {
        this.sockComm = comm;
    }
    public void run() {
        InputStream is;
        OutputStream os;
        ObjectInputStream ois;
        ObjectOutputStream oos;
        try {
            is = sockComm.getInputStream();
            ois = new ObjectInputStream(new BufferedInputStream(is));
            os = sockComm.getOutputStream();
            oos = new ObjectOutputStream(new BufferedOutputStream(os));
            oos.flush();
            boolean fin = false;
            while (!fin) {
                try {
                    String requete = ois.readUTF();
                    System.out.println("DEBUG: réception de la requete: " + requete);
                    TreeSet<P2PFile> resultatSearch = null;
                    String requeteTab [] = requete.split(" ");
                    if (requeteTab[0].equals("search")) {
                        String pattern = requeteTab[1];
                        // instructions de recherche du <pattern> dans la liste des fichiers
                    } else if (requeteTab[0].equals("get")) {
                        if(resultatSearch!=null){
                            try{
                                int num = Integer.parseInt(requeteTab[1]);
                            }
                            catch(NumberFormatException e){
                                oos.writeInt(1);  //renvoie le cas "help"
                                oos.flush();
                            }
                        }
                        else{
                            oos.writeInt(2);  //renvoie le cas "search first"
                            oos.flush();
                        }
                        // téléchargement du fichier numéro <num> dans la liste des résultats
                        // l’application P2PClient doit commencer par vérifier si elle ne possède pas déjà le fichier ciblé
                        // Si elle ne le possède pas, elle doit envoyer une requête de « téléchargement » à l’application P2PServer.
                        // En réponse, elle reçoit une liste contenant les adresses de toutes les applications
                        // P2PClient qui possèdent le fichier à télécharger.
                    } else if (requeteTab[0].equals("list")) {
                        if(resultatSearch!=null){
                            oos.writeInt(4);  //renvoie le cas "list"
                            oos.flush();
                        }
                        else{
                            oos.writeInt(2);  //renvoie le cas "search first"
                            oos.flush();
                        }
                    } else if (requeteTab[0].equals("local") && requeteTab[1].equals("list")) {
                        oos.writeInt(5);  //renvoie le cas "local list"
                        oos.flush();
                    } else if (requeteTab[0].equals("quit")) {
                        oos.writeInt(3);  //renvoie le cas "quit"
                        oos.flush();
                    }
                    else {
                        oos.writeInt(1);  //renvoie le cas "help"
                        oos.flush();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Mauvais format de nombre!");
                    oos.writeUTF("Mauvais format de nombre");
                    oos.flush();
                } /*catch (EOFException e) {
                    System.out.println("FIN du thread serveur");
                    fin = true;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        } catch (IOException e) {
            System.out.println("Problème de communication " + e.toString());
        } finally {
            try {
                if (sockComm != null)
                    sockComm.close();
            } catch(IOException e) {
                e.printStackTrace();
                System.out.println("Erreur IO2");
            }
        }
    }
}