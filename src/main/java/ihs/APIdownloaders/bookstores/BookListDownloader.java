package ihs.APIdownloaders.bookstores;

import ihs.models.Book;
import java.util.List;


public interface BookListDownloader {

    /**
     * For a given user phrase returns a list of books that user can search in bookstores.
     * @param userPhrase inserted by user can contain special characters.
     * @return List of books from which user chooses the one that should be searched in bookstores.
     */
    List<Book> downloadBookList(String userPhrase);
}