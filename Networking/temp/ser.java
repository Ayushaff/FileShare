import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.*;
import java.io.*;

class server {
    private ServerSocket serversocket;
    private Socket socket;

    server() {

        try {
            serversocket = new ServerSocket(5500);
            startListening();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startListening() {

        try {
            InputStream is;
            InputStreamReader isr;
            OutputStream os;
            OutputStreamWriter osw;
            StringBuffer sb;
            int c1, c2, c3;
            String pc1, pc2, pc3;
            int x;
            int roll;
            String name;
            String gender;
            String request, response;
            while (true) {
                System.out.println("server ready to accpt reqst on port no 5500");
                socket = serversocket.accept();

                is = socket.getInputStream();
                sb = new StringBuffer();
                isr = InputStreamReader(is);
                while (true) {
                    x = isr.read();
                    if (x == -1)
                        break;
                    if (x == '#')
                        break;
                    sb.append((char) x);
                }
                request = sb.toString();
                System.out.println("reqst arr : " + request);
                c1 = request.indexOf(",");
                c2 = request.indexOf(",", c1 + 1);
                pc1 = request.substring(0, c1);
                pc2 = request.substring(c1 + 1, c2);
                pc3 = request.substring(c2 + 1);
                roll=Integer.parseInt(pc1);
                
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class psp {
    public static void main(String[] args) {
        server ser = new server();
    }
}}