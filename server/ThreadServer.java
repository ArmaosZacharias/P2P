package server;

import comServClient.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class ThreadServer extends Thread {
    private Socket sockComm = null;
    private ListFileServer lfs=null;

    public ThreadServer(Socket comm, ListFileServer list) {
        this.sockComm = comm;
        this.lfs=list;
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
            AddressServerTcp ast;
            String resultatSearch = null;
            try{
                ast=new AddressServerTcp(sockComm.getInetAddress().getHostAddress(), ois.readInt());
                lfs.addFiles(ast, (ListFile)ois.readObject());
                boolean fin = false;

                while (!fin) {
                    try {
                        String requete = ois.readUTF();
                        System.out.println("DEBUG: réception de la requete: " + requete+" par "+ast.toString());
                        String requeteTab [] = requete.split(" ");

                        if (requeteTab[0].equals("search")) {
                            try{
                                String pattern = requeteTab[1];
                                resultatSearch=lfs.chercherList(pattern);
                                oos.writeInt(4); //renvoie le cas "list"
                                oos.writeUTF(resultatSearch);
                                oos.flush();
                            }
                            catch(ArrayIndexOutOfBoundsException e){
                                oos.writeInt(1);  //renvoie le cas "help"
                                oos.flush();
                            }
                        } else if (requeteTab[0].equals("get")) {
                            if (resultatSearch!=null) {
                                try {
                                    int num = Integer.parseInt(requeteTab[1]);
                                    P2PFile f=lfs.getFile(num);
                                    ArrayList<AddressServerTcp> paires=lfs.getPaires(num);
                                    if(paires==null){
                                        oos.writeInt(1);  //renvoie le cas "help"
                                        oos.flush();
                                    }
                                    else{
                                        if(paires.contains(ast)){
                                            oos.writeInt(7);  //le client a déjà le fichier
                                            oos.flush();
                                        } else {
                                            oos.writeInt(6);  //renvoie le cas "get"
                                            oos.flush();
                                            oos.writeObject(paires);
                                            oos.flush();
                                            oos.writeObject(f);
                                            oos.flush();
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    oos.writeInt(1);  //renvoie le cas "help"
                                    oos.flush();
                                }
                                catch(ArrayIndexOutOfBoundsException e){
                                    oos.writeInt(1);  //renvoie le cas "help"
                                    oos.flush();
                                }
                            } else {
                                oos.writeInt(2);  //renvoie le cas "search first"
                                oos.flush();
                            }
                            // téléchargement du fichier numéro <num> dans la liste des résultats
                            // l’application P2PClient doit commencer par vérifier si elle ne possède pas déjà le fichier ciblé
                            // Si elle ne le possède pas, elle doit envoyer une requête de « téléchargement » à l’application P2PServer.
                            // En réponse, elle reçoit une liste contenant les adresses de toutes les applications
                            // P2PClient qui possèdent le fichier à télécharger.
                        } else if (requeteTab[0].equals("list")) {
                            if (resultatSearch!=null) {
                                oos.writeInt(4);  //renvoie le cas "list"
                                oos.flush();
                                oos.writeUTF(resultatSearch);
                                oos.flush();
                            } else {
                                oos.writeInt(2);  //renvoie le cas "search first"
                                oos.flush();
                            }
                        } else if (requeteTab[0].equals("local")) {
                            try{
                                if(requeteTab[1].equals("list")){
                                    oos.writeInt(5);  //renvoie le cas "local list"
                                    oos.flush();
                                }
                                else{
                                    oos.writeInt(1);  //renvoie le cas "help"
                                    oos.flush();
                                }
                            }
                            catch(ArrayIndexOutOfBoundsException e){
                                oos.writeInt(1);  //renvoie le cas "help"
                                oos.flush();
                            }
                        } else if (requeteTab[0].equals("quit")) {
                            oos.writeInt(3);  //renvoie le cas "quit"
                            oos.flush();
                            lfs.delFiles(ast, (ListFile)ois.readObject());
                        } else {
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
                    if (sockComm != null) {
                        sockComm.close();
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                    System.out.println("Erreur IO2");
                }
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}