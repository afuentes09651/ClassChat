package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{


    public static void main(String[] args) throws IOException{
        Scanner reader = new Scanner(System.in);

       ServerSocket ss = new ServerSocket(42069); //set up server socket
        System.out.println("Awaiting connection...");
       Socket s = ss.accept();  //listen and accept connection
        System.out.println("Connection established");
       DataInputStream dis = new DataInputStream(s.getInputStream()); //setup input stream
        String output = dis.readUTF(); //retreive message
        System.out.println("Message received: " + output); //display message
        System.out.print("\nHow would you like to respond?  ");
        String response = reader.nextLine();


        //part 2 code
        DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //establish an output stream
        dos.writeUTF(response); //prepare message
        dos.flush(); //send message
        System.out.println("Response sent!");

        ss.close();  //close connection

    }
}
