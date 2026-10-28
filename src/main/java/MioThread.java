
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MioThread implements Runnable {

    boolean quit = false;
    public Socket mySocket;
    public HashMap <String, Integer> disponibilit = new HashMap<>();
    public List usernames = new ArrayList<String>();
    
    public MioThread(Socket socket, HashMap <String, Integer> disponibilit, List usernames) {
        this.mySocket = socket;
        this.disponibilit = disponibilit;
        this.usernames = usernames;
    }

    @Override
    public void run() {
        BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
        PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);

        out.println("WELCOME");

        while (true) {
            String loginUsername = in.readLine();
            String[] parti = loginUsername.split(" ");
            if (parti.length != 2 && parti[0].equals("LOGIN")) {
                if (!usernames.contains(parti[1])) {
                    usernames.add(parti[1]);
                    break;
                }
                out.println("ERR USERNAME");
            }
            out.println("OK");
        }


        do {
            String comandoClient = in.readLine();
            switch (comandoClient) {
                case "BUY":
                    // leggo dal socket il comando --> <tipo biglietto> <quantita>                    
                    String sceltaBiglietto = in.readLine();

                    // faccio il parse delle due parti del comando
                    String[] parti = sceltaBiglietto.split(" ");

                    //la prima parte [0] e' il tipo di biglietto (stringa)
                    String tipoB = parti[0];

                    //la seconda parte [1] e' la quantita (faccio il parse ad int)
                    Integer quantita = Integer.parseInt(parti[1]);
                    
                    // per capire la disponibiltia' uso l'HASHMAP usando come "chiave" il tipo di biglietto
                    //ATTENZIONE: il tipo di biglietto scritto nel comando dal cliente deve essere ESATTAMENTE uguale alla chiave
                    Integer disp = disponibilit.get(tipoB) ; 

                    if(quantita > disp){
                        out.println("KO");                    
                    }
                    else{
                        // con put aggiorno l'hashmap proprio sulla chiave del tipo di biglietto
                        disponibilit.put(tipoB, disp - quantita); 
                        out.println("OK");
                    }
                    break;

                case "N":
                    out.println(disponibilit);
                    break;

                case "QUIT":
                    out.println("BYE");
                    quit = true;
                    break;

                default:
                    out.println("ERR UNKNOWNCMD");
                    break;
            }
        } while (quit == false);



    }
}
