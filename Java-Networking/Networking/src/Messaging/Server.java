package Messaging;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import util.SocketWrapper;

public class Server {
    int clientNumber=0;
    ServerSocket ss;
    HashMap<User,SocketWrapper> clientMap;
    List<CreateGroup> groups;

    Server(){
        this.clientMap = new HashMap<>();
        this.groups = new ArrayList<CreateGroup>();
        try{
            ss = new ServerSocket(44444);
            System.out.println("Server is running on port 44444");
            while (true) {
                Socket clientSocket = ss.accept();
                this.clientNumber++;
                System.out.println("Client connected.");
                serve(clientSocket);        
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public void serve(Socket clientSocket){

        try{

            SocketWrapper serverSocketWrapper = new SocketWrapper(clientSocket);
            Object o = serverSocketWrapper.read();
            if(o instanceof String){

             String clientName = (String)o;
             System.out.println("Hello" +" "+ clientName);
             User user = new User(clientName);
             System.out.println(user);
             clientMap.put(user,serverSocketWrapper);
             System.out.println("Reached");
           }

           new ReadThreadServer(serverSocketWrapper,clientMap,groups);

        }catch(Exception e){

            System.out.println(e.getMessage());

        }
    }

    public static void main(String[] args) {
        new Server();
    }
    
}
