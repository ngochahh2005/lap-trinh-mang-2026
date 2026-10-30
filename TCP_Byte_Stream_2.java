
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2206 
    (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
    Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng các luồng byte 
    (InputStream/OutputStream) để trao đổi thông tin theo thứ tự:   
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". 
        Ví dụ: "B16DCCN999;2B3A6510"  
    b. Nhận dữ liệu từ server là một chuỗi các giá trị số nguyên được phân tách nhau bởi ký tự ",". 
        Ví dụ: 1,3,9,19,33,20  
    c. Tìm và gửi lên server giá trị lớn thứ hai cùng vị trí xuất hiện của nó trong chuỗi.
        Ví dụ: 20,5  
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_Byte_Stream_2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2206;

        String msv = "B23DCCN264";
        String qCode = "0VNY5AKF";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String req = msv + ";" + qCode;
            out.write(req.getBytes());
            out.flush();

            byte[] buffer = new byte[4096];
            int byteReads = in.read(buffer);
            String data = new String(buffer, 0, byteReads).trim();

            String[] parts = data.split(",");
            int[] a = new int[parts.length];
            for (int i = 0; i < parts.length; i++) {
                a[i] = Integer.parseInt(parts[i]);
            }

            // int[] a = {1, 3, 9, 19, 33, 20};

            int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, idx2 = -1, idx1 = -1;
            for (int i = 0; i < a.length; i++) {
                if (a[i] > max1) {
                    max2 = max1;
                    idx2 = idx1;
                    max1 = a[i];
                    idx1 = i;
                }
                if (a[i] < max1 && a[i] > max2) {
                    max2 = a[i];
                    idx2 = i;
                }
            }

            String res = String.valueOf(max2) + "," + String.valueOf(idx2);
            System.out.println(res);
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
