package models;

import enums.EnumPersonType;
import enums.EnumTicketType;
import exceptions.AlreadyThatTypeException;
import exceptions.ClientDoNotExistException;
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

    /***
     * Hire person to become employee
     * @param street Employee address info- street
     * @param number Employee address info- number
     * @param zipCode Employee address info- zip code
     * @param city Employee address info- city
     * @throws AlreadyThatTypeException Threw when person is already an employee
     */
    public void hire(String street, String number, String zipCode, String city) throws AlreadyThatTypeException {
        if(personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new AlreadyThatTypeException("This person is already an employee!");
        }

        personTypes.add(EnumPersonType.EMPLOYEE);
        address = new Address(street, number, zipCode, city);
    }

    /***
     * Fire employee
     * @throws AlreadyThatTypeException Threw when person is not an employee
     */
    public void fire() throws AlreadyThatTypeException {
        if(!personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new AlreadyThatTypeException("This person isn't an employee!");
        }

        operate = new ArrayList<>();
        personTypes.remove(EnumPersonType.EMPLOYEE);
        address = null;
    }

    /***
     * Make employee client account
     * @param email Client email
     * @param phoneNumber Client phone number
     * @throws AlreadyThatTypeException Threw when person is already client
     */
    public void becomeClient(String email, String phoneNumber) throws AlreadyThatTypeException {
        if(personTypes.contains(EnumPersonType.CLIENT)) {
            throw new AlreadyThatTypeException("This person is already a client!");
        }

        personTypes.add(EnumPersonType.CLIENT);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /***
     * Make employee client account
     * @param email Client email
     * @throws AlreadyThatTypeException Threw when person is already client
     */
    public void becomeClient(String email) throws AlreadyThatTypeException {
        becomeClient(email, null);
    }

    /***
     * Load all person from ObjectPlus
     */
    public static void setAllPersons(){
        try {
            allPersons = (List<Person>) ObjectPlus.getExtent(Person.class);
        } catch (ClassNotFoundException e) {
            allPersons = new ArrayList<>();
        }
    }

    /***
     * Get person by email
     * @param email Email of client we want
     * @return Return person with given email
     * @throws ClientDoNotExistException Threw when client with given email do not exist
     */
    public static Person getClientByEmail(String email) throws ClientDoNotExistException {
        for(Person p : allPersons){
            if(p.email != null)
                if(p.email.equals(email))
                    return p;
        }
        throw new ClientDoNotExistException("No client found!");
    }

    public String getName() {
        return firstName + " " + lastName;
    }

    public EnumSet<EnumPersonType> getPersonTypes() {
        return personTypes;
    }

    /***
     * Adds screening room to employee operates list
     * @param screeningRoom Screening room to add
     * @throws WrongPersonTypeException Threw when given person is not an employee
     */
    public void addScreeningRoomToOperate(ScreeningRoom screeningRoom) throws WrongPersonTypeException {
        if(!this.personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new WrongPersonTypeException("This person is not an employee");
        }
        if(screeningRoom == null) {
            throw new NullPointerException("Given screening room is null");
        }
        if(!operate.contains(screeningRoom)) {
            operate.add(screeningRoom);
            screeningRoom.addOperatedBy(this);
        }
    }

    /***
     * Check if employee works on given screening room
     * @param screeningRoom Screening room to check
     * @return Return true/false if employee operate on given screening room
     * @throws WrongPersonTypeException Threw when given person is not an employee
     */
    public boolean operateOn(ScreeningRoom screeningRoom) throws WrongPersonTypeException {
        if(!this.personTypes.contains(EnumPersonType.EMPLOYEE)) {
            throw new WrongPersonTypeException("This person is not an employee");
        }
        return operate.contains(screeningRoom);
    }

    /***
     * Check if employee can be busy on other screenings, that takes place on screening room that he take care of
     * @param screening Screening to check
     * @return Return true/false if employee is busy
     * @throws WrongPersonTypeException Threw when given person is not an Employee
     * @throws ParseException Threw while formating date
     */
    public boolean canBeBusy(Screening screening) throws WrongPersonTypeException, ParseException {
        if(!this.personTypes.contains(EnumPersonType.EMPLOYEE)) { //Check if person is Employee
            throw new WrongPersonTypeException("This person is not an employee");
        }
        if(operate.contains(screening.getTakesPlace())) { //Check if person operate on this screening room
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

    public Ticket buyTicketForScreening(Screening forScreening, EnumTicketType ticketType) {
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
        String result = firstName + " " + lastName;
        String types = " (";
        if(personTypes.contains(EnumPersonType.CLIENT)) {
            types  += "I'm client!";
            result += ", email: " + email;
            if(phoneNumber != null)
                result += ", phone number:" + phoneNumber;
        }
        if(personTypes.contains(EnumPersonType.EMPLOYEE)){
            types += "I'm employee!";
            result += ", address: " + address;
        }

        return result + types +")";
    }
}