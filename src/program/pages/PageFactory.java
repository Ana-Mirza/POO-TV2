package program.pages;

import program.util.Database;

public abstract class PageFactory {
    /**
     * Method creates instance of specific page
     * @param page contains name of page to be created
     * @param data contains status of system
     * @return specific instance of a page
     */
    public static Page createPage(final String page, final Database data) {
        return switch (page) {
            case "homepage neautentificat" -> new UnloggedHomepage();
            case "login" -> new Login();
            case "register" -> new Register();
            case "homepage autentificat" -> new LoggedHomepage(data);
            case "movies" -> new Movies(data);
            case "see details" -> new SeeDetails(data);
            case "upgrades" -> new Upgrades(data);
            case "logout" -> new Logout();
            default -> null;
        };
    }
}
