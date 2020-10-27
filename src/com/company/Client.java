package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in); //set up user input
        Socket sock = new Socket("localhost", 42069);
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        System.out.println("Connection established");
        System.out.print("\nEnter message for server: ");
        String message = reader.nextLine();
        dos.writeUTF(message);//prepare message
        dos.flush(); //send message

        //Part 2 code
        DataInputStream dis = new DataInputStream(sock.getInputStream()); //make an input stream

        System.out.println("Now just awaiting server message...");
        while(true){
            if(dis.available() > 0){ //checking if there are readable bytes available
                System.out.println("Message received: " + dis.readUTF()); //read the sercer message
                break;
            }
        }

        //------ close everything plz
        dos.close();
        dis.close();
        sock.close(); //close connection
    }
}
