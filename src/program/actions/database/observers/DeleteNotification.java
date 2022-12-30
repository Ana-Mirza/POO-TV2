package program.actions.database.observers;

import program.pages.Movies;
import program.util.Database;
import program.util.Movie;
import program.util.User;
import program.util.dependencies.Notification;

import java.util.Observable;
import java.util.Observer;

public final class DeleteNotification implements Observer {

    @Override
    public void update(final Observable o, final Object arg) {
        Database database = (Database) o;
        Notification notification = (Notification) arg;

        /* check if notification is for delete action */
        if (!notification.getMessage().equals("DELETE")) {
            return;
        }

        /* update each user's list of movies */
        for (User user: database.getUsersData()) {

            /* notify user if he purchased movie deleted */
            user.getPurchasedMovies().forEach(movie -> {
                if (movie.getName().equals(notification.getMovieName())) {
                    user.getNotifications().add(notification);

                    /* restore tokens or free movies */
                    updateTokens(user);
                }
            });

            /* delete movie from user's lists of purchased,
             * watched, liked, rated movies
             */
            updateUserLists(user, notification.getMovieName());
        }
    }

    /**
     * Method deletes movie removed from all user's lists
     * @param user contains user from which movie is removed
     * @param movieName name of movie removed
     */
    private void updateUserLists(final User user, final String movieName) {
        user.getPurchasedMovies().removeIf(movie -> movie.getName().equals(movieName));
        user.getWatchedMovies().removeIf(movie -> movie.getName().equals(movieName));
        user.getLikedMovies().removeIf(movie -> movie.getName().equals(movieName));
        user.getRatedMovies().removeIf(movie -> movie.getName().equals(movieName));
    }

    /**
     * Method adds price of movie (2 tokens) to user
     * or a free movie if user is premium
     * @param user to be restored tokens or free movies
     */
    private void updateTokens(final User user) {
        if (user.isPremium()) {
            int currentNum = user.getNumFreePremiumMovies();
            user.setNumFreePremiumMovies(currentNum + 1);
        } else {
            int currentTokens = user.getTokensCount();
            user.setTokensCount(currentTokens + 2);
        }
    }
}
