package program.pages;

import program.actions.Action;
import program.util.Movie;
import program.util.User;

import java.util.ArrayList;

public abstract class Page {
    private String name;
    private User currentUser;
    private ArrayList<Movie> userMovies;
    private ArrayList<String> accesiblePages;
    private ArrayList<String> actionsPermitted;

    // constructors
    public Page(final String name) {
        this.name = name;
        currentUser = new User();
        userMovies = new ArrayList<>();
        accesiblePages = new ArrayList<>();
        actionsPermitted = new ArrayList<>();
    }

    public Page(final String name, final User currentUser) {
        this.name = name;
        this.currentUser = currentUser;
        userMovies = new ArrayList<>();
        accesiblePages = new ArrayList<>();
        actionsPermitted = new ArrayList<>();
    }

    public Page(final String name, final User currentUser, final ArrayList<Movie> movies) {
        this.name = name;
        this.currentUser = currentUser;
        userMovies = new ArrayList<>(movies);
        accesiblePages = new ArrayList<>();
        actionsPermitted = new ArrayList<>();
    }

    // getters and setters
    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final  User getCurrentUser() {
        return currentUser;
    }

    public final void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public final ArrayList<String> getAccesiblePages() {
        return accesiblePages;
    }

    public final ArrayList<String> getActionsPermitted() {
        return actionsPermitted;
    }

    public final void setUserMovies(final ArrayList<Movie> userMovies) {
        this.userMovies = userMovies;
    }

    public final ArrayList<Movie> getUserMovies() {
        return userMovies;
    }


    /**
     * Method to accept visitor
     * @param action is action visitor
     */
    public void accept(final Action action) { }
}
