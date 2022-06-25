package models;

import enums.EnumPersonType;
import enums.EnumScreeningRoomType;
import exceptions.PartConnectedException;
import exceptions.WrongPersonTypeException;
import objectPlus.ObjectPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ScreeningRoom extends ObjectPlus {
    private int roomNumber;
    private int seatsCount;
    private EnumScreeningRoomType type;

    private List<Person> operatedBy;
    private List<Speaker> speakers;
    private static Set<Speaker> allSpeakers = new HashSet<>();
    private List<Screening> listOfScreenings;

    public ScreeningRoom(int roomNumber, int seatsCount, EnumScreeningRoomType type) {
        this.roomNumber = roomNumber;
        this.seatsCount = seatsCount;
        this.type = type;
        this.speakers = new ArrayList<>();
        this.listOfScreenings = new ArrayList<>();
        this.operatedBy = new ArrayList<>();
    }

    /***
     * Add given speaker to screening room speakers list
     * @param speaker Speaker to add
     * @throws PartConnectedException Threw when given speaker is connected to other screening room
     */
    public void addSpeaker(Speaker speaker) throws PartConnectedException {
        if(!speakers.contains(speaker)){
            if(allSpeakers.contains(speaker)) {
                throw new PartConnectedException("Given speaker is already installed in other screening room!");
            }
            speakers.add(speaker);
        }
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getSeatsCount() {
        return seatsCount;
    }

    public void addScreening(Screening screening) {
        this.listOfScreenings.add(screening);
    }

    /***
     * Check if screening room is busy
     * @param date Date when we want to check if screening room is busy
     * @param time Time when we want to check if screening room is busy
     * @param movieDuration Duration of movie that will be played
     * @return Return if screening room is busy at this time
     * @throws ParseException Threw when error in parsing date/time
     */
    public boolean isBusyAt(String date, String time, int movieDuration) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for(Screening s : listOfScreenings) {
            Date sDate = s.getScreeningDateTime();
            if(date.equals(dateFormat.format(sDate))) {
                Date sStartTime = timeFormat.parse(timeFormat.format(s.getScreeningDateTime()));
                Date sEndTime = new Date(sStartTime.getTime() + (60000L * s.getMovieOnScreening().getDuration()));
                Date addingStartTime = timeFormat.parse(time);
                Date addingEndTime = new Date(addingStartTime.getTime() + (60000L * movieDuration));

                if(addingStartTime.compareTo(sEndTime) < 0 && addingEndTime.compareTo(sStartTime) > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /***
     * Get screening at given date
     * @param date Date on which we want to get screenings
     * @return Screenings at given date
     */
    public List<Screening> getScreeningsAtDate(String date) {
        List<Screening> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        for(Screening s : listOfScreenings) {
            Date sDate = s.getScreeningDateTime();
            if(date.equals(dateFormat.format(sDate))) {
                result.add(s);
            }
        }

        return result;
    }

    public void listSpeakers(){
        int i = 1;
        System.out.println("Speakers in screening room \"" + roomNumber + "\":");
        for(Speaker s : speakers){
            System.out.println("    " + i + ". Speaker power - " + s.getPower() + "W");
            i++;
        }
    }

    /***
     * Adds given employee to operate on screening room
     * @param operator Employee that will operate on this screening room
     * @throws WrongPersonTypeException Threw when given person is not an employee
     */
    public void addOperatedBy(Person operator) throws WrongPersonTypeException {
        if(operator == null) {
            throw new NullPointerException("Given person room is null");
        }
        if(!operator.getPersonTypes().contains(EnumPersonType.EMPLOYEE)) {
            throw new WrongPersonTypeException("Given person is not an employee");
        }
        if(!operatedBy.contains(operator)) {
            operatedBy.add(operator);
            operator.addScreeningRoomToOperate(this);
        }
    }

    public int getScreeningCount(){
        return listOfScreenings.size();
    }

    @Override
    public String toString() {
        return "ScreeningRoom  info { Number- " + roomNumber + ", Seats count- " + seatsCount +
                ", Type- " + type + "}";
    }
}