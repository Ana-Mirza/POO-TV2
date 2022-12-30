package program.actions.onpage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import program.actions.Action;
import program.actions.Feature;
import program.actions.output.ErrorOutput;
import program.pages.LoggedHomepage;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.Logout;
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.pages.Register;
import program.util.Database;

public final class BuyPremiumAccount extends Feature implements Action {
    private boolean displayOutput;
    private ObjectNode node;

    // constructor
    public BuyPremiumAccount(final ActionsInput input) {
        super(input);
        displayOutput = false;
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page Logged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page Movies page visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page Regiser page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Checks if user has enough tokens to buy premium account
     * and changes user account to premium if so. If there are
     * not enough tokens, the output is an error.
     * @param page stores Upgrades to be visited
     */
    @Override
    public void visit(final Upgrades page) {
        int currentTokens = page.getCurrentUser().getTokensCount();
        final int premiumPrice = 10;

        // check if user has enough tokens to buy premium account
        if (currentTokens < premiumPrice) {
            ErrorOutput.set(node);
            displayOutput = true;
        }

        // buy premium account
        page.getCurrentUser().setTokensCount(currentTokens - premiumPrice);
        page.getCurrentUser().setPremium(true);
        page.getCurrentUser().getCredentials().setAccountType("premium");
    }

    /**
     * Method saves an error in output. Action is not
     * permitted on this page.
     * @param page See Details visited
     */
    @Override
    public void visit(final SeeDetails page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method calls visit method of current page and saves
     * output to be displayed.
     * @param data stores current status of system
     * @param output stores output to be displayed;
     *               the is if an error occurs
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        // visit page
        data.getCurrentPage().accept(this);
        if (displayOutput) {
            output.add(node);
        }
    }
}
