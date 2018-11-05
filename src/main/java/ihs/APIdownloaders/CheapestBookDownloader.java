package ihs.APIdownloaders;


import ihs.models.Book;

public interface CheapestBookDownloader {
    /**
     *
     * @param bookTitle
     * @param isbn13
     * @return DUMMY_BOOK if book not found
     */
    Book getCheapestBook(String bookTitle, String isbn13);


    static final Book DUMMY_BOOK = new Book(Bookstore.GOOGLE_BOOKS);
}