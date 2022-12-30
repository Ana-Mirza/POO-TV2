package program;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Input;
import program.actions.Action;
import program.actions.ActionFactory;
import program.actions.database.observers.AddNotification;
import program.actions.database.observers.DeleteNotification;
import program.actions.database.observers.Recommendation;
import program.actions.output.ExitOutput;
import program.util.Database;
import program.util.dependencies.Notification;

public final class Program {
    /* Singleton Pattern */
    private static Program instance = null;
    private Program() { }

    /**
     * Instantiates program class using Singleton Pattern
     *
     * @return returns unique instance of program class
     */
    public static Program getInstance() {
        if (instance == null) {
            instance = new Program();
        }
        return instance;
    }

    /**
     * Entry point of the program. It runs the actions specified
     * by user
     *
     * @param inputData stores input data from input file
     * @param output stores output of actions
     */
    public void start(final Input inputData, final ArrayNode output) {
        Database data = new Database(inputData);

        /* add notification observers */
        data.addObserver(new AddNotification());
        data.addObserver(new DeleteNotification());
        data.addObserver(new Recommendation());

        /* parse actions */
        for (ActionsInput action: inputData.getActions()) {
            Action myAction = ActionFactory.createAction(action);
            assert myAction != null;
            myAction.apply(data, output);
        }

        /* send recommendations */
        data.setChangedData();
        data.notifyObservers(new Notification(
                    "No recommendation", "Recommendation"));
        ExitOutput.set(data, output);
    }
}
