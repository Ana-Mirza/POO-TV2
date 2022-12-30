package program.actions.database.observers;

import program.util.Database;
import program.util.Movie;
import program.util.User;
import program.util.dependencies.Notification;

import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;



public final class Recommendation implements Observer {
    @Override
    public void update(final Observable o, final Object arg) {
        Database database = (Database) o;
        Notification notification = (Notification) arg;

        /* check if this is a recommendation
        * and if there is a premium user logger
        * */
        if (!notification.getMessage().equals("Recommendation")
                || database.getCurrentUser() == null
                || !database.getCurrentUser().isPremium()) {
            return;
        }

        /* sorted genre list */
        ArrayList<String> genres = getSortedGenres(database);

        /* sorted movie list */
        ArrayList<Movie> movies = getSortedMovies(database);

        /* find recommended movie */
        notification.setMovieName(recommendedMovie(genres, movies,
                                        database.getCurrentUser()));
        database.getCurrentUser().getNotifications().add(notification);

    }

    private ArrayList<String> getSortedGenres(final Database database) {
        ArrayList<String> genres = new ArrayList<>();

        /* use hashmap to keep count of ratings for each genre */
        HashMap<String, Integer> genreRating = new HashMap<>();

        database.getCurrentUser().getLikedMovies().forEach(movie ->
                                movie.getGenres().forEach(genre -> {
            if (!genres.contains(genre)) {
                genres.add(genre);
                genreRating.put(genre, 1);
            } else {
                int oldLikes = genreRating.get(genre);
                genreRating.put(genre, oldLikes + 1);
            }
        }));

        // sort list of genres based on likes, then lexicographically
        genres.sort(Comparator.comparing((String o) ->
                genreRating.get(o)).reversed().thenComparing(
                                String.CASE_INSENSITIVE_ORDER));

        return genres;
    }

    private ArrayList<Movie> getSortedMovies(final Database database) {
        ArrayList<Movie> movies = new ArrayList<>(database.getMoviesData());

        /* delete movies banned from user */
        String userCountry = database.getCurrentUser().getCredentials().getCountry();
        movies.removeIf(movie -> movie.getCountriesBanned().contains(userCountry));

        /* sort list of movies based on likes */
        movies.sort(Comparator.comparingInt(Movie::getNumLikes).reversed());

        return movies;
    }

    private String recommendedMovie(final ArrayList<String> genres,
                                    final ArrayList<Movie> movies,
                                    final User user) {
        for (String genre: genres) {
            for (Movie movie: movies) {

                /* check if movies was watched by user
                *  and if it belongs to genres liked
                * */
                if (movie.getGenres().contains(genre)
                    && !user.getWatchedMovies().contains(movie)) {
                    return movie.getName();
                }
            }
        }

        return "No recommendation";
    }
}
