package ihs.api;

import com.google.gson.JsonObject;
import ihs.api.bookstores.GoogleBooks;
import ihs.TestUtils;
import ihs.models.Book;

public class GoogleBooksMock extends GoogleBooks{


    public static String GET_CHEAPEST_TITLE = "Java+Design+Patterns";
    public static String GET_CHEAPEST_ISBN  = "9780201485394";

    static final String URL_JAVA_DESIGN_ISBN = "https://www.googleapis.com/books/v1/volumes?q=isbn=" + GET_CHEAPEST_ISBN;
    static final String URL_JAVA_DESIGN_TITLE = "https://www.googleapis.com/books/v1/volumes?q=" + GET_CHEAPEST_TITLE;
    static final String FILE_JAVA_DESIGN = "src\\test\\resources\\bookstores\\Google_java_design_patterns.json";

    static final Book EXPECTED_BOOK = new Book(Bookstore.GOOGLE_BOOKS, "Java Design Patterns", "Vaskaran Sarcar", "9781484218020", 84.29, "PLN", "https://play.google.com/store/books/details?id=uq9PCwAAQBAJ&rdid=book-uq9PCwAAQBAJ&rdot=1&source=gbs_api");

    @Override
    protected JsonObject getResponseJson(String fullURL) {
        System.out.println(fullURL);
        if(URL_JAVA_DESIGN_ISBN.equals(fullURL) || URL_JAVA_DESIGN_TITLE.equals(fullURL)){
            return TestUtils.readJsonObject(FILE_JAVA_DESIGN);
        }
        throw new IllegalArgumentException();
    }
}
