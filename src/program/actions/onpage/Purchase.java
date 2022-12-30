package program.actions.onpage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.ActionsInput;
import program.actions.Action;
import program.actions.Feature;
import program.actions.output.ErrorOutput;
import program.actions.output.StandardOutput;
import program.pages.LoggedHomepage;
import program.pages.UnloggedHomepage;
import program.pages.Login;
import program.pages.Logout;
import program.pages.Register;
import program.pages.Movies;
import program.pages.SeeDetails;
import program.pages.Upgrades;
import program.util.Database;
import program.util.Movie;

public final class Purchase extends Feature implements Action {
    private ObjectNode node;

    /* constructor */
    public Purchase(final ActionsInput input) {
        super(input);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Logged Homepage visited
     */
    @Override
    public void visit(final LoggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Unlogged Homepage visited
     */
    @Override
    public void visit(final UnloggedHomepage page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Login page visited
     */
    @Override
    public void visit(final Login page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Logout page visited
     */
    @Override
    public void visit(final Logout page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Movies page visited
     */
    @Override
    public void visit(final Movies page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Register page visited
     */
    @Override
    public void visit(final Register page) {
        ErrorOutput.set(node);
    }

    /**
     * Method saves error in output. Action is not permitted
     * on this page.
     * @param page stores Upgrades page visited
     */
    @Override
    public void visit(final Upgrades page) {
        ErrorOutput.set(node);
    }

    /**
     * Method checks if user has enough tokens to buy movie and
     * displays an error if so. If user has premium it checks if
     * there are free movies left, and if not, if he has enough
     * tokens to buy movie.
     * @param page stores See Details page visited
     */
    @Override
    public void visit(final SeeDetails page) {
        final int moviePrice = 2;

        /* check if user does not have enough tokens */
        if ((!page.getCurrentUser().isPremium()
                && page.getCurrentUser().getTokensCount() < moviePrice)
                || (page.getCurrentUser().getNumFreePremiumMovies() == 0
                && page.getCurrentUser().getTokensCount() < moviePrice)) {
            ErrorOutput.set(node);
            return;
        }

        /* check if movie was previously bought */
        Movie movie = page.getUserMovies().get(0);
        if (page.getCurrentUser().getPurchasedMovies().contains(movie)) {
            ErrorOutput.set(node);
            return;
        }

        /* purchase movie */
        page.getCurrentUser().getPurchasedMovies().add(movie);

        /* update free movies left if user is premium and has free movies */
        if (page.getCurrentUser().isPremium()
                && page.getCurrentUser().getNumFreePremiumMovies() > 0) {
            int currentFreeMovies = page.getCurrentUser().getNumFreePremiumMovies();
            page.getCurrentUser().setNumFreePremiumMovies(currentFreeMovies - 1);
        } else {
            /* update tokens of user */
            int currentTokens = page.getCurrentUser().getTokensCount();
            page.getCurrentUser().setTokensCount(currentTokens - moviePrice);
        }

        /* save output */
        StandardOutput.set(node, page);
    }

    /**
     * Method calls visit method of current page to apply
     * purchase action and saves output to display.
     * @param data stores current status of system
     * @param output stores output to be displayed
     */
    @Override
    public void apply(final Database data, final ArrayNode output) {
        ObjectMapper mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        /* visit page and save output */
        data.getCurrentPage().accept(this);
        output.add(node);
    }
}
