package ihs.api;

import com.google.gson.JsonObject;
import ihs.TestUtils;
import ihs.api.bookstores.ITBookStore;
import ihs.models.Book;

public class ITBookStoreMock extends ITBookStore{

    public static String GET_CHEAPEST_TITLE = "mongodb";
    public static String GET_CHEAPEST_ISBN  = "9781484211830";

    static final String URL_MONGODB_ISBN = "https://api.itbook.store/1.0/books/" + GET_CHEAPEST_ISBN;
    static final String URL_MONGODB_TITLE = "https://api.itbook.store/1.0/search/" + GET_CHEAPEST_TITLE;
    static final String FILE_MONGO_DB = "src\\test\\resources\\bookstores\\ITBookStore_mongo_db.json";

    static final Book EXPECTED_BOOK = new Book(Bookstore.IT_BOOK_STORE, "The Definitive Guide to MongoDB, 3rd Edition", "", "9781484211830", 49.95, "USD", "https://itbook.store/books/9781484211830");

    @Override
    protected JsonObject getResponseJson(String fullURL) {
        if(URL_MONGODB_ISBN.equals(fullURL) || URL_MONGODB_TITLE.equals(fullURL)){
            return TestUtils.readJsonObject(FILE_MONGO_DB);
        }
        throw new IllegalArgumentException();
    }
}
