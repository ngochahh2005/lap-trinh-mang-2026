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
       Ví dụ: "B16DCCN999;ucpQ9zAh" 
    b. Nhận dữ liệu từ server gồm đúng 2 frame liên tiếp. Payload của mỗi frame là một phần của cùng 
       một chuỗi JSON đơn giản trên một dòng (không xuống dòng). Client phải nối 2 payload theo đúng 
       thứ tự để thu được chuỗi JSON hoàn chỉnh. 
    c. Trích xuất các trường "event", "user", "ok" và gửi lại lên server theo định dạng "event=;user=;ok=<0|1>" (true=1, false=0). 
    d. Đóng kết nối và kết thúc chương trình.
*/

public class TCP_NIO_Stream_2 {

    public static void main(String[] args) {

        String host = "36.50.135.242";
        int port = 2211;

        String studentCode = "B23DCCN264";
        String qCode = "AQBqBEwR";

        try {
            SocketChannel channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(host, port));

            String request = studentCode + ";" + qCode;
            writeFrame(channel, request);

            StringBuilder jsonBuilder = new StringBuilder();

            for (int i = 0; i < 2; i++) {
                String part = readFrame(channel);
                jsonBuilder.append(part);
            }

            String json = jsonBuilder.toString();

            System.out.println("JSON nhan duoc:");
            System.out.println(json);

            String event = extractString(json, "event");
            String user = extractString(json, "user");
            boolean ok = extractBoolean(json, "ok");

            String result =
                    "event=" + event +
                    ";user=" + user +
                    ";ok=" + (ok ? 1 : 0);

            System.out.println("Gui server:");
            System.out.println(result);

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

    static String extractString(
            String json,
            String key
    ) {

        String pattern = "\"" + key + "\"";

        int keyPos = json.indexOf(pattern);

        if (keyPos == -1) {
            return "";
        }

        int colonPos =
                json.indexOf(':', keyPos);

        int firstQuote =
                json.indexOf('"', colonPos + 1);

        int secondQuote =
                json.indexOf('"', firstQuote + 1);

        return json.substring(
                firstQuote + 1,
                secondQuote
        );
    }

    static boolean extractBoolean(
            String json,
            String key
    ) {

        String pattern = "\"" + key + "\"";

        int keyPos = json.indexOf(pattern);

        if (keyPos == -1) {
            return false;
        }

        int colonPos =
                json.indexOf(':', keyPos);

        String value =
                json.substring(colonPos + 1)
                        .trim();

        return value.startsWith("true");
    }
}