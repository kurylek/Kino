package checkingMains;

import models.*;
import objectPlus.ObjectPlus;

import java.io.*;

public class ReadAndListExtends {
    public static void main(String[] args) throws Exception {
        try {
            ObjectPlus.readExtent(new ObjectInputStream(new DataInputStream(new BufferedInputStream(new FileInputStream("D:\\projekt")))));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Movie.deleteMoviesWithExpiredLicense();
        Screening.updateScreeningStatus();

        ObjectPlus.showExtent(Screening.class);
        ObjectPlus.showExtent(ScreeningRoom.class);
        ObjectPlus.showExtent(MovieTimedLicense.class);
        ObjectPlus.showExtent(MovieNoTimeLimitLicense.class);
        ObjectPlus.showExtent(Person.class);
    }
}
