package Models;

import Enums.EnumScreeningStatus;
import Exceptions.AlreadyThatTypeException;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screening {
    static int minViewerCount = 10;

    private Date screeningDate; //dd-MM-yyyy HH:mm
    private int viewerCount;
    private BigDecimal baseTicketPrice;
    private EnumScreeningStatus screeningStatus;

    public Screening(String date, String time, BigDecimal baseTicketPrice, Movie movie, ScreeningRoom screeningRoom) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        this.screeningDate = dateFormat.parse(date + " " + time);
        this.viewerCount = 0;
        this.baseTicketPrice = baseTicketPrice;
        this.screeningStatus = EnumScreeningStatus.PLANNED;
    }

    public void changeScreeningStatus(EnumScreeningStatus status) throws AlreadyThatTypeException {
        if(screeningStatus == status){
            throw new AlreadyThatTypeException("This screebubg has already this status!");
        }

        this.screeningStatus = status;
    }

    public void increaseViewerCount() {
        this.viewerCount++;
    }


}