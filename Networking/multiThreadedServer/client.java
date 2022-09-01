import java.net.*;
import java.io.*;


class client {
    public static void main(String[] gg) {

        try {
            int x;
            int roll = Integer.parseInt(gg[0]);
            String name = gg[1];
            String gender = gg[2];
            String city=gg[3];

            String request = roll + "," + name + "," + gender + ","+city+"#";
            String response;
            Socket socket = new Socket("localhost", 5500); //can also write local host at place of ipv4address
            // ip and port
            // localhost if server is at same machine 127.0.0.1 we will go with localhost
            // for now

            OutputStream os;
            OutputStreamWriter osw;
            InputStream is;
            InputStreamReader isr;
            StringBuffer sb;

            // outputing request
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            osw.write(request);
            osw.flush();
            ; // very impimp

            // reading response
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            sb = new StringBuffer();

            // reading response
            while (true) {
                x=isr.read(); 
                if (x == -1)
                    break;
                if (x == '#')
                    break;
                sb.append((char) x);
            }

            response=sb.toString();           
            System.out.println("Response is : "+response);
            socket.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
