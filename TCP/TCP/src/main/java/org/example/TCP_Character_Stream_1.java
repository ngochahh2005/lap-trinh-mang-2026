package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TCP_Character_Stream_1 {
    static void main() {
        String host = "36.50.135.242";
        int port = 2208;

        String req = "B23DCCN264;mysv5ZHw";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.write(req);
            out.newLine();
            out.flush();

            String data = in.readLine();
            String[] str = data.split(", ");
            StringBuilder res = new StringBuilder();
            for (String s : str) {
                if (s.contains(".edu")) {
                    if (!res.isEmpty())  res.append(", ");
                    res.append(s);
                }
            }

            String result = res.toString();

            out.write(result);
            out.newLine();
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
