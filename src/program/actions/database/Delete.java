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
import program.util.dependencies.Notification;

public final class Delete extends Feature implements Action {
    private final String deletedMovie;
    private ObjectNode node;

    /**
     * Initializes type of feature and page on which
     * feature is applied.
     *
     * @param input stores input data of action
     */
    public Delete(final ActionsInput input) {
        super(input);
        deletedMovie = input.getDeletedMovie();
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
        int initialSize = data.getMoviesData().size();
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* delete movie with given name from database */
        data.getMoviesData().removeIf(movie -> movie.getName().equals(deletedMovie));

        /* if no movie was deleted, movie does not exist in database */
        int currentSize = data.getMoviesData().size();
        if (initialSize == currentSize) {
            data.getCurrentPage().accept(this);
            output.add(node);
            return;
        }

        /* notify users */
        data.setChangedData();
        data.notifyObservers(new Notification(deletedMovie, "DELETE"));
    }
}
