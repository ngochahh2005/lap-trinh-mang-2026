package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TongHaiSoNhiPhan {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;lIQVug9S";
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, server, port);
        socket.send(sendPacket);

        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        String[] data = new String(receivePacket.getData(), 0, receivePacket.getLength()).split("[;,]");
        int n1 = Integer.parseInt(data[1], 2);
        int n2 = Integer.parseInt(data[2], 2);
        int res = n1 + n2;

        byte[] resultData = String.valueOf(data[0] + ";" + res).getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);

        socket.close();
    }
}
