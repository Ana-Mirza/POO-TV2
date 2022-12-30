package program.actions.database.observers;

import program.util.Database;
import program.util.Movie;
import program.util.User;
import program.util.dependencies.Notification;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public final class AddNotification implements Observer {

    @Override
    public void update(final Observable o, final Object arg) {
        Notification notification = (Notification) arg;
        Database database = (Database) o;

        /* check if notification is for an add action*/
        if (!notification.getMessage().equals("ADD")) {
            return;
        }

        Movie movie = getMovie(database, notification);

        /* notify all users with movie's genres */
        for (User user: database.getUsersData()) {

            /* check that movie is not banned from user */
            if (!movie.getCountriesBanned().contains(user.getCredentials().getCountry())) {
                /* notify user if he is subscribed to at least
                   one genre of movie
                 */
                for (String genre: movie.getGenres()) {
                    if (user.getSubscriptions().contains(genre)) {
                        user.getNotifications().add(notification);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Method searches for movie newly added
     * @param database contains information about the program
     * @param notification contains notification to be sent to users
     * @return movie recently added
     */
    private static Movie getMovie(final Database database, final Notification notification) {
        ArrayList<Movie> movies = new ArrayList<>(database.getMoviesData());

        /* remove old movies */
        movies.removeIf(movie -> !movie.getName().equals(notification.getMovieName()));
        return movies.get(0);
    }
}
