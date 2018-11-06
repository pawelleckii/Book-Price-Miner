package ihs.APIdownloaders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ihs.models.Book;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ITBookStoreDownloader extends SiteContentDownloader implements CheapestBookDownloader {

    private static final String API_URL = "https://api.itbook.store/1.0/";

    @Override
    public Book getCheapestBook(String bookTitle, String isbn13) {
        Book cheapestBook = null;
        if(isbn13 != null && !isbn13.isEmpty()){
            cheapestBook = getCheapestBookByISBN(isbn13);
        }
        if(cheapestBook == null && bookTitle != null && !bookTitle.isEmpty()){
            cheapestBook = getCheapestBookByTitle(bookTitle);
        }
        return cheapestBook != null? cheapestBook : DUMMY_BOOK;
    }

    private Book getCheapestBookByISBN(String isbn13) {
        String fullURL = API_URL + "books/" + isbn13;
        JsonObject rootObj = getResponseJson(fullURL);
        List<Book> books = getBookList(rootObj);
        Optional<Book> cheapestValidBook;

        cheapestValidBook = books.stream()
                .filter(b -> b.getIsbn13().equals(isbn13))
                .filter(b -> b.getPrice() != -1.0)
                .sorted(Comparator.comparingDouble(Book::getPrice).reversed()).findFirst();
        return cheapestValidBook.orElse(null);
    }

    private Book getCheapestBookByTitle(String bookTitle) {
        String fullURL = API_URL + "search/" + urlifyTitle(bookTitle);
        JsonObject rootObj = getResponseJson(fullURL);
        List<Book> books = getBookList(rootObj);
        Optional<Book> cheapestValidBook;

        cheapestValidBook = books.stream()
                .filter(b -> b.getTitle().equals(bookTitle))
                .filter(b -> b.getPrice() != -1.0)
                .sorted(Comparator.comparingDouble(Book::getPrice).reversed()).findFirst();
        return cheapestValidBook.orElse(null);
    }


    private List<Book> getBookList(JsonObject rootObj) {
        if(rootObj.get("error").getAsString().equals("[books] Not found")){
            return new ArrayList<>();
        }
        JsonArray jsonBookArray = rootObj.getAsJsonArray("books");
        List<Book> bookList = new ArrayList<>();
        if(jsonBookArray != null) {
            for (JsonElement jsonBookInfo : jsonBookArray) {
                bookList.add(getBook(jsonBookInfo));
            }
        }
        return bookList;
    }

    private Book getBook(JsonElement jsonBookInfo) {
        double price = -1.0;
        String currencyCode = "";
        String buyLink = "";
        String title = "";
        String author = "";
        String isbn13 = "";
        JsonObject jsonObjectBookInfo = jsonBookInfo.getAsJsonObject();
        try {
            title = jsonObjectBookInfo.get("title").getAsString();
            isbn13 = jsonObjectBookInfo.get("isbn13").getAsString();
            buyLink = jsonObjectBookInfo.get("url").getAsString();
            String priceString = jsonObjectBookInfo.get("price").getAsString();
            if (priceString.contains("$")) {
                priceString = priceString.replace("$", "");
                price = Double.valueOf(priceString);
                currencyCode = "USD";
            }
        }catch (Exception e){
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, "en exception was thrown", e);
        }
        return new Book(Bookstore.IT_BOOK_STORE, title, author, isbn13, price, currencyCode, buyLink);
    }
}
