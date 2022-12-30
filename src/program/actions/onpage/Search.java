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
import program.pages.Logout;
import program.pages.Login;
import program.pages.Register;
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.util.Database;

import java.util.ArrayList;

public final class Search extends Feature implements Action {
    private final String startsWith;
    private Database database;
    private ObjectNode node;

    /* constructor */
    public Search(final ActionsInput input) {
        super(input);
        startsWith = input.getStartsWith();
    }

    /**
     * Mehtod stores error in output. Search action is not
     * permitted on this page.
     * @param page stores Logged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Mehtod stores error in output. Search action is not
     * permitted on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Search action is not
     * permitted on this page.
     * @param page stores Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Search action is not
     * permitted on this page.
     * @param page stores Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method uploads movies available to user from database
     * and saves in output to display.
     * @param page stores page visited
     */
    @Override
    public void visit(final Movies page) {
        /* set user movies */
        page.setUserMovies(new ArrayList<>(database.getUserMovies()));
        page.getUserMovies().removeIf((movie) -> !movie.getName().startsWith(startsWith));

        /* set output */
        StandardOutput.set(node, page);
    }

    /**
     * Method stores error in output. Search action is not
     * permitted on this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Search action is not
     * permitted on this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method stores error in output. Search action is not
     * permitted on this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        ErrorOutput.set(node);
    }

    /**
     * Method calls visit method of current page and
     * saves output to display.
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* visit page and save output */
        this.database = data;
        data.getCurrentPage().accept(this);
        output.add(node);
    }
}
