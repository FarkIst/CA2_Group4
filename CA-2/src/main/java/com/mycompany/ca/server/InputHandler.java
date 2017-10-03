/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ca.server;

/**
 *
 * @author AR
 */
public class InputHandler {
    private final ClientHandler clientHandler;
    private final ChatProtocolServer server;
    public InputHandler(ClientHandler c, ChatProtocolServer server)
    {
        this.clientHandler = c;
        this.server = server;
    }
    
    public void initalize(String s){
        if (s.length()>7)
        {
            System.out.println("TRUUUE");
            if (s.substring(0,6).equals("LOGIN:")){
                clientHandler.setUsername(s.substring(6,s.length()));
                server.clientReport();
            }else{
                System.out.println(s.substring(0,6)+" !=  "+"LOGIN:");
                clientHandler.closeClient();
            }
        }else{
           clientHandler.closeClient();
        }
    }
    
    public void handleInput(String s){
        if (s.equals("LOGOUT:"))
        {
            clientHandler.closeClient();
            server.clientReport();
        }
        if (s.length()>6 && s.substring(0,4).equals("MSG:"))
        {
            System.out.println("BEFORE BOOLEAN");
            Boolean stringHandled = server.containsClient(s.substring(4, s.length()), clientHandler.getUsername());
            System.out.println("AFTER BOOLEAN");
            if (!stringHandled)
            {
                clientHandler.closeClient();
            }
        }
        
        
    }
   
}
