
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Vector;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/*
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2210 
    (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
    Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng 
    GZIPInputStream/GZIPOutputStream để trao đổi thông tin 
    (mỗi thông điệp là một dòng text UTF-8 kết thúc bằng ‘\n’ và toàn bộ dữ liệu truyền/nhận đều được nén GZIP), 
    theo thứ tự sau:    
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode".  
       Ví dụ: "B16DCCN999;GZCRC_LEN03"    
    b. Nhận dữ liệu từ server, sau khi giải nén là một chuỗi văn bản.    
    c. Sắp xếp các ký tự trong chuỗi nhận được theo thứ tự từ điển (tăng dần theo mã ASCII). 
       Sau đó gửi chuỗi kết quả đã sắp xếp lên server.  
       Ví dụ: Nhận về "dbca1" thì gửi lên server "1abcd"    
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_GZIP_Stream_2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2210;

        String req = "B23DCCN264;aeut3smo\n";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream(), true);
            out.write(req.getBytes(StandardCharsets.UTF_8));
            out.flush();

            GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
            Vector<Character> a = new Vector<>(); 
            int c;

            while ((c = in.read()) != -1) {
                if (c == '\n') break;
                a.add((char)c);
            }

            Collections.sort(a);

            StringBuilder sb = new StringBuilder();
            for (Character ch : a) {
                sb.append(ch);
            }

            String s = sb.toString() + "\n";

            out.write(s.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
