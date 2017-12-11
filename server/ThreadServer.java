package server;
import java.net.*;
import java.io.*;

public class ThreadServer extends Thread {
    private Socket sockComm = null;

    public ThreadServer(Socket comm) {
        this.sockComm = comm;
    }
    public void run() {
        InputStream is;
        OutputStream os;
        InputStreamReader isr;
        OutputStreamWriter osw;
        try {
            is = sockComm.getInputStream();
            isr = new InputStreamReader(new BufferedInputStream(is));
            os = sockComm.getOutputStream();
            osw = new OutputStreamWriter(new BufferedOutputStream(os));
            osw.flush();
            boolean fin = false;
            while (!fin) {
                try {



                } catch (NumberFormatException e) {
                    System.out.println("Mauvais format de nombre!");
                    osw.write("Mauvais format de nombre");
                    osw.flush();
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