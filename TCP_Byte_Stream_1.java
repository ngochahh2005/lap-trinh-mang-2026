
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

/*
    Một chương trình server hỗ trợ kết nối qua giao thức TCP tại cổng 2206 
    (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
    Yêu cầu xây dựng chương trình client thực hiện kết nối tới server trên sử dụng luồng byte dữ liệu 
    (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:   
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". 
        Ví dụ: "B16DCCN999;FF49DC02"  
    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ","  
        Ex: 1,3,9,19,33,20  
    c. Thực hiện tìm giá trị khoảng cách nhỏ nhất của các phần tử nằm trong chuỗi và hai giá trị 
       lớn nhất tạo nên khoảng cách đó. Gửi lên server chuỗi gồm "khoảng cách nhỏ nhất, số thứ nhất, 
       số thứ hai". 
       Ex: 1,19,20  
    d. Đóng kết nối và kết thúc
*/

public class TCP_Byte_Stream_1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2206;

        String studentCode = "B23DCCN264";
        String qCode = "tNfS0qgP";
        
        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String req = studentCode + ";" + qCode;
            out.write(req.getBytes());
            out.flush();
            
            byte[] buffer = new byte[4096];
            int bytesRead = in.read(buffer);
            String data = new String(buffer, 0, bytesRead).trim();
            System.out.println("Server gui: " + data);

            String[] parts = data.split(",");
            int[] a = new int[parts.length];

            for (int i = 0; i < parts.length; i++) {
                a[i] = Integer.parseInt(parts[i].trim());
            }

            Arrays.sort(a);

            int minDistance = Integer.MAX_VALUE;

            int n1 = 0, n2 = 0;

            for (int i = 0; i < a.length - 1; i++) {
                int distance = a[i+1] - a[i];
                if (distance <= minDistance) {
                    minDistance = a[i+1] - a[i];
                    n1 = a[i];
                    n2 = a[i+1];
                }
            }

            String res = minDistance + "," + n1 + "," + n2;
            System.out.println("Client gui: " + res);
            out.write(res.getBytes());
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
