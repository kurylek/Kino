package Models;

import Enums.EnumScreeningRoomType;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScreeningRoom {
    private int roomNumber;
    private int seatsCount;
    private EnumScreeningRoomType type;

    private List<Speaker> speakers;
    public static Set<Speaker> allSpeakers = new HashSet<>();
}