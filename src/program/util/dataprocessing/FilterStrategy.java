package program.util.dataprocessing;

import program.pages.Page;
import program.util.dependencies.Filters;


public interface FilterStrategy {
    /**
     * Filter method to be implemented by specific filter
     * classes. It filters current movie list based on
     * certain characteristics.
     * @param input contains filters to be applied
     * @param page contains movie list to be filtered
     */
    void filter(Filters input, Page page);
}
