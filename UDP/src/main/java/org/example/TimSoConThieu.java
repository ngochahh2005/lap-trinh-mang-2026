package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;
import java.util.TreeSet;

public class TimSoConThieu {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;wAKCZwjj";
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int port = 2207;

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
        int n = Integer.parseInt(data[1]);
        String[] s = data[2].split(",");
        Set<Integer> set = new TreeSet<>();
        for (String x : s) {
            set.add(Integer.parseInt(x));
        }

        StringBuilder res = new StringBuilder(data[0] + ";");
        for (int i = 1; i <= n; i++) {
            if (!set.contains(i)) {
                res.append(i + ",");
            }
        }
        res.deleteCharAt(res.length() - 1);

        byte[] resultData = res.toString().getBytes();
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);

        socket.close();
    }
}
