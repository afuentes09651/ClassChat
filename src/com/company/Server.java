package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//helper class to construct and call the server
class ServerExe {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.main();
    }
}

public class Server {

    Map<String, ClientHandler> clients = new HashMap<>();
    Server server = this; //idk how else to like

    Server() {
    }

    void main() throws IOException {
        ServerSocket ss = new ServerSocket(42069); //set up server socket
        Socket s;
        int clientCount = 0;


        while (true) { //keep the server alive
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

    String listUsers() {
        String result = "";

        for (Map.Entry<String, ClientHandler> entry : clients.entrySet()) {
            result += "\n" + entry.getKey();
        }


        return result;
    }
}


//The Client Handler will be taking care of things for now on for each client.
//It will take the info from the socket and give it to the server to broadcast
class ClientHandler extends Thread {

    Socket sock;
    Server server;
    String name = "unknown";
    DataInputStream dis;
    DataOutputStream dos;


    ClientHandler(Socket sock, Server server) {
        this.sock = sock;
        this.server = server;

    }

    //we will start a thread to listen for the user request and message
    public void run() { //Commence the client thread
            try {

                //set up the streams
                dis = (DataInputStream) sock.getInputStream();
                dos = (DataOutputStream) sock.getOutputStream();

                dos.writeUTF("Welcome to the chat server!");
                dos.writeUTF("\nEnter your username: ");


            } catch (Exception e) {
                System.out.println("There was an issue with the Client Handler for " + name);
            }
        }
    }



