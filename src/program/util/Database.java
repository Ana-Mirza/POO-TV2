package program.util;

import fileio.Input;
import program.pages.Page;
import program.pages.PageFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

/**
 * Database of the program. Contains information about
 * the users in the system and all the movies.
 */
public class Database extends Observable {
    private final ArrayList<User> usersData;
    private final ArrayList<Movie> moviesData;
    private Page currentPage;
    private User currentUser;
    private ArrayList<Movie> userMovies;
    private LinkedList<Page> prevPages;

    // constructor
    public Database(final Input input) {
        // add database of users
        usersData = new ArrayList<>();
        input.getUsers().forEach((user) ->
                usersData.add(new User(user)));

        // add database of movies
        moviesData = new ArrayList<>();
        input.getMovies().forEach((movie) ->
                moviesData.add(new Movie(movie)));

        // set current page
        currentPage = PageFactory.createPage("homepage neautentificat", this);

        // set stack of pages
        prevPages = new LinkedList<>();
    }

    // getters and setters
    public final ArrayList<User> getUsersData() {
        return usersData;
    }

    public final ArrayList<Movie> getMoviesData() {
        return moviesData;
    }

    public final Page getCurrentPage() {
        return currentPage;
    }

    public final void setCurrentPage(final Page currentPage) {
        this.currentPage = currentPage;
    }

    public final User getCurrentUser() {
        return currentUser;
    }

    public final void setCurrentUser(final User currentUser) {
        this.currentUser = currentUser;
    }

    public final ArrayList<Movie> getUserMovies() {
        return userMovies;
    }

    public final LinkedList<Page> getPrevPages() {
        return prevPages;
    }

    public final void setPrevPages(final LinkedList<Page> prevPages) {
        this.prevPages = prevPages;
    }

    /**
     * Method signals that the database has been changed
     */
    public final void setChangedData() {
        setChanged();
    }

    /**
     * Initializes movies available to user
     * @param userMovies contains list of movies available
     */
    public final void setUserMovies(final ArrayList<Movie> userMovies) {
        this.userMovies = new ArrayList<>();
        this.userMovies.addAll(userMovies);
    }
}
