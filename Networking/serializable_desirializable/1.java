import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Student implements Serializable {
    public int roll;
    public String name;
    public char gender;
}

class psp {
    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "sony";
        s1.roll = 100;
        s1.gender = 'M';
        try {
            // convert this object into byteArray
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s1);
            oos.flush();

            byte b[];
            b = baos.toByteArray();
            System.out.println("Object serialized");

            // deserialize now
            ByteArrayInputStream bais = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Student s2 = (Student) ois.readObject();

            System.out.println("object deserialized");

            System.out.println("roll " + s2.roll);
            System.out.println("name " + s2.name);
            System.out.println("gender " + s2.gender);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
