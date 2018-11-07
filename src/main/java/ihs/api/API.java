package ihs.api;

import ihs.api.bookstores.BookListDownloader;
import ihs.api.bookstores.CheapestBookDownloader;
import ihs.api.bookstores.GoogleBooks;
import ihs.api.bookstores.ITBookStore;
import ihs.models.Book;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main app engine. Class for handling connections to every bookstore.
 * Field 'database' contains a class used for downloading book list from which user chooses book to search in bookstores.
 * Field 'bookStoreDownloaders' contains a list of bookstore API classes that will be used to find the cheapest book.
 */
public class API {

    //search engine used to find books
    private static final BookListDownloader database = new GoogleBooks();

    //list of all available books stores
    private static final List<CheapestBookDownloader> bookStoreDownloaders = Arrays.asList(new GoogleBooks(), new ITBookStore());

    /**
     * Method to search books in bookstore(s)
     * @param userPhrase phrase which is used to find books
     * @return the list of books.
     */
    public static  List<Book> getBooksFromDatabase(String userPhrase){
        return database.downloadBookList(userPhrase);
    }

    /**
     * Search for the cheapest book in all registered bookstores
     * @param isbn13 ISBN of search book (some stores can search by that number, null if search is done by title only)
     * @param bookTitle book title to search (can be null if search is done by ISBN only)
     * @return list of book ordered by price (if book is not fond than list is empty)
     */
    public static List<Book> getCheapestBookFromEachBookstore(String isbn13, String bookTitle){
        //the search is done in parallel
        return bookStoreDownloaders.parallelStream()
                .map(downloader -> downloader.getCheapestBook(isbn13, bookTitle))
                .filter(cheapestBook -> cheapestBook != CheapestBookDownloader.DUMMY_BOOK)
                .sorted()
                .collect(Collectors.toList());
    }
}