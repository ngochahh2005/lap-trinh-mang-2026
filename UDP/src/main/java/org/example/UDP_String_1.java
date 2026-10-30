package org.example;

import java.net.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class UDP_String_1 {
    static void main() throws Exception {
        String msv = "B23DCCN264", qcode = "hirah2xu";
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        // gui du lieu
        String req = ";" + msv + ";" + qcode;

        byte[] sendData = req.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );

        socket.send(sendPacket);

        // nhan du lieu
        byte[] buffer = new byte[1024];

        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());

        // xu ly
        String[] a = data.split(";");

        Map<Character, Integer> mp = new LinkedHashMap<>();
        for (int i = 0; i < a[1].length(); i++) {
            char c = a[1].charAt(i);
            if (c == ' ') continue;
            mp.put(c, mp.getOrDefault(c, 0) + 1);
        }

        int maxCnt = 0;
        Character maxValue = 0;
        for (Map.Entry<Character, Integer> entry : mp.entrySet()) {
            if (entry.getValue() > maxCnt) {
                maxCnt = entry.getValue();
                maxValue = entry.getKey();
            }
        }

        String res = a[0] + ";" + maxValue + ":";
        for (int i = 0; i < a[1].length(); i++) {
            if (a[1].charAt(i) == maxValue) {
                res += (i+1) + ",";
            }
        }

        // tra ket qua ve
        byte[] resultData = res.getBytes();

        DatagramPacket resultPacket = new DatagramPacket(
                resultData,
                resultData.length,
                receivePacket.getAddress(),
                receivePacket.getPort()
        );
        socket.send(resultPacket);

        socket.close();
    }
}
