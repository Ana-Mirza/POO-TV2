package program.actions.onpage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import program.actions.Action;
import program.actions.output.ErrorOutput;
import program.pages.LoggedHomepage;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.Logout;
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.pages.Register;
import program.util.Database;
import program.util.Movie;

public final class Subscribe implements Action {
    private ObjectNode node;
    private final String genre;
    private boolean display;

    /* constructor */
    public Subscribe(final ActionsInput input) {
        genre = input.getSubscribedGenre();
        display = true;
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method subscribes user to a genre of movies
     * @param page stores page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        Movie movie = page.getUserMovies().get(0);

        /*
           Check if genre is in list of genres for current movie
           and if user is not already subscribed to it
         */
        if (!movie.getGenres().contains(genre)
                || page.getCurrentUser().getSubscriptions().contains(genre)) {
            ErrorOutput.set(node);
            display = true;
            return;
        }

        /* add genre in list of subscriptions */
        page.getCurrentUser().getSubscriptions().add(genre);
        display = false;
    }

    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* visit page */
        data.getCurrentPage().accept(this);

        /* save output if necessary */
        if (display) {
            output.add(node);
        }
    }
}
