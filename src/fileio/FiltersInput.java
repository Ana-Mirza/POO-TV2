package fileio;

public final class FiltersInput {
    private SortInput sort;
    private ContainsInput contains;

    /* constructor */
    public FiltersInput() { }

    public SortInput getSort() {
        return sort;
    }

    /* getters and setters */
    public void setSort(final SortInput sort) {
        this.sort = sort;
    }

    public ContainsInput getContains() {
        return contains;
    }

    public void setContains(final ContainsInput contains) {
        this.contains = contains;
    }
}
