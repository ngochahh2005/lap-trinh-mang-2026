import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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
       Ví dụ: "B16DCCN999;GZLEN01" 
    b. Nhận dữ liệu từ server, sau khi giải nén là một chuỗi văn bản. 
    c. Thực hiện đảo ngược chuỗi nhận được, sau đó mã hóa chuỗi đã đảo ngược sang định dạng Base64. 
       Gửi kết quả lên server theo khuôn dạng: 
       | Ví dụ: Nhận "123" → Đảo ngược thành "321" → Base64 của "321" là "MzIx" → Gửi lên: "321|MzIx" 
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_GZIP_Stream_1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2210;
        
        String req = "B23DCCN264;eQep6DqV\n";

        try (Socket socket = new Socket(host, port)) {
            socket.setSoTimeout(5000);

            GZIPOutputStream gout = new GZIPOutputStream(socket.getOutputStream(), true);
            gout.write(req.getBytes(StandardCharsets.UTF_8));
            gout.flush();

            GZIPInputStream gin = new GZIPInputStream(socket.getInputStream());
            StringBuilder sb = new StringBuilder();
            int b;
            
            while ((b = gin.read()) != -1) {
                if (b == '\n') break;
                sb.append((char) b);
            }

            String data = sb.toString();
            
            System.out.println("Server gui: " + data);

            String reversed = new StringBuilder(data).reverse().toString();
            String encoded = Base64.getEncoder().encodeToString(reversed.getBytes(StandardCharsets.UTF_8));
            String res = reversed + "|" + encoded + "\n";
            
            System.out.println("Client gui: " + res);
            gout.write(res.getBytes(StandardCharsets.UTF_8));
            gout.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}