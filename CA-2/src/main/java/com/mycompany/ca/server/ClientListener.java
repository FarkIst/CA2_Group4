package com.mycompany.ca.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

//Thread for listening for clients joining server
public class ClientListener extends Thread {
    private ChatProtocolServer cps;
    ClientHandler client = null;
    ServerSocket serversocket ;
    
    public ClientListener(ChatProtocolServer cps)
    {
        this.cps = cps;
        serversocket = cps.getServerSocket();
    }
    
    public void run()
    {
        boolean running = true;
        while (running)
        {
            try {
                client = new ClientHandler( serversocket.accept(), cps);
                Thread t = new Thread(client);
                client.addObserver(cps);
                cps.addClient(client);
                t.start();
            } catch (IOException ex) {
                Logger.getLogger(ClientListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
