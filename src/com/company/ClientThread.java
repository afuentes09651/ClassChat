package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientThread{

    public static void main(String [] args){
        for(int i = 0; i < 10; i++){

            Thread t = new ThreadTest();
            t.start();

        }
    }
}

class ThreadTest extends Thread{

    ThreadTest(){}

    public void run(){
        Scanner reader = new Scanner(System.in); //set up user input
        try (Socket sock = new Socket("localhost", 42069)) {
            DataOutputStream dos = null;
            try {
                dos = new DataOutputStream(sock.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Connection established");
            System.out.print("\nEnter message for server: ");
            String message = reader.nextLine();
            try {
                dos.writeUTF(message);//prepare message
                dos.flush(); //send message
            } catch (IOException e) {
                e.printStackTrace();
            }


            //Part 2 code
            DataInputStream dis = null; //make an input stream
            try {
                dis = new DataInputStream(sock.getInputStream());
                System.out.println("Now just awaiting server message...");
                while (true) {
                    if (dis.available() > 0) { //checking if there are readable bytes available
                        System.out.println("Message received: " + dis.readUTF()); //read the sercer message
                        break;
                    }
                }
                //------ close everything plz
                dos.close();
                dis.close();
                sock.close(); //close connection
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
