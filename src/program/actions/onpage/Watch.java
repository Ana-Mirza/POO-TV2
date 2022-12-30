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

public final class Watch extends Feature implements Action {
    private ObjectNode node;
    private boolean display;
    public Watch(final ActionsInput input) {
        super(input);
        display = true;
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Logged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Movies page visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Register page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Watch action is not
     * permitted on this page.
     * @param page stores Upgrades page visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method checks if movie was purchased by current user
     * and if so, it is watched and added to watched movie list
     * of user. If movie was not bought, an error will be saved
     * in output.
     * @param page stores See Details page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        // check if purchased movie
        Movie movie = page.getUserMovies().get(0);
        if (!purchasedMovie(movie, page.getCurrentUser().getPurchasedMovies())) {
            ErrorOutput.set(node);
            return;
        }

        // add movie in list of watched movies if not watched
        if (page.getCurrentUser().getWatchedMovies().contains(movie)) {
            display = false;
            return;
        }
        page.getCurrentUser().getWatchedMovies().add(movie);

        // set output
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

        /* visit page and save output */
        data.getCurrentPage().accept(this);
        if (display) {
            output.add(node);
        }
    }

    /**
     * Method checks if movie was purchased by current user
     * @param movie ckecked if bought by user
     * @param movies contains list of purchased movies of user
     * @return true if movie was bought and false if not
     */
    private boolean purchasedMovie(final Movie movie,
                                   final ArrayList<Movie> movies) {
        return movies.contains(movie);
    }
}
