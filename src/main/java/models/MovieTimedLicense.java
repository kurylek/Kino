package models;

import enums.EnumMovieCategory;

import java.text.ParseException;

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
}