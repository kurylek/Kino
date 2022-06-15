package CheckingMains;

import Enums.EnumMovieCategory;
import Enums.EnumScreeningRoomType;
import Exceptions.CollidateException;
import Models.MovieNoTimeLimitLicense;
import Models.MovieTimedLicense;
import Models.Screening;
import Models.ScreeningRoom;
import ObjectPlus.ObjectPlus;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;

public class CheckMovie_Screening_ScreeningRoom {
    public static void main(String[] args) throws ParseException, CollidateException {
        ScreeningRoom sr1 = new ScreeningRoom(1, 60, EnumScreeningRoomType.TYPE_2D);
        ScreeningRoom sr2 = new ScreeningRoom(2, 30, EnumScreeningRoomType.TYPE_3D);

        MovieTimedLicense m1 = new MovieTimedLicense("Movie1", 60, EnumMovieCategory.ACTION, "10-06-2022", 30);
        MovieNoTimeLimitLicense m2 = new MovieNoTimeLimitLicense("Movie2", 60, EnumMovieCategory.ACTION, "10-06-2022", 10);

        Screening s1 = new Screening("15-06-2022", "18:00", BigDecimal.valueOf(25), m1, sr1);
        //Cant create screening when there is planned screening
        //Screening error1 = new Screening("15-06-2022", "18:30", BigDecimal.valueOf(25), m1, sr1); //Starts when other screening is ongoing
        //Screening error2 = new Screening("15-06-2022", "17:30", BigDecimal.valueOf(25), m1, sr1); //Ends when other screening is ongoing
        Screening s2 = new Screening("15-06-2022", "19:30", BigDecimal.valueOf(25), m1, sr1);
        Screening s3 = new Screening("15-06-2022", "18:00", BigDecimal.valueOf(25), m2, sr2);
        Screening s4 = new Screening("16-06-2022", "18:00", BigDecimal.valueOf(25), m2, sr2);

        System.out.println("Screenings at sr1 at 15-06-2022:");
        for(Screening s : sr1.getScreeningsAtDate("15-06-2022")){
            System.out.println(s.toString());
        }
        System.out.println("~~\nScreenings at sr1 at 16-06-2022:");
        for(Screening s : sr1.getScreeningsAtDate("16-06-2022")){
            System.out.println(s.toString());
        }
        System.out.println("~~\nScreenings at sr2 at 15-06-2022:");
        for(Screening s : sr2.getScreeningsAtDate("15-06-2022")){
            System.out.println(s.toString());
        }
        System.out.println("~~\nScreenings at sr2 at 16-06-2022:");
        for(Screening s : sr2.getScreeningsAtDate("16-06-2022")){
            System.out.println(s.toString());
        }

        System.out.println("\n\nAre there screenings planned? " + Screening.doScreeningExist());
        System.out.println("Screenings at 16-06-2022:");
        for(Screening s : Screening.getScreeningsAtDate("16-06-2022")){
            System.out.println(s.toString());
        }

        System.out.println("\n\nMovie1");
        m1.listPlatedOn();
        System.out.println("~~\nMovie2");
        m2.listPlatedOn();

        try {
            ObjectPlus.writeExtent(new ObjectOutputStream(new DataOutputStream(new BufferedOutputStream(new FileOutputStream("D:\\projekt")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
