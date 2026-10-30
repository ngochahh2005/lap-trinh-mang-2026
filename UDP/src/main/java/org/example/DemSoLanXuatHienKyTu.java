package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class DemSoLanXuatHienKyTu {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;vSgxl3HQ";
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );
        socket.send(sendPacket);

        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String[] data = new String(receivePacket.getData(), 0, receivePacket.getLength()).split(";");
        String s = data[1];
        Map<Character, Integer> mp = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c= s.charAt(i);
            mp.put(c, mp.getOrDefault(c, 0) + 1);
        }

        String res = data[0] + ";";
        for (Map.Entry<Character, Integer> e : mp.entrySet()) {
            res += String.valueOf(e.getValue()) + e.getKey();
            System.out.println(e.getKey() + " " + e.getValue());
        }

        byte[] resultData = res.getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);

        socket.close();
    }
}
