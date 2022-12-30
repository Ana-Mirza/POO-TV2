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
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.pages.Register;
import program.pages.PageFactory;
import program.pages.Page;
import program.util.Database;
import program.util.Movie;
import program.util.User;
import program.util.dependencies.Credentials;

import java.util.ArrayList;

public final class Logins extends Feature implements Action {
    private final Credentials credentials;
    private Database database;
    private ObjectNode node;

    // constructor
    public Logins(final ActionsInput input) {
        super(input);
        credentials = new Credentials(input.getCredentials());
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Only page where login action is allowed. If credentials
     * introduced are correct, page changes to authenticated homepage,
     * else it returns to unauthenticated homepage.
     * @param page page to be visited
     */
    @Override
    public void visit(final Login page) {
        int index = nameDoesNotExists(credentials, database.getUsersData());

        // check if name exists in database
        if (index < 0) {
            ErrorOutput.set(node);
            database.setCurrentPage(PageFactory.createPage(
                    "homepage neautentificat", database));
            return;
        }

        // set user logged and current page
        database.setCurrentUser(database.getUsersData().get(index));
        database.setCurrentPage(PageFactory.createPage(
                "homepage autentificat", database));
        database.getCurrentPage().setCurrentUser(database.getCurrentUser());

        // set user movie list by removing banned movies
        database.setUserMovies(new ArrayList<>(database.getMoviesData()));
        database.getUserMovies().removeIf(
                (movie) -> movieBanned(database.getCurrentPage(), movie));

        // set output
        StandardOutput.set(node, database.getCurrentPage());
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves an error as ouput. Login action is not
     * permitted on this page.
     * @param page page to be visited
     */
    @Override
    public void visit(final SeeDetails page) {
        ErrorOutput.set(node);
    }

    /**
     * Method calls visit method of action and stores output
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        // visit page
        this.database = data;
        data.getCurrentPage().accept(this);
        output.add(node);
    }

    /**
     * Method checks if given credentials of user are in database;
     * that is, if name is present in database and password is correct.
     * @param credentials are user info checked
     * @param users contains list of user in database
     * @return true if credentials are incorrect
     */
    private int nameDoesNotExists(final Credentials credentials, final ArrayList<User> users) {
        // check if name exists in database
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            // return index of user in list of users
            if (user.getCredentials().getName().equals(credentials.getName())
                    && user.getCredentials().getPassword().equals(credentials.getPassword())) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Method checks if a movie is banned from current user
     * @param page is current page user is on
     * @param movie movie tested
     * @return true if movie is banned and false if not
     */
    private boolean movieBanned(final Page page, final Movie movie) {
        return movie.getCountriesBanned().contains(
                page.getCurrentUser().getCredentials().getCountry());
    }
}
