package ihs.api.bookstores;

import ihs.models.Book;

public interface CheapestBookDownloader {

    /**
     * Interface implemented by every bookstore class. Searches for the cheapest book in bookstore.
     * Searches bookstore database by ISBN and/or book title.
     * @param isbn13 ISBN book number
     * @param bookTitle
     * @return the cheapest book found in bookstore API. Return DUMMY_BOOK if book was not found.
     */
    Book getCheapestBook(String bookTitle, String isbn13);

    Book DUMMY_BOOK = new Book(null);
}