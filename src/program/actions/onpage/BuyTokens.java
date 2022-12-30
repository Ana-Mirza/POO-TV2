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
import program.pages.Logout;
import program.pages.Login;
import program.pages.Register;
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.util.Database;

public final class BuyTokens extends Feature implements Action {
    private final int count;
    private boolean displayOutput;
    private ObjectNode node;

    // constructor
    public BuyTokens(final ActionsInput input) {
        super(input);
        displayOutput = false;
        count = Integer.parseInt(input.getCount());
    }

    /**
     * Method saves and error in output. Action is
     * not permitted on this page.
     * @param page Logged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves and error in output. Action is
     * not permitted on this page.
     * @param page Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is
     * not permitted on this page
     * @param page Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is
     * not permitted on this page
     * @param page Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is
     * not permitted on this page
     * @param page Movies page visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method saves an error in output. Action is
     * not permitted on this page
     * @param page Register page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
        displayOutput = true;
    }

    /**
     * Method checks if user has enough 'balance' to
     * buy tokens. The proportion between the two is 1:1
     * (1 token = 1 balance);
     * @param page Upgrades page visited
     */
    @Override
    public void visit(final Upgrades page) {
        int totalBalance = Integer.parseInt(
                page.getCurrentUser().getCredentials().getBalance());

        // check balance
        if (totalBalance < count) {
            ErrorOutput.set(node);
            displayOutput = true;
            return;
        }

        // update balance
        totalBalance -= count;
        page.getCurrentUser().getCredentials().setBalance(String.valueOf(totalBalance));

        // update tokens
        int currentTokens = page.getCurrentUser().getTokensCount();
        page.getCurrentUser().setTokensCount(currentTokens + count);
    }

    /**
     * Method saves an error in output. Action is
     * not permitted on this page
     * @param page See Details page visited
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
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        // visit page and display output
        data.getCurrentPage().accept(this);
        if (displayOutput) {
            output.add(node);
        }
    }
}
