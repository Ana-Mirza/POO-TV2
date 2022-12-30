package program.util.dataprocessing;

import program.pages.Page;
import program.util.dependencies.Contains;
import program.util.dependencies.Filters;

public final class ContentStrategy implements FilterStrategy {
    /**
     * Method applies filter to movie list available
     * in current page, by deleting movies that do not
     * contain certain actors or genres.
     * @param input contains filters to be applied
     * @param page contains movie list to be filtered
     */
    @Override
    public void filter(final Filters input, final Page page) {
        Contains contains = input.getContains();

        // check if contains filter exists
        if (contains == null) {
            return;
        }

        // delete movies not containing given actors
        if (contains.getActors() != null) {
            for (String actor: contains.getActors()) {
                page.getUserMovies().removeIf((movie) ->
                        !movie.getActors().contains(actor));
            }
        }

        // delete movies not having given genres
        if (contains.getGenres() != null) {
            for (String genre: contains.getGenres()) {
                page.getUserMovies().removeIf((movie) ->
                        !movie.getGenres().contains(genre));
            }
        }
    }
}
