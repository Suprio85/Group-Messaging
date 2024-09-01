package Messaging;
import java.util.Scanner;

import util.SocketWrapper;

public class Client {

    Client(String serverAdress ,  int serverPort){
            System.out.println("Enter name of the client: ");

            Scanner scanner = new Scanner(System.in);
            String clientName = scanner.nextLine();
        try{
            SocketWrapper clientSocketWrapper = new SocketWrapper(serverAdress, serverPort);
            clientSocketWrapper.write(clientName);

            new ReadThreadClient(clientSocketWrapper);
            new writeThreadClient(clientSocketWrapper, clientName);

        }catch(Exception e){
            System.out.println(e.getMessage());
            scanner.close();
        }
    }
    public static void main(String[] args) {
        new Client("127.0.0.1", 44444);
    }
}
