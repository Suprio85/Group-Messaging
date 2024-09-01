package Messaging;
import util.SocketWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadThreadServer implements Runnable {
    SocketWrapper serverSocketWrapper;
    Thread serverReadThread;
    HashMap<User, SocketWrapper> clientMap;
    List<CreateGroup> groups;

    public ReadThreadServer(SocketWrapper sc, HashMap<User, SocketWrapper> clHashMap, List<CreateGroup> groups) {
        this.serverSocketWrapper = sc;
        this.clientMap = clHashMap;
        serverReadThread = new Thread(this, "Server Read Thread");
        serverReadThread.start();
        this.groups = groups;

    }

    public void run() {
        try {
            System.out.println(serverReadThread.getName());
            while (true) {
                Object o = this.serverSocketWrapper.read();
                if (o instanceof Message) {
                    Message msg = (Message) o;
                    String To = msg.getTo();
                    var keys = clientMap.keySet();
                    User user = null;
                    for (User u : keys) {
                        if (u.name.equalsIgnoreCase(To)) {
                            user = u;
                            break;
                        }
                    }

                    System.out.println(user);

                    if (user == null) {
                        serverSocketWrapper.write("User Not Found");
                        System.out.println("User Not Found");
                    } else {
                        SocketWrapper sc = clientMap.get(user);
                        if (sc == null) {
                            System.out.println("No socket Found");
                        } else {
                            try {
                                sc.write(msg);
                                System.out.println("Message sent successfully to " + user.name + " " + user.id);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                                sc.closeConnection();
                            } 
                        }
                    }
                }
                
                else if(o instanceof CreateGroup){
                    CreateGroup cg = (CreateGroup)o;
                    if(groups.contains(cg)){
                        System.out.println("Group Already Exist");
                    }else{
                        groups.add(cg);
                        System.out.println("Group Created Sucessfully");
                    }
                }

                else if(o instanceof Group){

                    Group grp = (Group)o;
                    String grpName = grp.Name;
                    List<String> memList=new ArrayList<String>();
                    for (CreateGroup group : groups) {
                        if(group.Name.equalsIgnoreCase(grpName)){
                            memList = group.members;
                            break;
                        }
                    }
                    List<User> users = new ArrayList<User>();

                    var keys = clientMap.keySet();
                     
                    for (String mem : memList ) {
                        for (User user : keys) {
                            if(mem.equalsIgnoreCase(user.name))
                               users.add(user);   
                        }
                    }

                    for (User user : users) {
                        SocketWrapper sc = clientMap.get(user);

                        try{
                           String text ="Group Name: "+grp.Name+ "From: "+ grp.From+" "+"Text: "+grp.groupMsg;

                            sc.write(text);
                        }catch(Exception e){
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                        }
                        
                    }
                    
                }
            }

        } catch (Exception e) {
            System.out.println("Somthing went wrong while reading client.");
            System.out.println(e.getMessage());
            e.printStackTrace();
            try {
                serverSocketWrapper.closeConnection();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
                ioe.printStackTrace();
            }
        } finally {
            try{
                serverSocketWrapper.closeConnection();
            }catch(IOException e){
                e.printStackTrace();
            }
        }

    }

}
