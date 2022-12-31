package program.actions.back;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import program.actions.Action;
import program.actions.output.ErrorOutput;
import program.actions.output.StandardOutput;
import program.pages.LoggedHomepage;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.Register;
import program.pages.Movies;
import program.pages.Logout;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.util.Database;

public final class Back implements Action {
    private ObjectNode node;
    private boolean display;

    /* constructor */
    public Back() {
        display = true;
    }

    /**
     * Method saves output
     * @param page stores page visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        StandardOutput.set(node, page);
        display = false;
    }

    /**
     * Method saves error as output. The user can not
     * go back to this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error as output. The user can not
     * go back to this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error as output. The user can not
     * go back to this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves output
     * @param page stores page visited
     */
    @Override
    public void visit(final Movies page) {
        StandardOutput.set(node, page);
    }

    /**
     * Method saves error as output. The user can not
     * go back to this page.
     * @param page stores page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves output
     * @param page stores page visited
     */
    @Override
    public void visit(final Upgrades page) {
        StandardOutput.set(node, page);
        display = false;
    }

    /**
     * Method saves output
     * @param page stores page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        StandardOutput.set(node, page);
    }

    /**
     * Method checks for errors and if available
     * returns to previous page visited.
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* check for errors */
        if (error(data)) {
            ErrorOutput.set(node);
            output.add(node);
            return;
        }

        /* update page and visit it */
        data.setCurrentPage(data.getPrevPages().pop());
        data.getCurrentPage().accept(this);

        /* save output for display */
        if (display) {
            output.add(node);
        }
    }

    /**
     * Method checks if there are pages to go back to
     * and if there is any user logged.
     * @param database contains current status of program
     * @return true if there is an error and false if not
     */
    private boolean error(final Database database) {
        /* check if there is a user logged and if
        * there are pages in stack to go back to
        * */
        return database.getCurrentUser() == null
                || database.getPrevPages().isEmpty();
    }
}
