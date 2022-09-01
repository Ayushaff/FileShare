import java.util.*;
import java.io.*;
import java.net.*;

//first write server program
class server1 {
    private ServerSocket serverSocket;

    server1() {
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
            InputStream is;
            InputStreamReader isr;
            OutputStream os;
            OutputStreamWriter osw;
            StringBuffer sb;

            int c1, c2, c3, c4;
            String pc1, pc2, pc3, pc4;

            int rollNum;
            int x;
            String name;
            String gender;
            String city;
            String request, response;
            Socket socket;

            while (true) {

                System.out.println("\n* server is ready to accept request on port 5500 *\n");
                System.out.println("Enter: roll , name , gender , city");

                socket = serverSocket.accept();// server socket goes in wait mode to accept request atak jayga fir atake
                                               // hue mode se bhar aayhga jb request aa jaygi tab ,
                // jo bhi incoming request h vo dusre socket me divert krdi javegi or dusre,
                // socket ka address return hoga jo ye socket name ka variable ko assign hua h

                // inputing client's request they gave
                // processing for response get the ref of inputstream

                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                sb = new StringBuffer();

                while (true) {
                    x = isr.read();//read method is returning unicode to x
                    if (x == -1)
                        break;
                    if (x == '#')
                        break;
                    // read method will return unicode so better typecast in char ,and put all into buffer 
                    sb.append((char) x);
                }

                request = sb.toString();
                System.out.println("request arrived " + request);

                // now request processing
                // 012345678910 
                // 101,sonu,M,indore#
                c1 = request.indexOf(","); // pehle koma tak
                // c1=3 101,
                c2 = request.indexOf(",", c1 + 1); // c1+1 = 4 se agle coma tak
                // c2=8 sonu,
                c3 = request.indexOf(",", c2 + 1);
                //c3 = 10 M/F
                c4 = request.indexOf("#", c3 + 1);
                //c4 = 17 as this example but,c4 size is non predictable also its known that its index will be till '#'

                pc1 = request.substring(0, c1); // 0 index se c1-1(first comma ke pehle tak ka sb aajyga) tak ka ayega 101 agya
                pc2 = request.substring(c1 + 1, c2); // c1+1 se c2-1 tak
                pc3 = request.substring(c2 + 1, c3); // c2 se c3-1 tk
                pc4 = request.substring(c3 + 1);    //c3+1 se jitna bhi remaining
                
                rollNum = Integer.parseInt(pc1);
                name = pc2;
                gender = pc3;
                city = pc4;

                System.out.printf("Roll Num : %d , Name : %s , Gender : %s ,City : %s \n ", rollNum, name, gender,
                        city);
                System.out.printf("\n");

                response = "Data saved#";

                os = socket.getOutputStream();
                osw = new OutputStreamWriter(os);
                osw.write(response);
                osw.flush(); 
                socket.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
     * char ch;     
     * System.out.println("Continue?? Press Y[es] or N[o] ");
     * Scanner sc = new Scanner(System.in);
     * ch = sc.next().charAt(0);
     * String che;
     * che=sc.nextLine();
     * 
     * if (ch == 'Y' || ch == 'y')
     * continue;
     * else if (che.compareToIgnoreCase("yes")==0)
     * continue;
     * 
     * else if(che.compareToIgnoreCase("no")==0)
     * break;
     * else if (ch == 'n' || ch == 'N')
     * break;
     * 
     * 
     * socket.close();
     * }
     * } catch (Exception e) {
     * System.out.println(e);
     * }
     */
    
    public static void main(String[] args) {
        server1 server = new server1();
    }
}