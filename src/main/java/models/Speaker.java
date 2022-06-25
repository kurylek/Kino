package models;

import exceptions.NullWholeException;
import exceptions.PartConnectedException;
import objectPlus.ObjectPlus;

public class Speaker extends ObjectPlus {
    private float power; //in watt

    private ScreeningRoom screeningRoom;

    /***
     * Private constructor required because of composition associations
     * @param power Power of speaker in Watt
     * @param screeningRoom Screening room, that will contain speaker
     */
    private Speaker(float power, ScreeningRoom screeningRoom) {
        this.power = power;
        this.screeningRoom = screeningRoom;
    }

    /***
     * Crate speaker with given power, and adds it to given screening room.
     * @param power Power of speaker in Watt
     * @param screeningRoom Screening room, that will contain speaker
     * @return Return created speaker
     * @throws NullWholeException Threw when screening room is null
     * @throws PartConnectedException Thew when speaker is connected with other screening room
     */
    public static Speaker createSpeaker(float power, ScreeningRoom screeningRoom) throws NullWholeException, PartConnectedException {
        if (screeningRoom == null){
            throw new NullWholeException("Given screening room do not exist!");
        }

        //Crate new speaker
        Speaker speaker = new Speaker(power, screeningRoom);

        //Add speaker to ScreeningRoom
        screeningRoom.addSpeaker(speaker);

        return speaker;
    }

    public float getPower() {
        return power;
    }

    @Override
    public String toString() {
        return power + "W speaker from screening room- " + screeningRoom.getRoomNumber();
    }
}
