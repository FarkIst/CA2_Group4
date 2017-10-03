/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ca.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ClientHandler extends Observable implements Runnable{
    private String username;
    private final Socket clientSocket;
    private PrintWriter out;
    private Scanner scan ;
    private final ChatProtocolServer server;
    private final InputHandler inputHandler;

    public ClientHandler(Socket socket, ChatProtocolServer s) throws IOException {
        this.clientSocket = socket;
        this.server = s;
        this.inputHandler = new InputHandler(ClientHandler.this, server);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        scan = new Scanner(clientSocket.getInputStream());
    }

    @Override
    public void run(){
        String inputLine = scan.nextLine();
        inputHandler.initalize(inputLine);
        while (!inputLine.equals("stop"))  {
            inputLine = scan.nextLine();
            inputHandler.handleInput(inputLine);
        }
        closeClient();
    }
        
   //Use this method to close the socket
    public void closeClient()
    {
        try {
            this.clientSocket.close();
            scan.close();
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Update the observers
        this.setChanged();              
        this.notifyObservers(this);
    }
        
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
    
    
    public Socket getClientSocket() {
        return clientSocket;
    }
}
