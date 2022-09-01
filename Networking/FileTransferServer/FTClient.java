import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class FTClient {
    public static void main(String[] gg) {
        try {
            String fileName = gg[0];
            File file = new File(fileName);

            if (file.exists() == false) {
                System.out.println("File " + file.getName() + " doesnt exist");
            }
            if (file.isDirectory()) {
                System.out.println(fileName + " is a director not a file");
                return;
            }
            // initial
            long lengthOfFile = file.length();
            String name = file.getName();
            byte header[] = new byte[1024];
            // start making header with lengthOfFile and nameOfFile
            int i;
            long x, k;
            i = 0;
            k = lengthOfFile;

            // for length
            while (k > 0) {
                header[i] = (byte) (k % 10);
                k /= 10;
                i++;
            }
            // length done

            header[i] = (byte) ',';
            i++;

            // for name
            x = name.length();
            int r = 0;
            while (r < x) {
                header[i] = (byte) name.charAt(r);
                i++;
                r++;
            }
            // name done
            while (i < 1023) {
                header[i] = (byte) 32;
                i++;
            }
            // header completed
            // now connection
            Socket socket = new Socket("localhost", 5500);
            //send header
            OutputStream os=socket.getOutputStream();
            os.write(header,0,1024);    //from which index,how many
            os.flush();

            //to take acknoledgment
            InputStream is=socket.getInputStream();
            byte ack[]=new byte[1];

            int bytesReadCount;

            while(true){
                bytesReadCount=is.read(ack);
                if(bytesReadCount==-1)continue;
                break;
            }
            // acknoledgment done

            //write bytes
            FileInputStream fis=new FileInputStream(file);
            int chunkSize=4096;
            byte bytes[]=new byte[chunkSize];
            int j=0;
            while(j<lengthOfFile){
                bytesReadCount=fis.read(bytes);
                os.write(bytes, 0, bytesReadCount);
                os.flush();
                j+=bytesReadCount;
            }
            //bytes done 

            //read acknoledgement
            while(true){
                bytesReadCount=is.read(ack);
                if(bytesReadCount==-1)continue;
                break;
            }
            System.out.println("file "+fileName+"uploaded");
            socket.close();

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
