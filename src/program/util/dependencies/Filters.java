package program.util.dependencies;

import fileio.FiltersInput;

public final class Filters {
    private Sort sort;
    private Contains contains;

    public Filters(final FiltersInput input) {
        if (input.getSort() != null) {
            sort = new Sort(input.getSort());
        }

        if (input.getContains() != null) {
            contains = new Contains(input.getContains());
        }
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(final Sort sort) {
        this.sort = sort;
    }

    public Contains getContains() {
        return contains;
    }

    public void setContains(final Contains contains) {
        this.contains = contains;
    }

    /**
     * sort method implemented by filter strategy
     */
    public void sort() { }
}
