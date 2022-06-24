package models;

import enums.EnumPersonType;
import enums.EnumTicketType;
import exceptions.AlreadyThatTypeException;
import exceptions.WrongPersonTypeException;
import objectPlus.ObjectPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

public class Person extends ObjectPlus {
    private static List<Person> allPersons = new ArrayList<>();

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Address address;
    private EnumSet<EnumPersonType> personTypes;
    private List<ScreeningRoom> operate;
    private List<Ticket> boughtTickets;

    private Person(String firstName, String lastName, String email, String phoneNumber, Address address, EnumPersonType personType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.personTypes = EnumSet.of(EnumPersonType.PERSON, personType);
        this.operate = new ArrayList<>();
        this.boughtTickets = new ArrayList<>();

        allPersons.add(this);
    }

    public static Person createClientAccount(String firstName, String lastName, String email, String phoneNumber) {
        Person person = new Person(firstName, lastName, email, phoneNumber, null, EnumPersonType.CLIENT);
        return person;
    }

    public static Person createClientAccount(String firstName, String lastName, String email) {
        Person person = new Person(firstName, lastName, email, null, null, EnumPersonType.CLIENT);
        return person;
    }

    public static Person createEmployeeAccount(String firstName, String lastName, String street, String number, String zipCode, String city) {
        Address address = new Address(street, number, zipCode, city);
        Person person = new Person(firstName, lastName, null, null, address, EnumPersonType.EMPLOYEE);
        return person;
    }

    public void hire(String street, String number, String zipCode, String city) throws AlreadyThatTypeException {
        if(personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new AlreadyThatTypeException("This person is already an employee!");
        }

        personTypes.add(EnumPersonType.EMPLOYEE);
        address = new Address(street, number, zipCode, city);
    }

    public void fire() throws AlreadyThatTypeException {
        if(!personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new AlreadyThatTypeException("This person isn't an employee!");
        }

        operate = new ArrayList<>();
        personTypes.remove(EnumPersonType.EMPLOYEE);
        address = null;
    }

    public void becomeClient(String email, String phoneNumber) throws AlreadyThatTypeException {
        if(personTypes.contains(EnumPersonType.CLIENT)) {
            throw new AlreadyThatTypeException("This person is already a client!");
        }

        personTypes.add(EnumPersonType.CLIENT);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


    public void becomeClient(String email) throws AlreadyThatTypeException {
        becomeClient(email, null);
    }

    public static void setAllPersons(){
        try {
            allPersons = (List<Person>) ObjectPlus.getExtent(Person.class);
        } catch (ClassNotFoundException e) {
            allPersons = new ArrayList<>();
        }
    }

    public static Person getClientByEmail(String email) throws Exception {
        Person result = null;
        for(Person p : allPersons){
            if(p.email != null)
                if(p.email.equals(email))
                    return p;
        }
        throw new Exception("No client found!"); //własny exc TODO
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public EnumSet<EnumPersonType> getPersonTypes() {
        return personTypes;
    }

    public void addScreeningRoomToOperate(ScreeningRoom screeningRoom) throws Exception {
        if(!this.personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new WrongPersonTypeException("This person is not an(?) employee");
        }
        if(screeningRoom == null) {
            throw new Exception("Given screening room is null");//TODO własny exc
        }
        if(!operate.contains(screeningRoom)) {
            operate.add(screeningRoom);
            screeningRoom.addOperatedBy(this);
        }
    }

    public boolean operateOn(ScreeningRoom screeningRoom) throws WrongPersonTypeException {
        if(!this.personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new WrongPersonTypeException("This person is not an(?) employee");
        }
        return operate.contains(screeningRoom);
    }

    public boolean canBeBusy(Screening screening) throws WrongPersonTypeException, ParseException {
        if(!this.personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new WrongPersonTypeException("This person is not an(?) employee");
        }
        if(operate.contains(screening.getTakesPlace())) {
            return false;
        }
        Date screeningDate = screening.getScreeningDateTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        List<Screening> screeningsToCheck = Screening.getScreeningsAtDate(dateFormat.format(screeningDate));

        Date screeningStartTime = timeFormat.parse(timeFormat.format(screeningDate));
        Date screeningEndTime = new Date(screeningStartTime.getTime() + (60000L * screening.getMovieOnScreening().getDuration()));

        for(Screening s : screeningsToCheck) {
            if(operate.contains(s.getTakesPlace()) && !s.equals(screening)) {
                Date checkingStartTime = timeFormat.parse(timeFormat.format(s.getScreeningDateTime()));
                Date checkingEndTime = new Date(checkingStartTime.getTime() + (60000L * s.getMovieOnScreening().getDuration()));

                if(checkingStartTime.compareTo(screeningEndTime) < 0 && checkingEndTime.compareTo(screeningStartTime) > 0) {
                    return true;
                }
            }
        }

        return false;
    }

    public Ticket buyTicketForScreening(Screening forScreening, EnumTicketType ticketType) throws Exception {
        return new Ticket(ticketType, forScreening, this);
    }

    public void addTicket(Ticket ticket) {
        this.boughtTickets.add(ticket);
    }

    public List<Ticket> getBoughtTickets() {
        return boughtTickets;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address=" + address +
                ", personTypes=" + personTypes +
                '}';
    }
}