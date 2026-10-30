package org.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Set;
import java.util.TreeSet;

public class UDP_Data_Type_1 {
    static void main() throws Exception {
        String req = ";B23DCCN264;4kUxxjnH";
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2207;
        DatagramSocket socket = new DatagramSocket();

        // gui yeu cau
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
        System.out.println("Data nhận: " + data);

        // xu ly
        String[] str = data.split(";");
        int n = Integer.parseInt(str[1]);
        String[] s1 = str[2].split(",");
        Set<Integer> set = new TreeSet<>();

        for (String s : s1) {
            set.add(Integer.parseInt(s));
        }

        StringBuilder res = new StringBuilder(str[0] + ";");
        for (int i = 1; i <= n; i++) {
            if (set.contains(i)) continue;
            res.append(i + ",");
        }
        res.deleteCharAt(res.length() - 1);

        // gui ket qua
        byte[] result = res.toString().getBytes();
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
