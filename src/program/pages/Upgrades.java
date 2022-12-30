package program.pages;

import program.actions.Action;
import program.util.Database;


public final class Upgrades extends Page {

    // constructor
    public Upgrades(final Database data) {
        super("upgrades", data.getCurrentUser());
        if (data.getCurrentPage().getName().equals("homepage autentificat")) {
            super.getAccesiblePages().add("homepage autentificat");
            super.getAccesiblePages().add("movies");
            super.getAccesiblePages().add("logout");
            super.getActionsPermitted().add("buy premium account");
            super.getActionsPermitted().add("buy tokens");
        } else if (data.getCurrentPage().getName().equals("see details")) {
            super.getActionsPermitted().add("purchase");
            super.getActionsPermitted().add("watch");
            super.getActionsPermitted().add("like");
            super.getAccesiblePages().add("rate the movie");
            super.getAccesiblePages().add("upgrades");
        }
    }

    /**
     * Method to accept visitor
     * @param action is action visitor
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
