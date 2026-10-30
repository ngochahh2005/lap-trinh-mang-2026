package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/*
    Thông tin sản phẩm vì một lý do nào đó đã bị sửa đổi thành không đúng, cụ thể:  
    a) Tên sản phẩm bị đổi ngược từ đầu tiên và từ cuối cùng, 
    ví dụ: “lenovo thinkpad T520” bị chuyển thành “T520 thinkpad lenovo”  
    b) Số lượng sản phẩm cũng bị đảo ngược giá trị, ví dụ từ 9981 thành 1899    
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 
    (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s).
    Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng 
    (ObjectInputStream / ObjectOutputStream) để gửi/nhận và sửa các thông tin bị sai của sản phẩm. 
    Chi tiết dưới đây:  
    a) Đối tượng trao đổi là thể hiện của lớp Laptop được mô tả như sau
        • Tên đầy đủ của lớp: TCP.Laptop        
        • Các thuộc tính: id int, code String, name String, quantity int        
        • Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên        
        • Trường dữ liệu: private static final long serialVersionUID = 20150711L;   
    b) Tương tác với server theo kịch bản  
        1) Gửi đối tượng là chuỗi chứa mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". 
        Ví dụ: "B15DCCN999;5AD2B818"  
        2) Nhận một đối tượng là thể hiện của lớp Laptop từ server  
        3) Sửa các thông tin sai của sản phẩm về tên và số lượng.  
           Gửi đối tượng vừa được sửa sai lên server  
        4) Đóng socket và kết thúc chương trình.
*/

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

    public void fixedName() {
        String[] s = name.split("\\s+");
        String tmp = s[0];
        s[0] = s[s.length - 1];
        s[s.length - 1] = tmp;
        String res = "";
        for (int i = 0; i < s.length; i++) {
            if (i != 0) res += " ";
            res += s[i];
        }
        this.name = res;
    }

    public void fixedQuantity() {
        String res = new StringBuilder(String.valueOf(quantity)).reverse().toString();

        this.quantity = Integer.parseInt(res);
    }

    @Override
    public String toString() {
        return id + " " + code + " " + name + " " + quantity;
    }
}

public class TCP_Object_Stream_1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;

        String msv = "B23DCCN264";
        String qcode = "P0nQp4ia";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String req = msv + ";" + qcode;
            out.writeObject(req);
            out.flush();

            Laptop laptop = (Laptop)in.readObject();
            laptop.fixedName();
            laptop.fixedQuantity();
            System.out.println(laptop);

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
