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
import program.util.dataprocessing.ContentStrategy;
import program.util.dataprocessing.SortStrategy;
import program.util.dependencies.Filters;

import java.util.ArrayList;

public final class Filter extends Feature implements Action {
    private final Filters filters;
    private Database database;
    private ObjectNode node;

    /* constructor */
    public Filter(final ActionsInput input) {
        super(input);
        filters = new Filters(input.getFilters());
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores Logged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method filters movie list of user with given filters,
     * by content and through sorting, and stores output
     * @param page stores Movies page visited
     */
    @Override
    public void visit(final Movies page) {
        // set user movies
        if (page.getUserMovies().size() == 0) {
            page.setUserMovies(new ArrayList<>(database.getUserMovies()));
        }

        /* filter by input */
        page.filter(new ContentStrategy(), filters);

        /* sort by rating */
        page.filter(new SortStrategy(), filters);

        /* set output node */
        StandardOutput.set(node, page);
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores Registe page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores Upgrades page visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as output. Like action is not
     * permitted on this page.
     * @param page stores See Details page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        ErrorOutput.set(node);
    }

    /**
     * Method calls visit method of current page to apply filter
     * and saves output: error if current page is not permitted
     * to filter, or list of movies resulted after filtering.
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* visit page and save output for display */
        this.database = data;
        data.getCurrentPage().accept(this);
        output.add(node);
    }
}
