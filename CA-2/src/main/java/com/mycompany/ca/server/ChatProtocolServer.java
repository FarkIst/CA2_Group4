/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.ca.server;



import java.net.ServerSocket;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Vector;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ChatProtocolServer implements Observer {
    private ServerSocket serverSocket;
    private Vector clientsList;
    
    public ChatProtocolServer()
    {
        this.clientsList = new Vector();
    }
 
    public void start(String IP, int PORT) throws IOException {
        serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(IP, PORT));
        ClientListener clientListener = new ClientListener(ChatProtocolServer.this);
        clientListener.start();
    }
 
    public void stop() throws IOException {
        serverSocket.close();
    }

    public ServerSocket getServerSocket()
    {
        return serverSocket;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        this.clientsList.removeElement(o);
    }
    
    public void addClient(ClientHandler ch)
    {
        clientsList.addElement(ch);
    }
    
    
    public void clientReport()
    {
        try {
            for (Object mon: clientsList)
            {
                PrintWriter p = null;
                PrintStream output;
                java.util.Enumeration e = this.clientsList.elements();
                String reportStr="CLIENTLIST : ";
                while(e.hasMoreElements())
                {
                    ClientHandler client = (ClientHandler)e.nextElement();
                    reportStr+=client.getUsername()+",";
                    System.out.println(client.getUsername());
                }
                reportStr = reportStr.substring(0,reportStr.length()-1);
                java.util.Enumeration v = this.clientsList.elements();
                while(v.hasMoreElements())
                {
                    ClientHandler client = (ClientHandler)v.nextElement();

                        output = new PrintStream(client.getClientSocket().getOutputStream());
                        System.out.println(reportStr);
                        output.println(reportStr);
                }
                
            }
        } catch (IOException ex) {
                    Logger.getLogger(ChatProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
                }   
    }
    
    public Boolean containsClient(String s, String sender){
        try {
        java.util.Enumeration v = this.clientsList.elements();
        java.util.Enumeration x = this.clientsList.elements();
        PrintWriter p = null;
        PrintStream output;
        int num = 0;
        System.out.println("BEFORE !CONTAINS : ");
        if (!s.contains(":"))
        {
            return false;
        }
            
        int startMsg = s.indexOf(":");
 
        String msg = s.substring(startMsg+1,s.length());
        List<String>nameList = new ArrayList();
        System.out.println("BEFORE SCONTAINS , ");
        if (s.contains(","))
        {
            System.out.println("IN COMMA IF STATEMENT");
            int commaLoc = s.indexOf(",");
            nameList.add(s.substring(0,commaLoc));
            nameList.add(s.substring(commaLoc+1,s.length()));
        }else
        {
            System.out.println("IN SINGLE ARRAYLIST SIZE");
            System.out.println(s.substring(0,s.indexOf(":")));
            if (s.substring(0,s.indexOf(":")).equals("*"))
            {
                
                while(x.hasMoreElements())
                {
                    System.out.println("GENERATING MY ARRAYLIST FROM STAR :))");
                    ClientHandler client = (ClientHandler)x.nextElement();
                    nameList.add(client.getUsername());
                }
            }
            nameList.add(s.substring(0,s.indexOf(":")));
        }
        for (String userString: nameList){
            System.out.println("IN FOR LOOP");
        while(v.hasMoreElements())
        {
            System.out.println("IN WHILE LOOP");
            ClientHandler client = (ClientHandler)v.nextElement();
            if ( client.getUsername().equals(userString) ){
                System.out.println("USERNAME MATCH TO USERSTRING");
                num++;
                output = new PrintStream(client.getClientSocket().getOutputStream());
                System.out.println("OUTPUTTING MSG");
                System.out.println("MSGRES:"+sender+":");
                output.println("MSGRES:"+sender+":"+msg);
                System.out.println("AFTER MESSAGE OUTPUT");
            }
        }
        if (num > 0)
        {  
            System.out.println("NICKNAME HIT!!"); 
            return true;
        }
        }
        } catch (IOException ex) {
            Logger.getLogger(ChatProtocolServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("WOOAH RETURNING FALSE");
        return false;
    }
    
    public static void main(String[] args) throws IOException {
        ChatProtocolServer server = new ChatProtocolServer(); 
        server.start("localhost", 8081);
    }
    
}
