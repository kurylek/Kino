package models;

import enums.EnumScreeningStatus;
import exceptions.AlreadyThatTypeException;
import exceptions.CollidateException;
import objectPlus.ObjectPlus;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Screening extends ObjectPlus {
    private static List<Screening> allScreenings = new ArrayList<>();
    private static int minViewerCount = 10;

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

        allScreenings.add(this);
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

    public EnumScreeningStatus getScreeningStatus() {
        return screeningStatus;
    }

    public boolean canBuyTicket() {
        return viewerCount<takesPlace.getSeatsCount();
    }

    public static void setAllScreenings(){
        try {
            allScreenings = (List<Screening>) ObjectPlus.getExtent(Screening.class);
        } catch (ClassNotFoundException e) {
            allScreenings = new ArrayList<>();
        }
    }

    public static boolean doScreeningExist(){
        return allScreenings.size() > 0;
    }

    public static List<Screening> getScreeningsAtDate(String date) {
        List<Screening> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for(Screening s : allScreenings) {
            Date sDate = s.getScreeningDateTime();
            if(date.equals(dateFormat.format(sDate))) {
                result.add(s);
            }
        }

        return result;
    }

    public BigDecimal getBaseTicketPrice() {
        return baseTicketPrice;
    }

    public static void updateScreeningStatus(){
        Date now = new Date();
        for(Screening s : allScreenings){
            if(s.screeningStatus == EnumScreeningStatus.PLANNED){
                if(now.compareTo(s.screeningDateTime) > 0) {
                    Date sEndTime = new Date(s.screeningDateTime.getTime() + (60000L * s.getMovieOnScreening().getDuration()));
                    if (now.compareTo(sEndTime) < 0) {
                        s.screeningStatus = EnumScreeningStatus.DURING;
                    }else{
                        s.screeningStatus = EnumScreeningStatus.COMPLETED;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Screening info {Number- " + screeningNumber + ", Date- " + screeningDateTime +
                ", Current viewers count- " + viewerCount + ", Base ticket price- " + baseTicketPrice +
                ", Screening status- " + screeningStatus + ", Takes place in screening room- "
                + takesPlace.getRoomNumber() + ", Displays movie- " + movieOnScreening.getTitle() + "}";
    }
}