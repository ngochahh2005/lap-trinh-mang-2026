import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 
 * (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
 * Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte 
 * (BufferedWriter/BufferedReader) theo kịch bản sau: 
 *      a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". 
 *         Ví dụ: "B15DCCN999;EC4F899B" 
 *      b. Nhận một chuỗi ngẫu nhiên là danh sách các một số tên miền từ server 
 *         Ví dụ: giHgWHwkLf0Rd0.io, I7jpjuRw13D.io, wXf6GP3KP.vn, MdpIzhxDVtTFTF.edu, TUHuMfn25chmw.vn, HHjE9.com, 4hJld2m2yiweto.vn, y2L4SQwH.vn, s2aUrZGdzS.com, 4hXfJe9giAA.edu 
 *      c. Tìm kiếm các tên miền .edu và gửi lên server 
 *         Ví dụ: MdpIzhxDVtTFTF.edu, 4hXfJe9giAA.edu 
 *      d. Đóng kết nối và kết thúc chương trình.
 */
public class TCP_Character_Stream_1 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;

        String studentCode = "B23DCCN264";
        String qCode = "mysv5ZHw";

        try {
            Socket socket = new Socket(host, port);

            socket.setSoTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = studentCode + ";" + qCode;

            out.write(request);
            out.newLine();
            out.flush();

            String data = in.readLine();
            System.out.println("Server gui: " + data);

            String[] domains = data.split(",");

            StringBuilder res = new StringBuilder();

            for (String domain : domains) {
                domain = domain.trim();

                if (domain.endsWith(".edu")) {
                    if (res.length() > 0) {
                        res.append(", ");
                    }
                    res.append(domain);
                }
            }

            System.out.println("Client gui lai Server: " + res.toString());

            out.write(res.toString());
            out.newLine();
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}