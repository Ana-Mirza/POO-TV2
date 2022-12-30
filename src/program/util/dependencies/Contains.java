package program.util.dependencies;

import fileio.ContainsInput;

import java.util.ArrayList;

public final class Contains {
    private ArrayList<String> actors;
    private ArrayList<String> genres;

    // constructor
    public Contains(final ContainsInput input) {
        if (input.getActors() != null) {
            actors = new ArrayList<>(input.getActors());
        }

        if (input.getGenre() != null) {
            genres = new ArrayList<>(input.getGenre());
        }
    }

    // getters and setters
    public ArrayList<String> getActors() {
        return actors;
    }

    public void setActors(final ArrayList<String> actors) {
        this.actors = actors;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(final ArrayList<String> genres) {
        this.genres = genres;
    }
}
