package Models;

import Enums.EnumMovieCategory;
import ObjectPlus.ObjectPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Movie extends ObjectPlus {
    private String title;
    private int duration; //in minutes
    private List<EnumMovieCategory> categories;
    private Date licensePurchaseDate;
    private int playsInCurrentYear;
    private int minViewerAge; // -> ENUM PEGI? | int cant be null!

    private Map<String, Screening> playedOn;

    public Movie(String title, int duration, EnumMovieCategory category, String licensePurchaseDate, int minViewerAge) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.title = title;
        this.duration = duration;
        this.categories = new ArrayList<>();
        this.categories.add(category);
        this.licensePurchaseDate = dateFormat.parse(licensePurchaseDate);
        this.playsInCurrentYear = 0;
        this.minViewerAge = minViewerAge;
        this.playedOn = new TreeMap<>();
    }

    public Movie(String title, int duration, EnumMovieCategory category, String licensePurchaseDate) throws ParseException {
        this(title, duration, category, licensePurchaseDate, 0); //int cant be null!
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public void addPlayedOn(Screening screening) {
        if(!playedOn.containsKey(screening.getScreeningNumber())){
            playedOn.put(screening.getScreeningNumber(), screening);


        }
    }

    public void listPlatedOn(){
        System.out.println("This movie was played on:");
        for(Screening s : playedOn.values()) {
            System.out.println(s);
        }
    }

    @Override
    public String toString() {
        return "Movie info {Title- " + title + ", Duration- " + duration + "min, Categories- " + categories +
                ", License Purchase Date=" + new SimpleDateFormat("dd-MM-yyyy").format(licensePurchaseDate) +
                ", Played " + playsInCurrentYear + "times this year, Min. viewer age=" + minViewerAge + "}";
    }
}