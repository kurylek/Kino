package Models;

import Exceptions.NullWholeException;
import Exceptions.PartConnectedException;
import ObjectPlus.ObjectPlus;

public class Speaker extends ObjectPlus {
    private float power; //in watt

    private ScreeningRoom screeningRoom;

    //Private constructor!
    private Speaker(float power, ScreeningRoom screeningRoom) {
        this.power = power;
        this.screeningRoom = screeningRoom;
    }

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
