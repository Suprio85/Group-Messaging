package Messaging;
import java.io.IOException;

import util.SocketWrapper;
public class ReadThreadClient implements Runnable{
SocketWrapper clienSocketWrapper;
Thread clientReadThread;


 public ReadThreadClient(SocketWrapper cs){
    this.clienSocketWrapper = cs;
    this.clientReadThread = new Thread(this,"Client Read Thread");
    clientReadThread.start();
 }

    public void run(){
        try{
            System.out.println(clientReadThread.getName());
            while(true){
                Object o = clienSocketWrapper.read();
                if(o instanceof Message){
                    Message msg = (Message)o;
                    System.out.println("New Message Recived!");
                    System.out.println("From: "+ msg.getFrom());
                    System.out.println("To: "+msg.getTo());
                    System.out.println("Text: " +msg.getText());
                }

                if(o instanceof String){
                    String msg = (String)o;
                    System.out.println(msg);
                }

                if(o==null){
                    System.out.println("null found");
                }


            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }finally{
            try {
                clienSocketWrapper.closeConnection();
            } catch (IOException ioe) {
                System.out.println("somtheing wrong closing connection"+" "+ioe.getMessage());
                ioe.printStackTrace();
            }
        }

    }
    
}
