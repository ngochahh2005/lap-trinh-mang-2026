package TCP;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

/*
    Thông tin khách hàng cần thay đổi định dạng lại cho phù hợp với khu vực, cụ thể:  
    a. Tên khách hàng cần được chuẩn hóa theo định dạng mới. 
       Ví dụ: nguyen van hai duong -> DUONG, Nguyen Van Hai  
    b. Ngày sinh của khách hàng hiện đang ở dạng mm-dd-yyyy, cần được chuyển thành định dạng dd/mm/yyyy. 
       Ví dụ: 10-11-2012 -> 11/10/2012  
    c. Tài khoản khách hàng là các chữ cái in thường được sinh tự động từ họ tên khách hàng. 
       Ví dụ: nguyen van hai duong -> nvhduong    
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2209 
    (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
    
    Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng đối tượng 
    (ObjectInputStream / ObjectOutputStream) thực hiện gửi/nhận đối tượng khách hàng và chuẩn hóa. Cụ thể:  
    a. Đối tượng trao đổi là thể hiện của lớp Customer được mô tả như sau        
        • Tên đầy đủ của lớp: TCP.Customer        
        • Các thuộc tính: id int, code String, name String, dayOfBirth String, userName String        
        • Hàm khởi tạo đầy đủ các thuộc tính được liệt kê ở trên        
        • Trường dữ liệu: private static final long serialVersionUID = 20170711L;   
    b. Tương tác với server theo kịch bản dưới đây:   
        1) Gửi đối tượng là một chuỗi gồm mã sinh viên và mã câu hỏi ở định dạng "studentCode;qCode". 
           Ví dụ: "B15DCCN999;F2DA54F3"   
        2) Nhận một đối tượng là thể hiện của lớp Customer từ server với các thông tin đã được thiết lập   
        3) Thay đổi định dạng theo các yêu cầu ở trên và gán vào các thuộc tính tương ứng.  
           Gửi đối tượng đã được sửa đổi lên server   
        4) Đóng socket và kết thúc chương trình.
*/

class Customer implements Serializable {
    private int id;
    private String code;
    private String name;
    private String dayOfBirth;
    private String userName;
    private static final long serialVersionUID = 20170711L; 

    public Customer(int id, String code, String name, String dayOfBirth, String userName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userName = userName;
    }

    public void chuanHoaTen() {
        String[] s = name.split("\\s+");
        int n = s.length;
        String res = s[n-1].toUpperCase() + ",";
        for (int i = 0; i < n-1; i++) {
            String tmp = s[i].toLowerCase();
            res += " " + tmp.substring(0, 1).toUpperCase() + tmp.substring(1);
        }
        this.name = res;
    }

    public void chuanHoaNgaySinh() {
        String[] s = dayOfBirth.split("-");
        this.dayOfBirth = s[1] + "/" + s[0] + "/" + s[2];
    }

    public void sinhTaiKhoan() {
        String[] s = name.split("\\s+");
        int n = s.length;
        String res = "";
        for (int i = 0; i < n-1; i++) {
            res += s[i].substring(0, 1).toLowerCase();
        }
        res += s[n-1].toLowerCase();
        this.userName = res;
    }
}

public class TCP_Object_Stream_2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2209;

        String msv = "B23DCCN264";
        String qcode = "3DK6wX0G";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String req = msv + ";" + qcode;
            out.writeObject(req);
            out.flush();

            Customer customer = (Customer)in.readObject();
            customer.sinhTaiKhoan();
            customer.chuanHoaNgaySinh();
            customer.chuanHoaTen();

            out.writeObject(customer);
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
