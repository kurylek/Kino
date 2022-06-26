package models;

import enums.EnumMovieCategory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MovieNoTimeLimitLicense extends Movie {
    private int maxPlaysPerYear;

    public MovieNoTimeLimitLicense(String title, int duration, EnumMovieCategory category, String licensePurchaseDate, int minViewerAge, int maxPlaysPerYear) throws ParseException {
        super(title, duration, category, licensePurchaseDate, minViewerAge);
        this.maxPlaysPerYear = maxPlaysPerYear;
    }

    public MovieNoTimeLimitLicense(String title, int duration, EnumMovieCategory category, String licensePurchaseDate, int maxPlaysPerYear) throws ParseException {
        super(title, duration, category, licensePurchaseDate);
        this.maxPlaysPerYear = maxPlaysPerYear;
    }

    @Override
    public int getMyAttribute() {
        return maxPlaysPerYear;
    }

    @Override
    public String toString() {
        String result = "Movie info {Title- " + super.getTitle() + ", Duration- " + super.getDuration() + "min, Categories- " + super.getCategories() +
                ", License Purchase Date=" + new SimpleDateFormat("dd-MM-yyyy").format(super.getLicensePurchaseDate()) +
                ", Played " + super.getPlaysInCurrentYear() + "times this year";
        if(super.getMinViewerAge() > 0) {
            result += ", Min. viewer age=" + super.getMinViewerAge();
        }
        result += ", Can be played max " + maxPlaysPerYear + " times per year}";
        return result;
    }
}