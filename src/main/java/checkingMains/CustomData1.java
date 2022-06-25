package checkingMains;

import enums.EnumMovieCategory;
import enums.EnumScreeningRoomType;
import enums.EnumTicketType;
import exceptions.AlreadyThatTypeException;
import models.*;
import objectPlus.ObjectPlus;

import java.io.*;
import java.math.BigDecimal;

public class CustomData1 {
    public static void main(String[] args) throws Exception {
        //Clients
        Person p1 = Person.createClientAccount("Jan", "Kowalski", "kowalski@gmail.com");
        Person p2 = Person.createClientAccount("Jan", "Nowak", "test1");

        //Employees
        Person p3 = Person.createEmployeeAccount("Anna", "Nowak", "Mila", "3a", "00-001", "Warszawa");
        Person p4 = Person.createEmployeeAccount("Anna", "Kowalska", "Mila", "3b", "00-001", "Warszawa");

        //Employee become client
        try {
            p4.becomeClient("test2", "123456789");
        } catch (AlreadyThatTypeException e) {
            e.printStackTrace();
        }

        //Screening rooms
        ScreeningRoom sr1 = new ScreeningRoom(1, 60, EnumScreeningRoomType.TYPE_2D);
        ScreeningRoom sr2 = new ScreeningRoom(2, 30, EnumScreeningRoomType.TYPE_3D);

        //Employee will work on screening room
        p4.addScreeningRoomToOperate(sr1);

        //Movies
        MovieTimedLicense m1 = new MovieTimedLicense("Action movie", 60, EnumMovieCategory.ACTION, "10-06-2022", 30);
        MovieNoTimeLimitLicense m2 = new MovieNoTimeLimitLicense("Horror movie", 60, EnumMovieCategory.HORROR, "10-06-2022", 10);
        MovieTimedLicense m3 = new MovieTimedLicense("Movie to delete", 60, EnumMovieCategory.COMEDY, "01-01-2022", 1);

        //Screenings
        Screening s1 = new Screening("15-06-2022", "18:00", BigDecimal.valueOf(20), m1, sr1);
        //Cant create screening when there is planned screening
        //Screening error1 = new Screening("15-06-2022", "18:30", BigDecimal.valueOf(25), m1, sr1); //Starts when other screening is ongoing
        //Screening error2 = new Screening("15-06-2022", "17:30", BigDecimal.valueOf(25), m1, sr1); //Ends when other screening is ongoing
        String day = "25-06-2022";
        Screening s2 = new Screening(day, "11:30", BigDecimal.valueOf(20), m1, sr1);
        Screening s3 = new Screening(day, "18:00", BigDecimal.valueOf(25), m2, sr2);
        Screening s4 = new Screening(day, "11:00", BigDecimal.valueOf(30), m2, sr2);
        Screening s5 = new Screening(day, "19:30", BigDecimal.valueOf(20), m1, sr1);
        Screening s6 = new Screening(day, "18:30", BigDecimal.valueOf(25), m1, sr1);
        Screening s7 = new Screening(day, "22:00", BigDecimal.valueOf(15), m2, sr2);

        //Person buy ticket
        p4.buyTicketForScreening(s1, EnumTicketType.NORMAL);
        p4.buyTicketForScreening(s7, EnumTicketType.REDUCED);

        //Save data
        try {
            ObjectPlus.writeExtent(new ObjectOutputStream(new DataOutputStream(new BufferedOutputStream(new FileOutputStream("D:\\projekt")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
