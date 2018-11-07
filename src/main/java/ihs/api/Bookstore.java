package ihs.api;

/**
 * Enum contains every served bookstore API.
 */
public enum Bookstore {
    GOOGLE_BOOKS("Google Books"),
    IT_BOOK_STORE("ITbook.store");

    private String name;

    Bookstore(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
