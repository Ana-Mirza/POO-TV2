package program.pages;

import program.actions.Action;


public final class Login extends Page {
    // constructor
    public Login() {
        super("login");
        super.getActionsPermitted().add("login");
        super.getAccesiblePages().add("login");
    }

    /**
     * Accept method for action visitor
     * @param action is visitor of page
     */
    public void accept(final Action action) {
        action.visit(this);
    }
}
