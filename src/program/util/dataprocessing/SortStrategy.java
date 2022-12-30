package program.util.dataprocessing;

import program.pages.Page;
import program.util.Movie;
import program.util.dependencies.Filters;
import program.util.dependencies.Sort;

import java.util.Comparator;

public final class SortStrategy implements FilterStrategy {
    /**
     * Sorts current movie list based on duration first,
     * and in case of equality, by rating.
     * @param input contains filters to be applied
     * @param page contains movie list to be filtered
     */
    @Override
    public void filter(final Filters input, final Page page) {
        Sort sort = input.getSort();

        /* check if sorting method exists */
        if (sort == null) {
            return;
        }

        /* sort based on duration first, then by rating */
        if (sort.getRating().equals("increasing")
                && sort.getDuration() == null) {
            page.getUserMovies().sort(Comparator.comparingDouble(Movie::getRating));
        } else if (sort.getRating().equals("decreasing")
                && sort.getDuration() == null) {
            page.getUserMovies().sort(Comparator.comparingDouble(
                    Movie::getRating).reversed());
        } else if (sort.getDuration().equals("decreasing")
                && sort.getRating().equals("decreasing")) {
            page.getUserMovies().sort(Comparator.comparingInt(
                    Movie::getDuration).reversed().thenComparing(
                    Comparator.comparingDouble(Movie::getRating).reversed()));
        } else if (sort.getDuration().equals("increasing")
                && sort.getRating().equals("increasing")) {
            page.getUserMovies().sort(Comparator.comparingInt(
                    Movie::getDuration).thenComparingDouble(Movie::getRating));
        } else if (sort.getDuration().equals("increasing")
                && sort.getRating().equals("decreasing")) {
            page.getUserMovies().sort(Comparator.comparingInt(
                    Movie::getDuration).thenComparing(
                    Comparator.comparingDouble(Movie::getRating).reversed()));
        } else if (sort.getDuration().equals("decreasing")
                && sort.getRating().equals("increasing")) {
            page.getUserMovies().sort(Comparator.comparingInt(
                    Movie::getDuration).reversed().thenComparingDouble(Movie::getRating));
        }
    }
}
