package program.actions;

import fileio.ActionsInput;

public abstract class Feature {
    private String type;
    private String page;

    /**
     * Initializes type of feature and page on which
     * feature is applied.
     * @param input stores input data of action
     */
    public Feature(final ActionsInput input) {
        type = input.getType();
        page = input.getPage();
    }

    // getters and setters
    public final String getType() {
        return type;
    }

    public final void setType(final String type) {
        this.type = type;
    }

    public final String getPage() {
        return page;
    }

    public final void setPage(final String page) {
        this.page = page;
    }
}
