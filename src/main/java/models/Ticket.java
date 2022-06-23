package models;

import enums.EnumTicketType;
import objectPlus.ObjectPlus;

import java.math.BigDecimal;

public class Ticket extends ObjectPlus {
    private static final BigDecimal REUCED_TICKET_MULTIPLIER = new BigDecimal("0.75");
    private EnumTicketType ticketType;
    private BigDecimal price;
    private String ticketCode;

    private Person boughtBy;
    private Screening forScreening;

    public Ticket(EnumTicketType ticketType, Screening forScreening, Person boughtBy) throws Exception {
        if(forScreening == null) {
            throw new Exception("Given screening is null"); //TODO Own exc
        }
        if(boughtBy == null) {
            throw new Exception("Given person is null"); //TODO Own exc
        }

        this.ticketType = ticketType;
        this.forScreening = forScreening;
        this.boughtBy = boughtBy;
        this.price = checkPriceWithDiscount(forScreening, ticketType);

        forScreening.addTicket(this);
        boughtBy.addTicket(this);

        StringBuilder tCode = new StringBuilder(forScreening.getScreeningNumber());
        tCode.append(String.format("%03d", forScreening.getViewerCount()));
        this.ticketCode = String.valueOf(tCode);
    }

    public static BigDecimal checkPriceWithDiscount(Screening screening, EnumTicketType ticketType) {
        BigDecimal price = screening.getBaseTicketPrice();
        if(ticketType == EnumTicketType.REDUCED)
            price = price.multiply(REUCED_TICKET_MULTIPLIER);
        return price;
    }

    public String getTicketCode() {
        return ticketCode;
    }
}