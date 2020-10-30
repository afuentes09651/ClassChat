package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Bubba extends Thread {

    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in); //set up user input
        Socket sock = new Socket("localhost", 42069);
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());

        //Part 2 code
        DataInputStream dis = new DataInputStream(sock.getInputStream()); //make an input stream
        Thread listener = new ResponseListener(dis);

        listener.start();
        System.out.println("Connection established");
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
