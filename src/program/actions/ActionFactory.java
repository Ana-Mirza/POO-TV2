package program.actions;

import fileio.ActionsInput;
import program.actions.back.Back;
import program.actions.changepage.ChangePage;
import program.actions.database.Add;
import program.actions.onpage.Registers;
import program.actions.onpage.Logins;
import program.actions.onpage.Search;
import program.actions.onpage.Purchase;
import program.actions.onpage.Filter;
import program.actions.onpage.Watch;
import program.actions.onpage.Like;
import program.actions.onpage.Rate;
import program.actions.onpage.BuyPremiumAccount;
import program.actions.onpage.BuyTokens;
import program.actions.database.Delete;
import program.actions.subscribe.Subscribe;

public abstract class ActionFactory {
    /**
     * @param actions contains actions to be instanced
     * @return instance of specific action
     */
    public static Action createAction(final ActionsInput actions) {
        return switch (actions.getType()) {
            case "change page" -> new ChangePage(actions);
            case "subscribe" -> new Subscribe(actions);
            case "back" -> new Back();

            /* on page actions */
            case "on page" -> switch (actions.getFeature()) {
                case "register" -> new Registers(actions);
                case "login" -> new Logins(actions);
                case "search" -> new Search(actions);
                case "filter" -> new Filter(actions);
                case "purchase" -> new Purchase(actions);
                case "watch" -> new Watch(actions);
                case "like" -> new Like(actions);
                case "rate" -> new Rate(actions);
                case "buy premium account" -> new BuyPremiumAccount(actions);
                case "buy tokens" -> new BuyTokens(actions);
                default -> null;
            };

            /* database actions */
            case "database" -> switch (actions.getFeature()) {
                case "add" -> new Add(actions);
                case "delete" -> new Delete(actions);
                default -> null;
            };
            default -> null;
        };
    }
}
