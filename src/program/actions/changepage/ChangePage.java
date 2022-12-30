package program.actions.changepage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import program.actions.Action;
import program.actions.output.ErrorOutput;
import program.actions.output.StandardOutput;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.LoggedHomepage;
import program.pages.Logout;
import program.pages.Register;
import program.pages.Movies;
import program.pages.Upgrades;
import program.pages.SeeDetails;
import program.pages.PageFactory;
import program.pages.Page;
import program.util.Database;

import java.util.ArrayList;
import java.util.LinkedList;

public final class ChangePage implements Action {
    private final String page;
    private final String movie;
    private boolean displayOutput;
    private Database database;
    private ObjectNode node;

    /* constructors */
    public ChangePage(final ActionsInput input) {
        page = input.getPage();
        movie = input.getMovie();
        displayOutput = false;
    }

    @Override
    public void visit(final LoggedHomepage page) {
        StandardOutput.set(node, page);
    }

    @Override
    public void visit(final UnloggedHomepage page) {
        StandardOutput.set(node, page);
    }

    @Override
    public void visit(final Login page) {
        StandardOutput.set(node, page);
    }

    /**
     * Changes current page to unauthenticated homepage
     * @param page page to be visited
     */
    @Override
    public void visit(final Logout page) {
        /* update current page */
        database.setCurrentPage(PageFactory.createPage(
                "homepage neautentificat", database));
        database.setUserMovies(new ArrayList<>());
        database.setCurrentUser(null);

        /* empty stack of previous pages */
        database.setPrevPages(new LinkedList<>());
    }

    /**
     * Displayes all movies available to current user
     * @param page page to ve visited
     */
    @Override
    public void visit(final Movies page) {
        // set output
        StandardOutput.set(node, page);
        displayOutput = true;
    }

    @Override
    public void visit(final Register page) {
        StandardOutput.set(node, page);
    }

    @Override
    public void visit(final Upgrades page) {
        StandardOutput.set(node, page);
    }

    /**
     * Displays output of a selected movie or
     * nothing if the movie was not found
     * @param page page to be visited
     */
    @Override
    public void visit(final SeeDetails page) {
        /* set movie available */
        page.getUserMovies().removeIf((movie) -> !movie.getName().equals(this.movie));
        displayOutput = true;

        /* check if movie was found */
        if (page.getUserMovies().size() == 0) {
            /* return to movies page if movie was not found */
            database.setCurrentPage(PageFactory.createPage("movies", database));
            /* set error output */
            ErrorOutput.set(node);
            database.getPrevPages().pop();
            return;
        }

        /* set output for movie */
        StandardOutput.set(node, page);
    }

    /**
     * Method checks if requested page is accesible and
     * changes to requested page if available
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* check if page is available */
        if (pageNotAvailable(data)) {
            ErrorOutput.set(node);
            output.add(node);
            return;
        }

        /* save previous page if there is a user logged */
        if (data.getCurrentUser() != null) {
            data.getPrevPages().push(data.getCurrentPage());
        }

        /* set new current page */
        data.setCurrentPage(PageFactory.createPage(page, data));

        /* update current page */
        this.database = data;
        data.getCurrentPage().accept(this);
        if (displayOutput) {
            output.add(node);
        }
    }

    /**
     * @param data containes current status of system
     * @return true if page can not be reached and false
     * if page can be reached from current page
     */
    private boolean pageNotAvailable(final Database data) {
        Page currentPage = data.getCurrentPage();

        /* check available pages */
        return !currentPage.getAccesiblePages().contains(page);
    }
}
