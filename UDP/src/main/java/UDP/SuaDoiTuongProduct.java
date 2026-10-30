package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

class Product implements Serializable {
    private String id, code, name;
    private int quantity;
    private static final long serialVersionUID = 20161107;

    public Product(String id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    void chuanHoaTen() {
        String[] s = name.split("\\s+");
        StringBuilder res = new StringBuilder();
        int n = s.length;
        String tmp = s[0];
        s[0] = s[n-1];
        s[n-1] = tmp;
        for (String x : s) {
            res.append(x + " ");
        }
        name = res.toString().trim();
    }

    void reverseQuantity() {
        StringBuilder s = new StringBuilder(String.valueOf(quantity));
        quantity = Integer.parseInt(s.reverse().toString());
    }
}

public class SuaDoiTuongProduct {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;kZqFKEDL";
        InetAddress server = InetAddress.getByName("127.0.0.1");
        int port = 2209;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, server, port);
        socket.send(sendPacket);

        byte[] buffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        byte[] requestId = Arrays.copyOfRange(receivePacket.getData(), 0, 8);
        byte[] data = Arrays.copyOfRange(receivePacket.getData(), 8, receivePacket.getLength());

        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Product product = (Product) ois.readObject();

        product.chuanHoaTen();
        product.reverseQuantity();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(product);
        oos.flush();

        byte[] resultBytes = baos.toByteArray();
        byte[] resultData = new byte[resultBytes.length + 8];
        System.arraycopy(requestId, 0, resultData, 0, requestId.length);
        System.arraycopy(resultBytes, 0, resultData, 8, resultBytes.length);
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);

        socket.close();
    }
}
