package UDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

class Book implements Serializable {
    private String id, title, author, isbn, publishDate;
    private static final long serialVersionUID = 20251107L;

    public Book(String id, String title, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }

    void chuanHoaTitle() {
        title = chuanHoaTen(title);
    }

    String chuanHoaTen(String title) {
        String[] str = title.toLowerCase().split("\\s+");
        StringBuilder res = new StringBuilder();
        for (String s : str) {
            res.append(Character.toUpperCase(s.charAt(0)) + s.substring(1) + " ");
        }
        return res.toString().trim();
    }

    void chuanHoaAuthor() {
        author = chuanHoaTen(author);
        String[] str = author.split("\\s+");
        str[0] = str[0].toUpperCase();
        String res = str[0] + ",";
        for (int i = 1; i < str.length; i++) {
            res += " " + str[i];
        }
        author = res.trim();
    }

    void chuanISBN() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < isbn.length(); i++) {
            if (i == 3 || i == 4 || i == 6 || i == 12) res.append("-");
            res.append(isbn.charAt(i));
        }
        isbn = res.toString();
    }

    void chuanHoaNgay() {
        String[] str = publishDate.split("-");
        publishDate = str[1] + "/" + str[0];
    }
}

public class ChuanHoaThongTinBook {
    static void main(String[] args) throws Exception {
        String req = ";B23DCCN264;LFACr5Bi";
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

        Book book = (Book) ois.readObject();
        book.chuanHoaTitle();
        book.chuanHoaNgay();
        book.chuanHoaAuthor();
        book.chuanISBN();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(book);
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
