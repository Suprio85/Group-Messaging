package Messaging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.SocketWrapper;

public class writeThreadClient implements Runnable {
    Message message;
    SocketWrapper clienSocketWrapper;
    String clientName;
    Thread clwtiteThread;

    public writeThreadClient(SocketWrapper clSocketWrapper, String name) {
        this.message = new Message();
        this.clienSocketWrapper = clSocketWrapper;
        this.clientName = name;
        this.clwtiteThread = new Thread(this, "client write thread");
        clwtiteThread.start();
    }

    private void createGroup(Scanner sc) {
        sc.nextLine();
        System.out.println("Enter Group Name:");
        String groupName = sc.nextLine();
        String member = null;

        List<String> memList = new ArrayList<>();
        memList.add(clientName);
        do {
            System.out.println("Type Members Name. Type Quit to exit");
            member = sc.nextLine();
            if (!member.equalsIgnoreCase("Quit")) {
                memList.add(member);
                System.out.println(member + "Added to the group");

            }
        } while (!member.equalsIgnoreCase("Quit"));

        CreateGroup grp = new CreateGroup(groupName, memList);

        try {
            clienSocketWrapper.write(grp);
        } catch (Exception e) {
            System.out.println("Error Creating Group" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendgroupMessage(Scanner sc) {
        sc.nextLine();
        System.out.println("Name of the Group? ");
        String grpName = sc.nextLine();
        System.out.println("Write a Message");
        String msg = sc.nextLine();
        Group grp = new Group(grpName, msg,clientName);

        try {
            clienSocketWrapper.write(grp);
        } catch (IOException e) {
            System.out.println("Error sending Group Message to Sever " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Message sent to the server");

    }

    private void sendMessage(Scanner sc)  {
        String from = clientName;
        System.out.print("Enter name of the client to send: ");
        String to = sc.nextLine();
        System.out.print("Enter the message: ");
        String text = sc.nextLine();
        Message message = new Message();
        message.setFrom(from);
        message.setTo(to);
        message.setText(text);
        try {
            clienSocketWrapper.write(message);

        } catch (IOException e) {
            System.out.println("error sending message to server");
            e.printStackTrace();
        }
        System.out.println("Message Sent succesfully to the server");
    }

    public void run() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.println(clwtiteThread.getName());
            do {
                System.out.println("What You want to do?");
                System.out.println("1.Create Group");
                System.out.println("2.Send Message to the Group");
                System.out.println("3.Send Message");

                int t = input.nextInt();
                // input.next();

                switch (t) {
                    case 1:
                        createGroup(input);
                        break;
                    case 2:
                        sendgroupMessage(input);
                        break;
                    case 3:
                        sendMessage(input);
                        break;
                    default:
                        break;
                }

            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                input.close();
                clienSocketWrapper.closeConnection();
            } catch (IOException ioe) {
                System.out.println("somtheing wrong closing connection" + " " + ioe.getMessage());
            }
        }

    }

}
