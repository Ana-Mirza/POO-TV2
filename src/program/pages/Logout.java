package program.pages;

import program.actions.Action;

public final class Logout extends Page {
    // constructor
    public Logout() {
        super("logout");
    }

    /**
     * Accept method for action visitor
     * @param action is visitor of page
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
