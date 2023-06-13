import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class HTTPSConnectionExample {
    public static void main(String[] args) throws IOException {
            Socket socket = new Socket(InetAddress.getByName("crazycoder.tech"), 80);
        System.out.println("Connected");
            PrintStream writer = new PrintStream(socket.getOutputStream());
            BufferedReader reader = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
            writer.println("GET /ip.php HTTP/1.0\nUser-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/114.0.0.0 Safari/537.36\nAccept: text/html\n\n");
            String str;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
    }
}