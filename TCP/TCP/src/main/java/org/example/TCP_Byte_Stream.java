package org.example;

import java.io.OutputStream;
import java.net.Socket;

public class TCP_Byte_Stream {
    static void main() {
        String host = "36.50.135.242";
        int port = 2206;

        String req = "B23DCCN264;tNfS0qgP";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
