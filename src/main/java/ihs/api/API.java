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

    private static final BookListDownloader database = new GoogleBooks();
    private static final List<CheapestBookDownloader> bookStoreDownloaders = Arrays.asList(new GoogleBooks(), new ITBookStore());

    public static  List<Book> getBooksFromDatabase(String userPhrase){
        return database.downloadBookList(userPhrase);
    }

    public static List<Book> getCheapestBookFromEachBookstore(String bookTitle, String isbn13){
        return bookStoreDownloaders.parallelStream()
                .map(downloader -> downloader.getCheapestBook(bookTitle, isbn13))
                .filter(cheapestBook -> cheapestBook != CheapestBookDownloader.DUMMY_BOOK)
                .sorted()
                .collect(Collectors.toList());
    }
}