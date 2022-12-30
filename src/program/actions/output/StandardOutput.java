package program.actions.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import program.pages.Page;

public class StandardOutput extends OutputStrategy {
    /**
     * Method initializes an ObjectNode with standard
     * output for successful operations.
     * @param node saves output to be displayed
     * @param page stores page from which output is taken
     */
    public static void set(final ObjectNode node, final Page page) {
        ObjectMapper mapper = new ObjectMapper();

        // set standard output
        node.set("error", null);
        node.set("currentMoviesList", mapper.valueToTree(page.getUserMovies()));
        node.set("currentUser", mapper.valueToTree(page.getCurrentUser()));
    }
}
