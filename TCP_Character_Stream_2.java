
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2208 
    (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
    Yêu cầu là xây dựng một chương trình client tương tác với server sử dụng các luồng byte 
    (BufferedWriter/BufferedReader) theo kịch bản sau:   
    a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi với định dạng "studentCode;qCode". 
        Ví dụ: "B15DCCN999;BAA62945" 
    b. Nhận một chuỗi ngẫu nhiên từ server  
        Ví dụ: dgUOo ch2k22ldsOo  
    c. Liệt kê các ký tự (là chữ hoặc số) xuất hiện nhiều hơn một lần trong chuỗi và số lần xuất hiện của chúng và gửi lên server  
        Ví dụ: d:2,O:2,o:2,2:3,  
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_Character_Stream_2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2208;

        String msv = "B23DCCN264";
        String qCode = "OIra1npA";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String req = msv + ";" + qCode;
            out.write(req);
            out.newLine();
            out.flush();

            String data = in.readLine();
            System.out.println(data);
            Map<Character, Integer> mp = new HashMap<>();

            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                mp.put(c, mp.getOrDefault(c, 0) + 1);
            }

            String res = "";
            List<Character> ch = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                if (!Character.isLetterOrDigit(c)) continue;
                if (mp.get(c) > 1 && !ch.contains(c)) {
                    ch.add(c);
                    res += c + ":" + mp.get(c) + ",";
                }
            }

            System.out.println(res);
            out.write(res);
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
