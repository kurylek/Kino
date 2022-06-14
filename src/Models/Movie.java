package Models;

import Enums.EnumMovieCategory;

import java.util.Date;
import java.util.List;

public abstract class Movie {
    private String title;
    private int duration; //in minutes
    private List<EnumMovieCategory> caterogies;
    private Date licensePurchaseDate;
    private int playsInCurrentYear;
    private int minViewerAge; // -> ENUM PEGI?
}