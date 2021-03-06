package models;

import enums.EnumTicketType;
import objectPlus.ObjectPlus;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class Ticket extends ObjectPlus {
    private static final BigDecimal REDUCED_TICKET_MULTIPLIER = new BigDecimal("0.75");
    private EnumTicketType ticketType;
    private BigDecimal price;
    private String ticketCode;
    private Person boughtBy;
    private Screening forScreening;

    public Ticket(EnumTicketType ticketType, Screening forScreening, Person boughtBy) {
        if(forScreening == null) {
            throw new NullPointerException("Given screening is null");
        }
        if(boughtBy == null) {
            throw new NullPointerException("Given person is null");
        }

        this.ticketType = ticketType;
        this.forScreening = forScreening;
        this.boughtBy = boughtBy;
        this.price = checkPriceWithDiscount(forScreening, ticketType);

        forScreening.addTicket(this);
        boughtBy.addTicket(this);

        //TicketCode - [ScreeningNumber][xxx] xxx-Viewer number
        StringBuilder tCode = new StringBuilder(forScreening.getScreeningNumber());
        tCode.append(String.format("%03d", forScreening.getViewerCount()));
        this.ticketCode = String.valueOf(tCode);
    }

    /***
     * Check what price will ticket have
     * @param screening Screening to check ticket price
     * @param ticketType Selected discount type
     * @return Ticket price
     */
    public static BigDecimal checkPriceWithDiscount(Screening screening, EnumTicketType ticketType) {
        BigDecimal price = screening.getBaseTicketPrice();
        if(ticketType == EnumTicketType.REDUCED)
            price = price.multiply(REDUCED_TICKET_MULTIPLIER);
        return price;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public Screening getForScreening() {
        return forScreening;
    }

    public void printInfo() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        //In polish, bc UI is in polish
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        return "Numer biletu: " + ticketCode + ", typ biletu: " + ticketType + ", cena: " + price
                + "z??, film: " + forScreening.getMovieOnScreening().getTitle() + ", data seansu: "
                + dateFormat.format(forScreening.getScreeningDateTime());
    }
}