/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ca.server;



import java.net.ServerSocket;

import java.io.*;
import java.net.InetSocketAddress;



public class ChatProtocolServer {
    private ServerSocket serverSocket;
 
    public void start(String IP, int PORT) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(IP, PORT));
        while (true)
            new ClientHandler(serverSocket.accept()).run();
            
    }
 
    public void stop() throws IOException {
        serverSocket.close();
    }
    
    public static void main(String[] args) throws IOException {
        ChatProtocolServer server = new ChatProtocolServer(); 
        server.start("localhost", 8081);
    }
    
}
