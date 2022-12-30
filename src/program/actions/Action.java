package program.actions;

import com.fasterxml.jackson.databind.node.ArrayNode;
import program.pages.LoggedHomepage;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.Logout;
import program.pages.Movies;
import program.pages.Register;
import program.pages.Upgrades;
import program.pages.SeeDetails;
import program.util.Database;


public interface Action {
    /**
     * Method visits Logged Homepage and executes action
     *
     * @param page stores page visited
     */
    void visit(LoggedHomepage page);

    /**
     *
     * Method visits Unlogged Homepage and executes action
     *
     * @param page stores page visited
     */
    void visit(UnloggedHomepage page);

    /**
     * Method visits Login page and executes action
     *
     * @param page stores page visited
     */
    void visit(Login page);

    /**
     * Method visits Logout page an dexecutes action
     *
     * @param page stores page visited
     */
    void visit(Logout page);

    /**
     * Method visits Movies page and executes action
     *
     * @param page stores page visited
     */
    void visit(Movies page);

    /**
     * Method visits Register page and executes action
     *
     * @param page stores page visited
     */
    void visit(Register page);

    /**
     * Method visits Upgrades page and executes action
     *
     * @param page stores page visited
     */
    void visit(Upgrades page);
    /**
     * Method visits See Details page and executes action
     *
     * @param page stores page visited
     */
    void visit(SeeDetails page);

    /**
     * Method calls visit method for specific page of current action
     * and stores output to be displayed
     *
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    void apply(Database data, ArrayNode output);
}
