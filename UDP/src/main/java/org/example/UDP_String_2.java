package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_String_2 {
    static void main() throws Exception {
        String req = ";B23DCCN264;z3p8ZDxy";
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2208;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData =  req.getBytes();
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
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());

        String[] a = data.split(";");
        String[] str = a[1].trim().toLowerCase().split("\\s+");
        String res = a[0] + ";";
        for (String s : str) {
            res +=  Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ";
        }
        res.trim();
        System.out.println(res);

        byte[] result = res.getBytes();
        DatagramPacket resultPacket = new DatagramPacket(
                result,
                result.length,
                receivePacket.getAddress(),
                receivePacket.getPort()
        );
        socket.send(resultPacket);

        socket.close();
    }
}
