package ihs.api;

import ihs.api.bookstores.GoogleBooks;
import ihs.models.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GoogleBooksTest {

    GoogleBooks google = new GoogleBooksMock();

    @Test
    public void getCheapestBook() throws Exception{
        Book book = google.getCheapestBook(GoogleBooksMock.GET_CHEAPEST_TITLE, GoogleBooksMock.GET_CHEAPEST_ISBN);
        Assert.assertEquals(GoogleBooksMock.EXPECTED_BOOK, book);
    }

}
