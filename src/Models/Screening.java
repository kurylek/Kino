package Models;

import Enums.EnumScreeningStatus;
import Exceptions.AlreadyThatTypeException;
import Exceptions.CollidateException;
import ObjectPlus.ObjectPlus;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screening extends ObjectPlus {
    static int minViewerCount = 10;

    private String screeningNumber; //xxzzY -xx screeningRoom zz-screeningNumberThisDay Y-ddMMyyyy
    private Date screeningDateTime; //dd-MM-yyyy HH:mm
    private int viewerCount;
    private BigDecimal baseTicketPrice;
    private EnumScreeningStatus screeningStatus;

    private ScreeningRoom takesPlace;
    private Movie movieOnScreening;

    public Screening(String date, String time, BigDecimal baseTicketPrice, Movie movie, ScreeningRoom screeningRoom) throws CollidateException, ParseException {
        if(screeningRoom == null) {
            throw new NullPointerException("Given screening room in null!");
        }
        if(screeningRoom.isBusyAt(date, time, movie.getDuration())) {
            throw new CollidateException("Movie will collidate with over"); //moze zrobic swoj exception?
        }
        this.takesPlace = screeningRoom;
        screeningRoom.addScreening(this);

        StringBuilder sNumber = new StringBuilder(String.format("%02d", screeningRoom.getRoomNumber()));
        sNumber.append(String.format("%04d", screeningRoom.getScreeningCount()));

        this.screeningNumber = String.valueOf(sNumber);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.screeningDateTime = dateFormat.parse(date + " " + time);
        this.viewerCount = 0;
        this.baseTicketPrice = baseTicketPrice;
        this.screeningStatus = EnumScreeningStatus.PLANNED;
        this.movieOnScreening = movie;
        movie.addPlayedOn(this);
    }

    public void changeScreeningStatus(EnumScreeningStatus status) throws AlreadyThatTypeException {
        if(screeningStatus == status){
            throw new AlreadyThatTypeException("This screening has already this status!");
        }

        this.screeningStatus = status;
    }

    public Movie getMovieOnScreening() {
        return movieOnScreening;
    }

    public Date getScreeningDateTime() {
        return screeningDateTime;
    }

    public void increaseViewerCount() {
        this.viewerCount++;
    }

    public String getScreeningNumber() {
        return screeningNumber;
    }

    @Override
    public String toString() {
        return "Screening info {Number- " + screeningNumber + ", Date- " + screeningDateTime +
                ", Current viewers count- " + viewerCount + ", Base ticket price- " + baseTicketPrice +
                ", Screening status- " + screeningStatus + ", Takes place in screening room- "
                + takesPlace.getRoomNumber() + ", Displays movie- " + movieOnScreening.getTitle() + "}";
    }
}