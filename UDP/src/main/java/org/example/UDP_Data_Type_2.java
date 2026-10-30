package org.example;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDP_Data_Type_2 {
    static void main() throws Exception {
        String req = ";B23DCCN264;toQkjJQR";
        InetAddress server = InetAddress.getByName("36.50.135.242");
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
        String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println(data);

        String[] str = data.split(";");
        String[] s = str[1].split(",");
        int ma = Integer.MIN_VALUE, mi = Integer.MAX_VALUE;
        for (int i = 0; i < s.length; i++) {
            int n = Integer.parseInt(s[i]);
            if (n > ma) ma = n;
            if (n < mi) mi = n;
        }

        String res = str[0] + ";" + ma + "," + mi;
        System.out.println(res);
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
