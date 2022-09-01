import java.util.*;
import java.io.*;
import java.net.*;

class RequestProcessor extends Thread {
    private Socket socket;

    RequestProcessor(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            InputStream is;
            InputStreamReader isr;
            OutputStream os;
            OutputStreamWriter osw;

            StringBuffer sb;
            int x;
            int c1, c2, c3, c4;
            String pc1, pc2, pc3, pc4;

            int roll;
            String name, gender, city;
            String request, response;

            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            sb = new StringBuffer();

            // reading or inputing request
            while (true) {
                x = isr.read();
                if (x == -1)
                    break;
                if (x == '#')
                    break;
                sb.append((char) x);
            }
            request = sb.toString();
            System.out.println("request arrived : " + request);

            c1 = request.indexOf(",");
            c2 = request.indexOf(",", c1 + 1);
            c3 = request.indexOf(",", c2 + 1);
            c4 = request.indexOf("#", c3 + 1);

            pc1 = request.substring(0, c1);
            pc2 = request.substring(c1 + 1, c2);
            pc3 = request.substring(c2 + 1, c3);
            pc4 = request.substring(c3+1);

            roll = Integer.parseInt(pc1);
            name = pc2;
            gender = pc3;
            city = pc4;
            System.out.printf("Roll Num : %d , Name : %s , Gender : %s ,City : %s \n ", roll, name, gender,
            city);
            response = "Data Saved#";

            // writing or sending or outputing response
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            osw.write(response);
            osw.flush();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

class server {
    private ServerSocket serverSocket;

    server() {
        try {
            serverSocket = new ServerSocket(5500); // bind with some port if 5500 aint available ,exception will be
                                                   // generated
            startListening();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startListening() {
        try {
            Socket socket;
            RequestProcessor requestProcessor;
            while (true) {
                System.out.println("server is ready to accpet the request on port 5500");
                socket = serverSocket.accept();
                requestProcessor = new RequestProcessor(socket);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        server server = new server();
    }
}