package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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

class ResponseListener extends Thread{

    Socket s;
    DataInputStream dis;

    ResponseListener(Socket s, DataInputStream dis){
        this.s = s;
        this.dis = dis;
    }

    public void run() {
        try {
            System.out.println("\nReceived message: " + dis.readUTF());


        } catch (IOException e) {
            System.out.println("There was an error in the Response Listener");
        }
    }
}
