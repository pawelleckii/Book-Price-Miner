package ihs.models;

import ihs.APIdownloaders.Bookstore;
import ihs.APIdownloaders.NBPCurrencyRateDownloader;

/***
 * Universal model of a single book.
 * Every API Downloader has to convert data to this model.
 */
public class Book {
    private final String title;
    private final String author;
    private final String isbn13;
    private final double price;
    private final double priceInPLN;
    private final String currencyCode;
    private final String buyLink;
    private final  Bookstore bookStore;

    public Book(Bookstore bookStore, String title, String author, String isbn13, double price, String currencyCode, String buyLink) {
        this.bookStore = bookStore;
        this.title = title;
        this.author = author;
        this.isbn13 = isbn13;
        this.price = price;
        this.currencyCode = currencyCode.toUpperCase();
        this.buyLink = buyLink;
        this.priceInPLN = priceToPLN();
    }

    public Book(Bookstore bookStore) {
        this(bookStore, "", "", "", -1.0, "", "");
    }

    private double priceToPLN() {
        if("PLN".equals(currencyCode)){
            return price;
        }
        if(currencyCode != null && !currencyCode.isEmpty() && !currencyCode.equals("PLN")){
            NBPCurrencyRateDownloader downloader = NBPCurrencyRateDownloader.getInstance();
            double currencyRate = downloader.getCurrencyRate(currencyCode);
            if(currencyRate != -1.0){
                return price * currencyRate;
            }
        }
        return -1.0;
    }

    public double getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn13() {
        return isbn13;
    }

    @Override
    public String toString() {
        return "ihs.models.Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", price=" + price + " " + currencyCode +
                ", priceInPLN='" + priceInPLN + '\'' +
                ", buyLink='" + buyLink + '\'' +
                ", bookStore=" + bookStore +
                "}\n";
    }
}
