package models;

import enums.EnumMovieCategory;
import objectPlus.ObjectPlus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Movie extends ObjectPlus {
    private String title;
    private int duration; //in minutes
    private List<EnumMovieCategory> categories;
    private Date licensePurchaseDate;
    private int playsInCurrentYear;
    private int minViewerAge;

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

    public Date getLicensePurchaseDate() {
        return licensePurchaseDate;
    }

    /***
     * Adds screening to qualified associations, where key is screening number
     * @param screening Screening to add
     */
    public void addPlayedOn(Screening screening) {
        if(!playedOn.containsKey(screening.getScreeningNumber())){
            playedOn.put(screening.getScreeningNumber(), screening);
            playsInCurrentYear++;
        }
    }

    /***
     * Delete movies with timed license, that has expired license
     */
    public static void deleteMoviesWithExpiredLicense() {
        try {
            List<MovieTimedLicense> timedLicenseMovies = (List<MovieTimedLicense>) ObjectPlus.getExtent(MovieTimedLicense.class);
            List<MovieTimedLicense> moviesToRemove = new ArrayList<>();
            for (MovieTimedLicense m : timedLicenseMovies) {
                Date now = new Date();
                Date licenseExpiryDate = new Date(m.getLicensePurchaseDate().getTime() + (86400000L * m.getLicenseDuration())); //86400000 = 1000 * 60 * 60 * 24- one day in msec
                if (now.compareTo(licenseExpiryDate) > 0) { //Check if license is expired
                    moviesToRemove.add(m);
                }
            }
            timedLicenseMovies.removeAll(moviesToRemove); //remove movies with expired license from extension
        } catch (ClassNotFoundException ignore) {}
    }

    /***
     * Generate list of all movies that cinema has
     * @return List of all movies- timed license movies and no time license movies
     */
    public static List<Movie> getAllMovies() {
        List<Movie> allMovies = new ArrayList<>();
        try {
            allMovies.addAll((List<MovieNoTimeLimitLicense>) ObjectPlus.getExtent(MovieNoTimeLimitLicense.class));
        } catch (ClassNotFoundException ignore) {}
        try {
            allMovies.addAll((List<MovieTimedLicense>) ObjectPlus.getExtent(MovieTimedLicense.class));
        } catch (ClassNotFoundException ignore) {}
        return allMovies;
    }

    @Override
    public String toString() {
        return "Movie info {Title- " + title + ", Duration- " + duration + "min, Categories- " + categories +
                ", License Purchase Date=" + new SimpleDateFormat("dd-MM-yyyy").format(licensePurchaseDate) +
                ", Played " + playsInCurrentYear + "times this year, Min. viewer age=" + minViewerAge + "}";
    }
}