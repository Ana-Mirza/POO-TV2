package fileio;

import java.util.ArrayList;

public final class Input {

    private ArrayList<UsersInput> users;

    private ArrayList<MoviesInput> movies;

    private ArrayList<ActionsInput> actions = new ArrayList<>();

    /* constructor */
    public Input() {
    }

    /* getters and setters */
    public ArrayList<UsersInput> getUsers() {
        return users;
    }

    public void setUsers(final ArrayList<UsersInput> users) {
        this.users = users;
    }

    public ArrayList<MoviesInput> getMovies() {
        return movies;
    }

    public void setMovies(final ArrayList<MoviesInput> movies) {
        this.movies = movies;
    }

    public ArrayList<ActionsInput> getActions() {
        return actions;
    }

    public void setActions(final ArrayList<ActionsInput> actions) {
        this.actions = actions;
    }
}
