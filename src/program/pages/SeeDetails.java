package program.pages;

import program.actions.Action;
import program.util.Database;

public final class SeeDetails extends Page {
    public SeeDetails(final Database data) {
        super("see details", data.getCurrentUser(),
                data.getCurrentPage().getUserMovies());
        super.getAccesiblePages().add("homepage autentificat");
        super.getAccesiblePages().add("movies");
        super.getAccesiblePages().add("upgrades");
        super.getAccesiblePages().add("logout");
        super.getAccesiblePages().add("see details");
    }

    /**
     * Method to accept visitor
     * @param action is action visitor
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
