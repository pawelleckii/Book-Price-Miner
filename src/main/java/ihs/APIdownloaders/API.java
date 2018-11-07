package ihs.APIdownloaders;

import ihs.APIdownloaders.bookstores.BookListDownloader;
import ihs.APIdownloaders.bookstores.CheapestBookDownloader;
import ihs.APIdownloaders.bookstores.GoogleBooks;
import ihs.APIdownloaders.bookstores.ITBookStore;
import ihs.models.Book;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class for handling //TODO
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