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
import program.pages.PageFactory;
import program.util.Database;
import program.util.Movie;
import program.util.User;
import program.util.dependencies.Credentials;
import java.util.ArrayList;

public final  class Registers extends Feature implements Action {
    private final Credentials credentials;
    private Database database;
    private ObjectNode node;

    /* constructor */
    public Registers(final ActionsInput input) {
        super(input);
        this.credentials = new Credentials(input.getCredentials());
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores Logged Homepage  visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores Unlogged Homepage  visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores Login page  visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores Logout page  visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores Movies page  visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    /**
     * Method checks if credentials given are already
     * in the database, and if so, stores an error in output,
     * else if registers new user and saves him in the database.
     * @param page stores Register page visited
     */
    @Override
    public void visit(final Register page) {
        /* check if name is already in database */
        if (nameAlreadyExists(credentials, database.getUsersData())) {
            ErrorOutput.set(node);
            database.setCurrentPage(PageFactory.createPage(
                    "homepage neautentificat", database));
            return;
        }

        /* save new user in database */
        database.getUsersData().add(new User(credentials));
        int size = database.getUsersData().size();
        database.setCurrentUser(database.getUsersData().get(size - 1));
        database.getCurrentPage().setCurrentUser(database.getCurrentUser());

        /* set new page */
        database.setCurrentPage(PageFactory.createPage(
                "homepage autentificat", database));

        /* set user movie list by removing banned movies */
        database.setUserMovies(new ArrayList<>(database.getMoviesData()));
        database.getUserMovies().removeIf(
                (movie) -> movieBanned(database.getCurrentUser(), movie));

        /* set output */
        StandardOutput.set(node, database.getCurrentPage());
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores Upgrades page  visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Register action is not
     * permitted on this page.
     * @param page stores See Details page  visited
     */
    @Override
    public void visit(final SeeDetails page) {
        ErrorOutput.set(node);
    }

    /**
     * Method calls visit method of current page and
     * stores output to display.
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* visit page */
        this.database = data;
        data.getCurrentPage().accept(this);
        output.add(node);
    }

    /**
     * Method checks if user credentials are already in the
     * database.
     * @param credentials
     * @param users
     * @return
     */
    private boolean nameAlreadyExists(final Credentials credentials,
                                      final ArrayList<User> users) {
        /* check if name already exists in database */
        for (User user: users) {
            if (user.getCredentials().getName().equals(credentials.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method checks if movie is banned from user
     * @param user stores data about current user
     * @param movie containes informatin about movie to
     *              be tested
     * @return
     */
    private boolean movieBanned(final User user, final Movie movie) {
        return movie.getCountriesBanned().contains(
                user.getCredentials().getCountry());
    }
}
