package ihs;

import ihs.APIdownloaders.BookListDownloader;
import ihs.APIdownloaders.CheapestBookDownloader;
import ihs.APIdownloaders.GoogleBooksDownloader;
import ihs.APIdownloaders.ITBookStoreDownloader;
import ihs.models.Book;

import java.util.List;

public class BookPriceMiner {

    public static void main(String[] args) {

        String userRawTitle = "APIs: A Strategy Guide";
        //String userRawTitle = "Reksio";
        //String userRawTitle = "APIs: A Strategy Guide";

        GoogleBooksDownloader googleBooksDownloader = new GoogleBooksDownloader();
        List<Book> booksFromDataBase = googleBooksDownloader.downloadBookList(userRawTitle);
        System.out.println(booksFromDataBase.toString());
        Book queriedBook = booksFromDataBase.get(0);

        Book cheapestGoogleBook = googleBooksDownloader.getCheapestBook(queriedBook.getTitle(), queriedBook.getIsbn13());
        System.out.println(cheapestGoogleBook.toString());

        CheapestBookDownloader itBookStoreDownloader = new ITBookStoreDownloader();
        Book cheapestITStoreBook = itBookStoreDownloader.getCheapestBook(queriedBook.getTitle(), queriedBook.getIsbn13());
        System.out.println(cheapestITStoreBook.toString());
    }
}