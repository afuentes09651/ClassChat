package com.company;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{


    public static void main(String[] args) throws IOException{

       ServerSocket ss = new ServerSocket(42069); //set up server socket
        System.out.println("Awaiting connection...");
       Socket s = ss.accept();  //listen and accept connection
        System.out.println("Connection established");
       DataInputStream dis = new DataInputStream(s.getInputStream()); //setup input stream
        String output = dis.readUTF(); //retreive message
        System.out.println("Message received: " + output); //display message
        ss.close();  //close connection

    }
}
