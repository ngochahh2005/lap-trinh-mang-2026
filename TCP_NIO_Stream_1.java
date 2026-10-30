import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/*
    Một chương trình server cho phép kết nối qua giao thức TCP tại cổng 2211 
    (thời gian giao tiếp tối đa cho mỗi yêu cầu là 5s). 
    Yêu cầu là xây dựng một chương trình client tương tác tới server ở trên sử dụng SocketChannel 
    và ByteBuffer để trao đổi thông tin theo giao thức frame: 4 byte độ dài (int32) + payload (UTF-8). 
    Lưu ý: server & client đều phải đọc đủ dữ liệu bằng vòng lặp (readFully) do server luôn chia nhỏ dữ liệu khi gửi. 
    Trình tự trao đổi như sau:  
    a. Gửi mã sinh viên và mã câu hỏi theo định dạng "studentCode;qCode". 
       Ví dụ: "B16DCCN999;fkdRJYuX"  
    b. Nhận dữ liệu từ server gồm đúng 3 frame liên tiếp. Payload của mỗi frame là một phần của cùng 
       một HTTP request, client phải nối 3 payload theo đúng thứ tự để thu được chuỗi HTTP request 
       hoàn chỉnh (các dòng phân tách bởi "\r\n" và kết thúc bằng "\r\n\r\n").  
    c. Từ chuỗi HTTP request hoàn chỉnh, trích xuất và gửi lại lên server theo định dạng "METHOD;PATH;HOST" 
       trong đó PATH luôn bao gồm query-string.  
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_NIO_Stream_1 {

    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2211;

        String studentCode = "B23DCCN264";
        String qCode = "HMrlWA61";

        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(host, port));

            String request = studentCode + ";" + qCode;

            writeFrame(channel, request);

            StringBuilder httpRequest = new StringBuilder();

            for (int i = 0; i < 3; i++) {
                String payload = readFrame(channel);
                httpRequest.append(payload);
            }

            String http = httpRequest.toString();

            System.out.println("HTTP request:");
            System.out.println(http);

            String[] lines = http.split("\r\n");

            String[] firstLine = lines[0].split(" ");

            String method = firstLine[0];
            String path = firstLine[1];

            String hostValue = "";

            for (String line : lines) {
                if (line.regionMatches(
                        true,
                        0,
                        "Host:",
                        0,
                        5
                )) {
                    hostValue = line.substring(5).trim();
                    break;
                }
            }

            String result =
                    method + ";" +
                    path + ";" +
                    hostValue;

            System.out.println("Gui server: " + result);

            writeFrame(channel, result);

            channel.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void writeFrame(
            SocketChannel channel,
            String message
    ) throws IOException {

        byte[] payload =
                message.getBytes(StandardCharsets.UTF_8);

        ByteBuffer buffer =
                ByteBuffer.allocate(4 + payload.length);

        buffer.putInt(payload.length);

        buffer.put(payload);

        buffer.flip();

        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
    }

    static String readFrame(
            SocketChannel channel
    ) throws IOException {

        ByteBuffer lengthBuffer =
                ByteBuffer.allocate(4);

        readFully(channel, lengthBuffer);

        lengthBuffer.flip();

        int length = lengthBuffer.getInt();

        ByteBuffer payloadBuffer =
                ByteBuffer.allocate(length);

        readFully(channel, payloadBuffer);

        payloadBuffer.flip();

        return StandardCharsets.UTF_8
                .decode(payloadBuffer)
                .toString();
    }

    static void readFully(
            SocketChannel channel,
            ByteBuffer buffer
    ) throws IOException {

        while (buffer.hasRemaining()) {

            int n = channel.read(buffer);

            if (n == -1) {
                throw new IOException(
                        "Server dong ket noi truoc khi doc du du lieu"
                );
            }
        }
    }
}