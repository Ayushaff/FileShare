import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import javax.swing.InputMap;
import javax.swing.plaf.synth.SynthPasswordFieldUI;

class RequestProcess extends Thread {
    private Socket socket;

    RequestProcess(Socket socket) {
        this.socket = socket;
        start();
    }

    public void run() {
        try {
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            int bytesToReceive = 1024;
            byte tmp[] = new byte[1024];
            byte header[] = new byte[1024];
            int bytesReadCount;
            int i, j, k;
            i = 0;
            j = 0;

            while (j < bytesToReceive) {
                bytesReadCount = is.read(tmp);
                if (bytesReadCount == -1)
                    continue;
                for (k = 0; k < bytesReadCount; k++) {
                    header[i] = tmp[k];
                    i++;
                }
                j += bytesReadCount;
            }
            int lengthOfFile = 0;
            i = 0;
            j = 1;
            while (header[i] != ',') {
                lengthOfFile = lengthOfFile + (header[i] * j);
                j *= 10;
                i++;
            }
            i++;

            StringBuffer sb = new StringBuffer();
            while (i <= 1023) {
                sb.append((char) header[i]);
                i++;
            }
            String fileName = sb.toString().trim();
            System.out.println("Receiving file : " + fileName + " of length : " + lengthOfFile);
            File file = new File("uploads" + File.separator + fileName);
            if (file.exists())
                file.delete();

            FileOutputStream fos = new FileOutputStream(file);
            byte ack[] = new byte[1];
            ack[0] = 1;
            os.write(ack, 0, 1);
            os.flush();

            int chunkSize = 4096;
            byte bytes[] = new byte[chunkSize];
            i = 0;
            long m;
            m = 0;
            while (m < lengthOfFile) {
                bytesReadCount = is.read(bytes);
                if (bytesReadCount == -1)
                    continue;
                fos.write(bytes, 0, bytesReadCount);
                fos.flush();
                m = m + bytesReadCount;
            }
            fos.close();
            ack[0] = 1;
            os.write(ack, 0, 1);
            os.flush();
            System.out.println("file saved to " + file.getAbsolutePath());
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

public class FTServer {
    private ServerSocket serverSocket;

    FTServer() {
        try {
            serverSocket = new ServerSocket(5500);
            startListening();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void startListening() {
        try {
            Socket socket;
            RequestProcess requestProcess;
            while (true) {
                System.out.println("Server is ready to accpet request on 5500");
                socket=serverSocket.accept();
                requestProcess = new RequestProcess(socket);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        FTServer server=new FTServer();
    }
}
