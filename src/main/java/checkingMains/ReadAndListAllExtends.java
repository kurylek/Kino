package checkingMains;

import models.*;
import objectPlus.ObjectPlus;

import java.io.*;
import java.util.List;

public class ReadAndListAllExtends {
    public static void main(String[] args) throws Exception {
        try {
            ObjectPlus.readExtent(new ObjectInputStream(new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\projekt")))));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        ObjectPlus.showExtent(Screening.class);
        ObjectPlus.showExtent(ScreeningRoom.class);
        ObjectPlus.showExtent(MovieTimedLicense.class);
        ObjectPlus.showExtent(MovieNoTimeLimitLicense.class);
        ObjectPlus.showExtent(Person.class);

        List<Person> p = (List<Person>) ObjectPlus.getExtent(Person.class);
        System.out.println(p.size());

        System.out.println(Screening.doScreeningExist());
    }
}