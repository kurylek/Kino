package Models;

import java.util.Date;

public abstract class Movie {
    private String title;
    private int duration; //in minutes
    //kategoria
    private Date licensePurchaseDate;
    private int playsInCurrentYear;
    private int minViewerAge; // -> ENUM PEGI?
}