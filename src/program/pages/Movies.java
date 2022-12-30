package program.pages;

import program.actions.Action;
import program.util.Database;
import program.util.dataprocessing.FilterStrategy;
import program.util.dependencies.Filters;

public final class Movies extends Page {
    // constructor
    public Movies(final Database data) {
        super("movies", data.getCurrentUser(), data.getUserMovies());
        super.getAccesiblePages().add("homepage autentificat");
        super.getAccesiblePages().add("see details");
        super.getAccesiblePages().add("logout");
        super.getAccesiblePages().add("movies");
        super.getActionsPermitted().add("search");
        super.getActionsPermitted().add("filter");
    }

    /**
     * Accept method for action visitor
     * @param action is visitor of page
     */
    public void accept(final Action action) {
        action.visit(this);
    }

    /**
     * Method of applying filter strategy on movie list of
     * current user.
     * @param filterStrategy is instance of specialized filter
     *                       strategy
     * @param filter containes input about how the filter should
     *               be done
     */
    public void filter(final FilterStrategy filterStrategy, final Filters filter) {
        filterStrategy.filter(filter, this);
    }
}
