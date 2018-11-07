package ihs.APIdownloaders.bookstores;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ihs.APIdownloaders.Bookstore;
import ihs.APIdownloaders.SiteContentDownloader;
import ihs.models.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Class for connecting to Google Books API.
 * Can be used for downloading whole list by user phrase or
 */
public class GoogleBooks extends SiteContentDownloader implements CheapestBookDownloader, BookListDownloader {

    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    @Override
    public List<Book> downloadBookList(String userPhrase){

        String fullURL = API_URL + urlifyTitle(userPhrase);
        JsonObject rootObj = getResponseJson(fullURL);
        return getBookList(rootObj);
    }

    @Override
    public Book getCheapestBook(String bookTitle, String isbn13) {
        Book cheapestBook = null;
        if (isbn13 != null && !isbn13.isEmpty()) {
            cheapestBook = getCheapestBookByISBN(isbn13);
        }
        if (cheapestBook == null && bookTitle != null && !bookTitle.isEmpty()) {
            cheapestBook = getCheapestBookByTitle(bookTitle);
        }
        return cheapestBook != null? cheapestBook : DUMMY_BOOK;
    }

    private Book getCheapestBookByISBN(String isbn13) {
        String fullURL = API_URL + "isbn=" + isbn13;
        JsonObject rootObj = getResponseJson(fullURL);
        List<Book> books = getBookList(rootObj);
        Optional<Book> cheapestValidBook;
        cheapestValidBook = books.stream()
                .filter(b -> b.getIsbn13().equals(isbn13))
                .filter(b -> b.getPrice() != -1.0)
                .sorted()
                .findFirst();
        return cheapestValidBook.orElse(null);
    }

    private Book getCheapestBookByTitle(String bookTitle) {
        String fullURL = API_URL + urlifyTitle(bookTitle);
        JsonObject rootObj = getResponseJson(fullURL);
        List<Book> books = getBookList(rootObj);
        Optional<Book> cheapestValidBook;
        cheapestValidBook = books.stream()
                .filter(b -> b.getTitle().equals(bookTitle))
                .filter(b -> b.getPrice() != -1.0)
                .sorted()
                .findFirst();
        return cheapestValidBook.orElse(null);
    }

    private List<Book> getBookList(JsonObject rootObj) {
        JsonArray jsonBookArray = rootObj.getAsJsonArray("items");
        if(jsonBookArray == null){
            return new ArrayList<>();
        }
        List<Book> bookList = new ArrayList<>();
        for(JsonElement jsonBookInfo : jsonBookArray){
            bookList.add(getBook(jsonBookInfo));
        }
        return bookList.stream()
                .filter(b -> b.getPriceInPLN() != -1.0)
                .collect(Collectors.toList());
    }

    private Book getBook(JsonElement jsonBookInfo) {
        double price = -1.0;
        String currencyCode = "";
        String buyLink = "";
        String title = "";
        String author = "";
        String isbn13 = "";
        try {
            JsonObject jsonObject = jsonBookInfo.getAsJsonObject();
            JsonObject volumeInfo = jsonObject.getAsJsonObject("volumeInfo");
            title = volumeInfo.get("title").getAsString();
            JsonArray authors = volumeInfo.getAsJsonArray("authors");
            author = authors != null? authors.get(0).getAsString() : "";

            JsonArray jsonIndustryIdentifiers = volumeInfo.getAsJsonArray("industryIdentifiers");

            //industryIdentifiers: {"type": String , "identifier" : String}
            if(jsonIndustryIdentifiers != null) {
                for (JsonElement identifier : jsonIndustryIdentifiers) {
                    if (identifier.getAsJsonObject().get("type").getAsString().equals("ISBN_13")) {
                        isbn13 = identifier.getAsJsonObject().get("identifier").getAsString();
                        break;
                    }
                }
            }

            JsonObject saleInfo = jsonObject.getAsJsonObject("saleInfo");
            JsonObject retailPrice = saleInfo.getAsJsonObject("retailPrice");
            if(retailPrice != null) {
                price = retailPrice.get("amount").getAsDouble();
                currencyCode = retailPrice.get("currencyCode").getAsString();
            }
            JsonElement buyLinkJson = saleInfo.get("buyLink");
            if(buyLinkJson != null) {
                buyLink = buyLinkJson.getAsString();
            }
        } catch (Exception e){
            Logger.getLogger(this.getClass().toString()).log(Level.SEVERE, "an exception was thrown", e);
        }
        return new Book(Bookstore.GOOGLE_BOOKS, title, author, isbn13, price, currencyCode, buyLink);
    }
}