package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class Laptop implements Serializable {
    private int id;
    private String code;
    private String name;
    private int quantity;
    private static final long serialVersionUID = 20150711L;

    public Laptop(int id, String code, String name, int quantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public void chuanHoaTen() {
        String[] str = name.split(" ");
        String tmp = str[0];
        str[0] = str[str.length - 1];
        str[str.length - 1] = tmp;
        String res = "";
        for (String s : str) {
            if (!res.isEmpty()) res += " ";
            res += s;
        }
        name = res;
    }

    public void chuanHoaSL() {
        String sl = String.valueOf(quantity);
        quantity = Integer.parseInt(new StringBuilder(sl).reverse().toString());
    }
}

public class TCP_Object_Stream_1 {
    static void main() {
        String host = "36.50.135.242";
        int port = 2209;

        String req = "B23DCCN264;P0nQp4ia";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(req);
            out.flush();

            Laptop laptop = (Laptop) in.readObject();
            laptop.chuanHoaSL();
            laptop.chuanHoaTen();

            out.writeObject(laptop);
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}