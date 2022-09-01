import java.io.ByteArrayInputStream;
import java.io.*;
import java.util.*;
import javax.naming.spi.ObjectFactoryBuilder;

class City implements Serializable {
    public int code;
    public String name; 
}

class student implements Serializable {
    public int roll;
    public String name;
    public char gender;
    public City city;
}

class psp {
    public static void main(String[] args) {
        student s1 = new student();
        s1.name = "sony";
        s1.roll = 100;
        s1.gender = 'M';
        City c = new City();
        c.code = 101;
        c.name = "lmau";
        s1.city = c;
        try {
            // serializing
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s1);
            oos.flush();

            byte b[];
            b = baos.toByteArray();
            System.out.println("obj serialized");

            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            student s2 = (student) ois.readObject();

            System.out.println("obj deserialized");
            System.out.println("student details");
            System.out.println(s2.roll);
            System.out.println(s2.name);
            System.out.println(s2.gender);
            System.out.println("city Details");
            System.out.println(s2.city.code);
            System.out.println(s2.city.name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
