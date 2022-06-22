package models;

import enums.EnumTicketType;
import objectPlus.ObjectPlus;

import java.math.BigDecimal;

public class Ticket extends ObjectPlus {
    private static final BigDecimal REUCED_TICKET_MULTIPLIER = new BigDecimal("0.75");
    private EnumTicketType ticketType;
    private BigDecimal price;

    public Ticket(EnumTicketType ticketType, BigDecimal price) {
        this.ticketType = ticketType;

        if(ticketType == EnumTicketType.NORMAL) {
            this.price = price;
        }else {
            this.price = price.multiply(REUCED_TICKET_MULTIPLIER);
        }
    }

    public static BigDecimal checkPriceWithDiscount(Screening screening, EnumTicketType ticketType) {
        BigDecimal price = screening.getBaseTicketPrice();
        if(ticketType == EnumTicketType.REDUCED)
            price = price.multiply(REUCED_TICKET_MULTIPLIER);
        return price;
    }
}