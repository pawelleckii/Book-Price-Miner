package ihs.api;

import ihs.api.bookstores.GoogleBooks;
import ihs.models.Book;
import org.junit.Assert;
import org.junit.Test;

public class GoogleBooksTest {

    @Test
    public void getCheapestBook() {
        GoogleBooks google = new GoogleBooksMock();
        Book book = google.getCheapestBook(GoogleBooksMock.GET_CHEAPEST_TITLE, GoogleBooksMock.GET_CHEAPEST_ISBN);
        Assert.assertEquals(GoogleBooksMock.EXPECTED_BOOK, book);
    }
}
