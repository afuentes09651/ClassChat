package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server{


    public static void main(String[] args) throws IOException{
        ServerSocket ss = new ServerSocket(42069); //set up server socket
        Socket s;
        int clientCount = 0;

        while(true){ //keep the server alive
            System.out.println("Awaiting connection...");
            s = ss.accept(); //listen for connection
            System.out.println("Connection established");

            //Set up your IO streams
            DataInputStream dis = new DataInputStream(s.getInputStream()); //setup input stream
            DataOutputStream dos = new DataOutputStream(s.getOutputStream()); //establish an output stream

            System.out.println("setting up thread" + clientCount + "...");
            clientCount++;

            Thread client = new ClientHandler(s,dis,dos, clientCount);
            client.start();

        }
        //ss.close();  //close connection
    }

/*



 */

}

class ClientHandler extends Thread{
    DataInputStream dis;
    DataOutputStream dos;
    Socket s;
    Scanner reader = new Scanner(System.in);
    int cc;

    ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos, int cc){
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.cc = cc;
    }

    public void run() {

        if(cc % 2 == 0){
            try {
                System.out.println(cc + " is sleeping");
                sleep(50000);
                System.out.println(cc + " just woke up");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
        try {
            String output = dis.readUTF(); //retreive message
            System.out.println("Message received: " + output); //display message
            System.out.print("\nHow would you like to respond?  ");
            String response =   "hello there " + cc;        //reader.nextLine();


            //part 2 code
            dos.writeUTF(response); //prepare message
            dos.flush(); //send message
            System.out.println("Response sent!");

            //----- close everything plz
            dos.close();
            dis.close();
        }
        catch(IOException e){
            System.out.println("Something went wrong with the IO Streams");
            e.printStackTrace();
        }
    }
}
