package Models;

import Enums.EnumScreeningRoomType;
import Exceptions.PartConnectedException;
import ObjectPlus.ObjectPlus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScreeningRoom extends ObjectPlus {
    private int roomNumber;
    private int seatsCount;
    private EnumScreeningRoomType type;

    private List<Speaker> speakers;
    public static Set<Speaker> allSpeakers = new HashSet<>();

    public ScreeningRoom(int roomNumber, int seatsCount, EnumScreeningRoomType type) {
        this.roomNumber = roomNumber;
        this.seatsCount = seatsCount;
        this.type = type;
        this.speakers = new ArrayList<>();
    }

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

    public void listSpeakers(){
        int i = 1;
        System.out.println("Speakers in screening room \"" + roomNumber + "\":");
        for(Speaker s : speakers){
            System.out.println("    " + i + ". Speaker power - " + s.getPower() + "W");
            i++;
        }
    }
}