package program.pages;

import program.actions.Action;

public final class Register extends Page {
    // constructor
    public Register() {
        super("register");
        super.getActionsPermitted().add("register");
        super.getAccesiblePages().add("register");
    }

    /**
     * Method to accept visitor
     * @param action is action visitor
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
