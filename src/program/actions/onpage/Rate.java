package program.actions.onpage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import program.actions.Action;
import program.actions.Feature;
import program.actions.output.ErrorOutput;
import program.actions.output.StandardOutput;
import program.pages.LoggedHomepage;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.Logout;
import program.pages.Register;
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.util.Database;
import program.util.Movie;

import java.util.ArrayList;

public final class Rate extends Feature implements Action {
    private final int rating;
    private ObjectNode node;

    /* constructor */
    public Rate(final ActionsInput input) {
        super(input);
        rating = input.getRate();
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Logged Homepage page visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Unlogged Homepage page visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Movies page visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Register page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Rating action is not
     * permitted on this page.
     * @param page stores Upgrades page visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method rates a movie if rating is in accepted
     * range (1-5), and if movie was watched before.
     * If conditions are met, movie is stores in rated movie
     * list of user.
     * @param page stores See Details page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        Movie movie = page.getUserMovies().get(0);
        final int minRating = 1;
        final int maxRating = 5;

        /* check if movie was watched and if rating is valid */
        if (!watchedMovie(movie, page.getCurrentUser().getWatchedMovies())
                || rating < minRating || rating > maxRating) {
            ErrorOutput.set(node);
            return;
        }

        /* check if movie was rated before */
        if (!page.getCurrentUser().getRatedMovies().contains(movie)) {
            movie.setRatingSum(movie.getRatingSum() + rating);
            movie.setNumRatings(movie.getNumRatings() + 1);
            movie.setRating((movie.getRatingSum() / (double) movie.getNumRatings()));
            page.getCurrentUser().getRatedMovies().add(movie);

            /* save rating for movie */
            page.getCurrentUser().getRatings().put(movie.getName(), rating);
        } else {
            int oldRating = page.getCurrentUser().getRatings().get(movie.getName());
            page.getCurrentUser().getRatings().put(movie.getName(), rating);

            /* calculate new rating */
            movie.setRatingSum(movie.getRatingSum() - oldRating + rating);
            movie.setRating(movie.getRatingSum() / (double) movie.getNumRatings());
        }

        /* save output */
        StandardOutput.set(node, page);
    }

    /**
     * Method calls visit method of current page and saves
     * output to display.
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* visit page */
        data.getCurrentPage().accept(this);
        output.add(node);
    }

    /**
     * Method checks if movie was watched previously
     * @param movie to be tested
     * @param movies contains list of watched list of current user
     * @return true if movie was watched, and false if not
     */
    private boolean watchedMovie(final Movie movie, final ArrayList<Movie> movies) {
        return movies.contains(movie);
    }
}
