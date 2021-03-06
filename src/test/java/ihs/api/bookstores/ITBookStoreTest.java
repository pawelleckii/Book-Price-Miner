package ihs.api.bookstores;

import ihs.models.Book;
import org.junit.Assert;
import org.junit.Test;

public class ITBookStoreTest {

    ITBookStore itBookStore = new ITBookStoreMock();

    @Test
    public void getCheapestBook() throws Exception {
        Book book = itBookStore.getCheapestBook(ITBookStoreMock.GET_CHEAPEST_ISBN, ITBookStoreMock.GET_CHEAPEST_TITLE);
        Assert.assertEquals(ITBookStoreMock.EXPECTED_BOOK, book);
    }
}
