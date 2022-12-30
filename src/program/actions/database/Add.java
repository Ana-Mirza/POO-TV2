package program.actions.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import program.actions.Action;
import program.actions.Feature;
import program.actions.output.ErrorOutput;
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
import program.util.dependencies.Notification;

public final class Add extends Feature implements Action {
    private final Movie movie;
    private ObjectNode node;

    /**
     * Initializes type of feature and page on which
     * feature is applied.
     *
     * @param input stores input data of action
     */
    public Add(final ActionsInput input) {
        super(input);
        movie = new Movie(input.getAddedMovie());
    }

    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    @Override
    public void visit(final SeeDetails page) {
        ErrorOutput.set(node);
    }

    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* check if movie is already in database */
        if (movieExists(data, movie)) {
            data.getCurrentPage().accept(this);
            output.add(node);
            return;
        }

        /* add movie in database */
        data.getMoviesData().add(movie);

        /* notify users */
        data.setChangedData();
        data.notifyObservers(new Notification(movie.getName(), "ADD"));

        /* update current user's list of movies */
        updateUser(data, movie);
    }

    /**
     * Method verifies if movie is already in database
     * @param data stores information about program
     * @param addedMovie contains movie added
     * @return true if movie is already in database, false
     *          if movie is new
     */
    private boolean movieExists(final Database data, final Movie addedMovie) {
        for (Movie movie: data.getMoviesData()) {
            if (movie.getName().equals(addedMovie.getName())) {
                return true;
            }
        }
        return false;
    }

    private void updateUser(final Database database, final Movie movie) {
        if (database.getCurrentUser() != null
                && !movie.getCountriesBanned().contains(
                        database.getCurrentUser().getCredentials().getCountry())) {
            database.getUserMovies().add(movie);
        }
    }
}
