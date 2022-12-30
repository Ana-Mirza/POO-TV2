package program.pages;

import program.actions.Action;
import program.util.Database;

public final class LoggedHomepage extends Page {
    // constructor
    public LoggedHomepage(final Database data) {
        super("homepage autentificat", data.getCurrentUser());
        super.getAccesiblePages().add("movies");
        super.getAccesiblePages().add("upgrades");
        super.getAccesiblePages().add("logout");
        super.getAccesiblePages().add("homepage autentificat");
    }

    /**
     * Accept method for action visitor
     * @param action is visitor of page
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
