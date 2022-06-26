package models;

import enums.EnumMovieCategory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MovieTimedLicense extends Movie {
    private int licenseDuration; //in days

    public MovieTimedLicense(String title, int duration, EnumMovieCategory category, String licensePurchaseDate, int minViewerAge, int licenseDuration) throws ParseException {
        super(title, duration, category, licensePurchaseDate, minViewerAge);
        this.licenseDuration = licenseDuration;
    }

    public MovieTimedLicense(String title, int duration, EnumMovieCategory category, String licensePurchaseDate, int licenseDuration) throws ParseException {
        super(title, duration, category, licensePurchaseDate);
        this.licenseDuration = licenseDuration;
    }

    public int getLicenseDuration() {
        return licenseDuration;
    }

    @Override
    public int getMyAttribute() {
        return getLicenseDuration();
    }

    @Override
    public String toString() {
        String result = "Movie info {Title- " + super.getTitle() + ", Duration- " + super.getDuration() + "min, Categories- " + super.getCategories() +
                ", License Purchase Date=" + new SimpleDateFormat("dd-MM-yyyy").format(super.getLicensePurchaseDate()) +
                ", Played " + super.getPlaysInCurrentYear() + "times this year";
        if(super.getMinViewerAge() > 0) {
            result += ", Min. viewer age=" + super.getMinViewerAge();
        }
        result += ", License duration " + licenseDuration + " days}";
        return result;
    }
}