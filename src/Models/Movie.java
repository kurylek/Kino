package Models;

import Enums.EnumMovieCategory;
import ObjectPlus.ObjectPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Movie extends ObjectPlus {
    private String title;
    private int duration; //in minutes
    private List<EnumMovieCategory> categories;
    private Date licensePurchaseDate;
    private int playsInCurrentYear;
    private int minViewerAge; // -> ENUM PEGI? | int cant be null!

    public Movie(String title, int duration, EnumMovieCategory category, String licensePurchaseDate, int minViewerAge) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.title = title;
        this.duration = duration;
        this.categories = new ArrayList<>();
        this.categories.add(category);
        this.licensePurchaseDate = dateFormat.parse(licensePurchaseDate);
        this.playsInCurrentYear = 0;
        this.minViewerAge = minViewerAge;
    }

    public Movie(String title, int duration, EnumMovieCategory category, String licensePurchaseDate) throws ParseException {
        this(title, duration, category, licensePurchaseDate, 0); //int cant be null!
    }
}