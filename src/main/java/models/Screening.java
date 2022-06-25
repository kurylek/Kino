package models;

import enums.EnumScreeningStatus;
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
    private List<Ticket> soldTickets;

    public Screening(String date, String time, BigDecimal baseTicketPrice, Movie movie, ScreeningRoom screeningRoom) throws CollidateException, ParseException {
        if(screeningRoom == null) {
            throw new NullPointerException("Given screening room in null!");
        }
        if(screeningRoom.isBusyAt(date, time, movie.getDuration())) {
            throw new CollidateException("Movie will collidate with over");
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
        this.soldTickets = new ArrayList<>();

        movie.addPlayedOn(this);

        allScreenings.add(this);
    }

    public Movie getMovieOnScreening() {
        return movieOnScreening;
    }

    public Date getScreeningDateTime() {
        return screeningDateTime;
    }

    public String getScreeningNumber() {
        return screeningNumber;
    }

    public ScreeningRoom getTakesPlace() {
        return takesPlace;
    }

    public EnumScreeningStatus getScreeningStatus() {
        return screeningStatus;
    }

    public boolean canBuyTicket() {
        return viewerCount<takesPlace.getSeatsCount();
    }

    public int getViewerCount() {
        return viewerCount;
    }

    /***
     * Load all screenings from ObjectPlus
     */
    public static void setAllScreenings(){
        try {
            allScreenings = (List<Screening>) ObjectPlus.getExtent(Screening.class);
        } catch (ClassNotFoundException e) {
            allScreenings = new ArrayList<>();
        }
    }

    /***
     * Check if there are any planned screenings
     * @return Return true/false if there are any planned screenings
     */
    public static boolean doScreeningExist(){
        return allScreenings.size() > 0;
    }

    /***
     * Get screenings at given date
     * @param date Date of screenings that we want
     * @return Screenings at given date
     */
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

    /***
     * Update screenings status
     */
    public static void updateScreeningStatus(){
        Date now = new Date();
        for(Screening s : allScreenings){
            if(s.screeningStatus == EnumScreeningStatus.PLANNED || s.screeningStatus == EnumScreeningStatus.DURING){ //Update only planned/during screenings
                if(now.compareTo(s.screeningDateTime) > 0) { //Check if screening started
                    Date sEndTime = new Date(s.screeningDateTime.getTime() + (60000L * s.getMovieOnScreening().getDuration())); //Get screening end time
                    if (now.compareTo(sEndTime) < 0) { //Check if screening ended
                        s.screeningStatus = EnumScreeningStatus.DURING;
                    }else{
                        s.screeningStatus = EnumScreeningStatus.COMPLETED;
                    }
                }
            }
        }
    }

    public void addTicket(Ticket ticket) {
        this.soldTickets.add(ticket);
        this.viewerCount++;
    }

    @Override
    public String toString() {
        return "Screening info {Number- " + screeningNumber + ", Date- " + screeningDateTime +
                ", Current viewers count- " + viewerCount + ", Base ticket price- " + baseTicketPrice +
                ", Screening status- " + screeningStatus + ", Takes place in screening room- "
                + takesPlace.getRoomNumber() + ", Displays movie- " + movieOnScreening.getTitle() + "}";
    }
}