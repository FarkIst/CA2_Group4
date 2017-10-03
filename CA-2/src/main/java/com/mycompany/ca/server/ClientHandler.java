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


public class ClientHandler {
        private String username;
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
 
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
 
        public void run() throws IOException {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(
              new InputStreamReader(clientSocket.getInputStream()));
             
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("STOP".equals(inputLine)) {
                    out.println("bye");
                    break;
                }
                out.println(inputLine);
            }
 
            in.close();
            out.close();
            clientSocket.close();
    }
}
