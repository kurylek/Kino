package Models;

import Enums.EnumMovieCategory;

import java.text.ParseException;

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
}