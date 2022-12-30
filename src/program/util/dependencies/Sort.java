package program.util.dependencies;

import fileio.SortInput;

public final class Sort {
    private String rating;
    private String duration;

    // constructor
    public Sort(final SortInput input) {
        rating = input.getRating();
        duration = input.getDuration();
    }

    // getters and setters
    public String getRating() {
        return rating;
    }

    public void setRating(final String rating) {
        this.rating = rating;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(final String duration) {
        this.duration = duration;
    }
}
