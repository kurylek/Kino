package models;

import enums.EnumPersonType;
import exceptions.AlreadyThatTypeException;
import objectPlus.ObjectPlus;

import java.util.ArrayList;
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

    private Person(String firstName, String lastName, String email, String phoneNumber, Address address, EnumPersonType personType) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.personTypes = EnumSet.of(EnumPersonType.PERSON, personType);

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