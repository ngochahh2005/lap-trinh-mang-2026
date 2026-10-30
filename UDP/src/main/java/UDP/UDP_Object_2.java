package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

class Customer implements Serializable {
    private static final long serialVersionUID = 20151107;
    private String id, code, name, dayOfBirth, userName;

    public Customer(String id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    void taoUsername() {
        String[] s = name.toLowerCase().split("\\s+");
        int n = s.length;
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < n-1; i++) {
            res.append(s[i].charAt(0));
        }
        res.append(s[n-1]);
        userName = res.toString().trim();
    }

    void chuanHoaTen() {
        String[] s = name.toLowerCase().split("\\s+");
        int n = s.length;
        StringBuilder res = new StringBuilder(s[n-1].toUpperCase() + ",");
        for (int i = 0; i < n-1; i++) {
            String x = s[i];
            String tmp = " " + Character.toUpperCase(x.charAt(0)) + x.substring(1);
            res.append(tmp);
        }
        name = res.toString();
    }

    void chuanHoaNgaySinh() {
        String[] s = dayOfBirth.split("-");
        dayOfBirth = s[1] + "/" + s[0] + "/" + s[2];
    }
}

public class UDP_Object_2 {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;bqlMiO3X";
        InetAddress server = InetAddress.getByName("36.50.135.242");
        int port = 2209;

        DatagramSocket socket = new DatagramSocket();

        byte[] sendData = req.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(
                sendData,
                sendData.length,
                server,
                port
        );
        socket.send(sendPacket);

        byte[] buffer = new byte[2048];
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket);

        byte[] receiveData = receivePacket.getData();
        int receiveLength = receivePacket.getLength();

        byte[] requestId = Arrays.copyOfRange(receiveData, 0, 8);
        byte[] objectBytes = Arrays.copyOfRange(receiveData, 8, receiveLength);

        ByteArrayInputStream bais = new ByteArrayInputStream(objectBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Customer customer = (Customer) ois.readObject();

        customer.taoUsername();
        customer.chuanHoaNgaySinh();
        customer.chuanHoaTen();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(customer);
        oos.flush();

        byte[] resultBytes = baos.toByteArray();
        byte[] resultData = new byte[8 + resultBytes.length];
        System.arraycopy(requestId, 0, resultData, 0,8);
        System.arraycopy(resultBytes, 0, resultData, 8, resultBytes.length);

        DatagramPacket resultPacket = new DatagramPacket(
                resultData,
                resultData.length,
                server,
                port
        );
        socket.send(resultPacket);

        socket.close();
    }
}
