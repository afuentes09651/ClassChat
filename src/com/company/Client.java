package com.company;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

    public void main() throws Exception {
        Scanner reader = new Scanner(System.in); //set up user input
        Socket sock = new Socket("localhost", 42069);
       DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        System.out.println("Connection established");

        //Part 2 code
        DataInputStream dis = new DataInputStream(sock.getInputStream()); //make an input stream
        Thread listener = new ResponseListener(dis);
        listener.start();

        System.out.print("\nEnter message for server: ");
        String message = reader.nextLine();
        dos.writeUTF(message);//prepare message
        dos.flush(); //send message


        //------ close everything plz
       // dos.close();
        //dis.close();
      //  sock.close(); //close connection
    }

    //Response Listener
}

class MessageHandler extends Thread{

}

class ResponseListener extends Thread{
    DataInputStream dis;

    ResponseListener(DataInputStream dis){
        this.dis = dis;
    }

    public void run(){
        try{
            System.out.println("Now just awaiting server message...");
            while(true){
                if(dis.available() > 0){ //checking if there are readable bytes available
                    System.out.println("Message received: " + dis.readUTF()); //read the sercer message
                    break;
                }
            }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

class ResponseListener extends Thread {
    BufferedReader reader;

    ResponseListener(Socket sock) {
        try {
            InputStream input = sock.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                System.out.println(reader.readLine());
            } catch (IOException e) {
                System.out.println("There has been an error with the Response Listener");
                break;
            }
        }
    }
}


class ServerCaller extends Thread {
    PrintWriter writer;
    String message;
    Scanner reader = new Scanner(System.in);
    BufferedReader nameCheck;

    ServerCaller(Socket sock) {
        try {
            OutputStream output = sock.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = sock.getInputStream();
            nameCheck = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting output stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        String okName = "";
        do {

            if (okName.equals("INVALID")) {
                System.out.println("Name has already been taken...");
            }

            System.out.print("Enter your name: ");
            String userName = reader.nextLine();
            writer.println(userName);
            try {
                okName = nameCheck.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (okName.equals("INVALID"));

        while (true) {
            message = reader.nextLine();
            writer.println(message);
            System.out.println("You: " + message);
        }

    }
}
