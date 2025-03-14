package com.company;

import java.io.*;
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

    //usernames are the key and it like consolidates everything
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


            System.out.println("setting up thread " + clientCount + "...");
            clientCount++;

            Thread client = new ClientHandler(s, server);
            client.start();

        }
        //connections.remove(s);
        //ss.close();  //close connection
    }

    //
    void dm(String message, String to, String from) {
        if (clients.containsKey(to)) {
            from = "DM From " + from;
            clients.get(to).sendMessage(message, from);
        } else {
            clients.get(from).sendMessage("ERROR - Invalid recipient", "System");
        }
    }

    void sendHelper(String message, String from) {
        String[] strings;

        if (message.indexOf(':') != -1) { //if we have a dm
            strings = message.split(":", 2);
            strings[0] = strings[0].toLowerCase(); //to make things simpler
            if (!strings[0].equals("all")) {
                dm(strings[1], strings[0], from);
            } else {
                broadcast(message, from);
            }
        } else {
            broadcast(message, from);
        }

    }

    void broadcast(String message, String from) {
        for (Map.Entry<String, ClientHandler> user : server.clients.entrySet()) {
            if (!user.getKey().equals(from)) {
                user.getValue().sendMessage(message, from);
            }
        }
    }

}


//The Client Handler will be taking care of things for now on for each client.
//It will take the info from the socket and give it to the server to broadcast
class ClientHandler extends Thread {

    Socket sock;
    Server server;
    String name = "unknown";
    BufferedReader reader;
    PrintWriter writer;
    String message;


    ClientHandler(Socket sock, Server server) throws IOException {
        this.sock = sock;
        this.server = server;

        InputStream input = sock.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));

        OutputStream output = sock.getOutputStream();
        writer = new PrintWriter(output, true);

    }

    //we will start a thread to listen for the user request and message
    public void run() { //Commence the client thread
            try {
                boolean validName = false;

                do {
                    name = reader.readLine(); //get the username
                    System.out.println("User selected username " + name);
                    if (server.clients.containsKey(name)) {
                        writer.println("INVALID");
                    } else {
                        validName = true;
                        writer.println("Accepted");
                    }
                }
                while (!validName);


                //i added to lowercase to make things easier
                server.clients.put(name.toLowerCase(), this); //add connection

                listUsers();

                writer.println("Rules are simple:" +
                        "\nIn order to DM, you must specify user like: NAME: MESSAGE" +
                        "\nOtherwise, please type: ALL: MESSAGE to broadcast message to users");

                String newUser = name + " has joined the chat!";
                System.out.println("THIS name is " + name);
                server.broadcast(newUser, name);

                while (!sock.isClosed()) {
                    message = reader.readLine();
                    server.sendHelper(message, name);
                }



            } catch (Exception e) {
                System.out.println("There was an issue with the Client Handler for " + name);
                server.clients.remove(name);
                System.out.println(name + " has left the server");
                String quit = name + " has left the server";
                server.broadcast(quit, name);
                listUsers();
            }
    }


    //convenience method to easily print the users
    void listUsers() {
        String result = "Available users: ";

        for (Map.Entry<String, ClientHandler> entry : server.clients.entrySet()) {
            result += "\n" + entry.getKey();
        }
        writer.println(result);
    }


    void sendMessage(String message, String from) {
        writer.println(from + ": " + message);
    }
}


