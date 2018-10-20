/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketclientraw;

/**
 *
 * @author brucecolburn
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class SocketClientRaw {
        
    public static void main(String[] args) throws Exception {
        String userName = "";
        String serverName = "";
        int serverPort = -1;
        boolean canConnect = false;
        
        Scanner userInput = new Scanner(System.in);
        Scanner userServ = new Scanner(System.in);
        Scanner userPort = new Scanner(System.in);
        
        while (canConnect == false) {
            System.out.print("IP or domain name: ");
            serverName = userServ.nextLine();
        
            System.out.print("Port number: ");
                try { serverPort = userPort.nextInt(); }
                catch (InputMismatchException e) { serverPort = -1; }
            
            if ((! "".equals(serverName)) && (serverPort > 0)) canConnect = true;
            else System.out.println("Server or Port invalid...");
        }
        
        try {
            //Socket for server and port
            Socket sock = new Socket(serverName, serverPort);
            //Keyboard Stream
            BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));
            //Output to server
            OutputStream ostream = sock.getOutputStream(); 
            PrintWriter pwrite = new PrintWriter(ostream, true);
            //Input from server
            InputStream istream = sock.getInputStream();
            BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));

            System.out.println("Connected to ChatServ!");
            String receiveMessage, sendMessage;               
            while(true) {
                if ((sendMessage = keyRead.readLine()) != null) {
                    if ("/exit".equals(sendMessage)) {
                        System.out.println("Exiting program...");
                        sock.close();
                        System.out.println("Connection closed, goodbye!");
                        System.exit(0);
                    }
                    pwrite.println(sendMessage);
                    pwrite.flush();
                }

                if((receiveMessage = receiveRead.readLine()) != null) { 
                    if (! receiveMessage.equals(sendMessage)) System.out.println(receiveMessage);
                    // UNCOMMENT BELOW LINE AND COMMENT ABOVE IF FOR SERVER ECHO DISPLAY!!!
                    //System.out.println(receiveMessage);
                }         
            }
        }
        catch (SocketException e) {
            System.out.println("Socket Failed: " + e);
            System.out.println("Exiting...");
            System.exit(0);
        }
        catch (IOException e) {
            System.out.println("IO Failure: " + e);
            System.out.println("Exiting...");
            System.exit(0);
        }
    }                    
}
