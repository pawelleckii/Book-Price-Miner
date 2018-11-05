package ihs.APIdownloaders;

import ihs.models.Book;
import java.util.List;

public interface BookListDownloader {
    List<Book> downloadBookList(String userQueryPhrase);
}