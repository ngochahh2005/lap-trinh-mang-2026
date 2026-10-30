package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

class Employee implements Serializable {
    private static final long serialVersionUID = 20261107L;
    private String id, name, hireDate;
    private double salary;

    public Employee(String id, String name, String hireDate, double salary) {
        this.id = id;
        this.name = name;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    void chuanHoaTen() {
        String[] str = name.toLowerCase().split("\\s+");
        StringBuilder res = new StringBuilder();
        for (String s : str) {
            res.append(Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ");
        }
        name = res.toString().trim();
    }

    void tinhSalary() {
        String[] str = hireDate.split("-");
        hireDate = str[2] + "/" + str[1] + "/" + str[0];
        double x = 0;
        for (int i = 0; i < str[0].length(); i++) {
            x += Double.parseDouble(String.valueOf(str[0].charAt(i)));
        }
        salary = salary * (1 + x/100);
    }
}

public class QuanLyThongTinEmployee {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;ySsumsIE";
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

        Employee employee = (Employee) ois.readObject();
        employee.chuanHoaTen();
        employee.tinhSalary();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(employee);
        oos.flush();

        byte[] result = baos.toByteArray();
        byte[] resultData = new byte[8 + result.length];
        System.arraycopy(requestId, 0, resultData, 0, 8);
        System.arraycopy(result, 0, resultData, 8, result.length);
        DatagramPacket resultPacket = new DatagramPacket(resultData, resultData.length, server, port);
        socket.send(resultPacket);

        socket.close();
    }
}
