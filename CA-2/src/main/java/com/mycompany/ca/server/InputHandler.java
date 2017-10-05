package com.mycompany.ca.server;

//Thread for handling user input
public class InputHandler {
    private final ClientHandler clientHandler;
    private final ChatProtocolServer server;
    public InputHandler(ClientHandler c, ChatProtocolServer server)
    {
        this.clientHandler = c;
        this.server = server;
    }
    
    //Handles @LOGIN{USERNAME}
    public void initalize(String s)
    {
        if (s.length()>7)
        {
            if (s.substring(0,6).equals("LOGIN:"))
            {
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
    
    //Handles rest of commands @MSG:{user,user}:{STRING}, @MSG:{user}{STRING}, @MSG:{*}:{STRING}, @LOGOUT:
    public void handleInput(String s){
        if (s.equals("LOGOUT:"))
        {
            clientHandler.closeClient();
            server.clientReport();
        }
        if (s.length()>6 && s.substring(0,4).equals("MSG:"))
        {
            Boolean stringHandled = server.containsClient(s.substring(4, s.length()), clientHandler.getUsername());
            if (!stringHandled)
            {
                clientHandler.closeClient();
            }
        }    
    }
}
