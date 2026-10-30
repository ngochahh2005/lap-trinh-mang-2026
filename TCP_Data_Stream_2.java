
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/*
    Mật mã caesar, còn gọi là mật mã dịch chuyển, để giải mã thì mỗi ký tự nhận được sẽ 
    được thay thế bằng một ký tự cách nó một đoạn s. 
    Ví dụ: với s = 3 thì ký tự “A” sẽ được thay thế bằng ký tự “D”.  
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2207 
    (hỗ trợ thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 

    Yêu cầu là xây dựng chương trình client tương tác với server trên, 
    sử dụng các luồng byte (DataInputStream/DataOutputStream) để trao đổi thông tin theo thứ tự: 
    a. Gửi một chuỗi gồm mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". 
        Ví dụ: "B15DCCN999;D68C93F7"  
    b. Nhận lần lượt chuỗi đã bị mã hóa caesar và giá trị dịch chuyển s nguyên  
    c. Thực hiện giải mã ra thông điệp ban đầu và gửi lên Server  
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_Data_Stream_2 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 2207;

        String msv = "B23DCCN264";
        String qcode = "c2CBSval";

        try {
            Socket socket = new Socket(host, port);
            socket.setSoTimeout(5000);

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String req = msv + ";" + qcode;
            out.writeUTF(req);
            out.flush();

            String str = in.readUTF();
            int s = in.readInt();

            String res = "";
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (!Character.isLetter(c)) {
                    res += c;
                    continue;
                }
                c = (char) (c - s % 26);
                if (!Character.isLetter(c) || Character.isUpperCase(str.charAt(i)) != Character.isUpperCase(c)) c = (char) (c + 26);
                res += c;
            }

            out.writeUTF(res);
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
