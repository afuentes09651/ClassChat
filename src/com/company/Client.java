package com.company;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket sock = new Socket("localhost", 42069);
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        dos.writeUTF("wubba");
        dos.flush();
        sock.close();
    }
}
