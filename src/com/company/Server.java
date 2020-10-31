package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server{

    static ArrayList<Socket> connections = new ArrayList<Socket>(); //used to broadcast to all sockets


    public void main(String [] args) throws IOException{
        ServerSocket ss = new ServerSocket(42069); //set up server socket
        Socket s;
        int clientCount = 0;


        while(true){ //keep the server alive
            System.out.println("Awaiting connection...");
            s = ss.accept(); //listen for connection
            System.out.println("Connection established");
            connections.add(s); //add socket to the broadcast list

            //Set up your IO streams
            DataInputStream dis = new DataInputStream(s.getInputStream()); //setup input stream
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //establish an output stream

            System.out.println("setting up thread " + clientCount + "...");
            clientCount++;

            Thread client = new ClientHandler(s, server);
            client.start();

        }
        //connections.remove(s);
        //ss.close();  //close connection
    }


    //The Client Handler will be taking care of things for now on for each client.
//It will take the info from the socket and give it to the server to broadcast
    class ClientHandler extends Thread{

        Socket sock;
        Server server;
        String name = "unkown";


        ClientHandler(Socket sock, Server server){
            this.sock = sock;
            this.server = server;

        }

        //we will start a thread to listen for the user request and message
        public void run() { //Commence the client thread
            try{




            } catch (Exception e) {
                System.out.println("There was an issue with the Client Handler for " + name);
            }
        }
    }
}



