package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class SapXepKyTuTheoTanSuat {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;AB12CD34";
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int port = 808;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, server, port);
        socket.send(sendPacket);

        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String[] data = new String(receivePacket.getData(), 0, receivePacket.getLength()).split(";");
        Map<Character, Integer> mp = new TreeMap<>();
        String s = data[1];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            mp.put(c, mp.getOrDefault(c, 0) + 1);
        }

        List<Map.Entry<Character, Integer>> list = new ArrayList<>(mp.entrySet());
        list.sort((a, b) -> {
            if (a.getValue().equals(b.getValue())) return a.getKey().compareTo(b.getKey());
            return b.getValue() - a.getValue();
        });

        StringBuilder res = new StringBuilder(data[0] + ";");
        for (Map.Entry<Character, Integer> e : list) {
            res.append(e.getKey() + ":" + e.getValue() + ",");
        }
        res.deleteCharAt(res.length() - 1);

        byte[] resultData = res.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);
    }
}
