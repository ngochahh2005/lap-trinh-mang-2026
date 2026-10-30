package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

class Student implements Serializable {
    private static final long serialVersionUID = 20171107;
    private String id;
    private String code;
    private String name;
    private String email;

    public Student(String id, String code, String name, String email) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.email = email;
    }

    public Student(String code) {
        this.code = code;
    }

    void chuanHoaTen() {
        String[] s = name.toLowerCase().trim().split("\\s+");
        StringBuilder res = new StringBuilder();
        for (String x : s) {
            String tmp = Character.toUpperCase(x.charAt(0)) + x.substring(1) + " ";
            res.append(tmp);
        }
        name = res.toString().trim();
    }

    void taoEmail() {
        String[] s = name.toLowerCase().split("\\s+");
        int n = s.length;
        StringBuilder res = new StringBuilder(s[n-1]);
        for (int i = 0; i < n-1; i++) {
            res.append(s[i].charAt(0));
        }
        res.append("@ptit.edu.vn");
        email = res.toString().trim();
    }
}

public class UDP_Object_1 {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;nO61Sa7b";
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
        Student student = (Student) ois.readObject();

        student.chuanHoaTen();
        student.taoEmail();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(student);
        oos.flush();

        byte[] resultBytes = baos.toByteArray();
        byte[] resultData = new byte[8 + resultBytes.length];
        System.arraycopy(requestId, 0, resultData, 0, 8);
        System.arraycopy(resultBytes, 0, resultData, 8, resultBytes.length);

        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);

        socket.close();
    }
}
