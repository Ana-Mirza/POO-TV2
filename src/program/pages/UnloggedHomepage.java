package program.pages;

import program.actions.Action;

public final class UnloggedHomepage extends Page {
    // constructor
    public UnloggedHomepage() {
        super("homepage neautentificat");
        super.getAccesiblePages().add("login");
        super.getAccesiblePages().add("register");
        super.getAccesiblePages().add("homepage neautentificat");
    }

    /**
     * Method to accept visitor
     * @param action is action visitor
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
