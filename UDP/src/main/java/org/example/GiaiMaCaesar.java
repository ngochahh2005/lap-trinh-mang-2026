package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class GiaiMaCaesar {
    static char dich(char c, int s) {
        if (!Character.isLetter(c)) return c;
        char sc = (char)(c - s % 26);
        if (!Character.isLetter(sc) || Character.isUpperCase(c) != Character.isUpperCase(sc)) {
            sc += 26;
        }
        return sc;
    }

    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;J5SE2YXc";
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int port = 2207;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, server, port);
        socket.send(sendPacket);

        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String[] data = new String(receivePacket.getData(), 0, receivePacket.getLength()).split(";");
        System.out.println(data[0] + " - " + data[1] + " - " + data[2]);
        int s = Integer.parseInt(data[2]);
        StringBuilder res = new StringBuilder(data[0] + ";");
        String str = data[1];
        for (int i = 0; i < str.length(); i++) {
            res.append(dich(str.charAt(i), s));
        }

        byte[] resultData = res.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);
    }
}
