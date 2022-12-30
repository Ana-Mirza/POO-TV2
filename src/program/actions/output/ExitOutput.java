package program.actions.output;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import program.util.Database;

public class ExitOutput extends OutputStrategy {

    /**
     * Method sets output containing recommendation if
     * there is a premium user logged
     * @param database saves current status of program
     * @param output saves output to be displayed
     */
    public static void set(final Database database, final ArrayNode output) {
        if (database.getCurrentUser() != null
            && database.getCurrentUser().isPremium()) {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode node = mapper.createObjectNode();

            node.set("error", null);
            node.set("currentMoviesList", null);
            node.set("currentUser", mapper.valueToTree(database.getCurrentUser()));
            output.add(node);
        }
    }
}
