package enums;

public enum EnumTicketType {
    NORMAL ("normalny"),
    REDUCED ("ulgowy");

    private final String typ;

    EnumTicketType(String typ) {
        this.typ = typ;
    }

    @Override
    public String toString() {
        return this.typ;
    }
}