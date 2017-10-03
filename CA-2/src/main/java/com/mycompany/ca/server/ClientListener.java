/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ca.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sean
 */
public class ClientListener extends Thread {
    private ChatProtocolServer cps;
    ClientHandler client = null;
    ServerSocket serversocket ;
    
    public ClientListener(ChatProtocolServer cps)
    {
        this.cps = cps;
        serversocket = cps.getServerSocket();
    }
    
    public void run(){
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
