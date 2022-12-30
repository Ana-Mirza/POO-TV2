package program.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.MoviesInput;

import java.util.ArrayList;

public final class Movie {
    private String name;
    private String year;
    private int duration;
    private ArrayList<String> genres;
    private ArrayList<String> actors;
    private ArrayList<String> countriesBanned;
    private int numLikes;
    private double rating;
    @JsonIgnore
    private int ratingSum;
    private int numRatings;

    // constructor
    public Movie(final MoviesInput input) {
        name = input.getName();
        year = String.valueOf(input.getYear());
        duration = input.getDuration();
        genres = input.getGenres();
        actors = input.getActors();
        countriesBanned = input.getCountriesBanned();
        rating = 0.00;
        numRatings = 0;
        numLikes = 0;
        ratingSum = 0;
    }

    // getters and setters
    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(final String year) {
        this.year = year;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }

    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getCountriesBanned() {
        return countriesBanned;
    }

    public void setCountriesBanned(final ArrayList<String> countriesBanned) {
        this.countriesBanned = countriesBanned;
    }

    public int getRatingSum() {
        return ratingSum;
    }

    public void setRatingSum(final int ratingSum) {
        this.ratingSum = ratingSum;
    }

    public int getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(final int numberRatings) {
        this.numRatings = numberRatings;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(final int numLikes) {
        this.numLikes = numLikes;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }
}
