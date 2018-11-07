package ihs.models;

import ihs.APIdownloaders.Bookstore;
import ihs.APIdownloaders.NBPCurrencyRateDownloader;

import java.util.Comparator;

/***
 * Universal model of a single book.
 * Every API Downloader converts data to this model.
 */
public class Book implements Comparable<Book>{
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

    public double getPriceInPLN() {
        return priceInPLN;
    }

    @Override
    public String toString() {
        return  "{'" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn13='" + isbn13 + '\'' +
                ", price=" + price + " " + currencyCode +
                ", priceInPLN='" + priceInPLN + '\'' +
                ", buyLink='" + buyLink + '\'' +
                ", bookStore=" + bookStore +
                "}";
    }

    @Override
    public int compareTo(Book o) {
        return Double.compare(getPriceInPLN(), o.getPriceInPLN());
    }

    public static final Comparator<Book> Cheapest = Comparator.comparingDouble(Book::getPriceInPLN).reversed();

    public String simplePrint() {
        return "'" + title + "' by " + author + " | Price: " + price + " " + currencyCode;
    }

    public String fullPrint() {
        if(title == null || title.isEmpty())
        {
            return bookStore.getName() + ":\n'" + "No such book.";
        }
        return bookStore.getName() + ":\n'" + title + "' by " + author + " | Price: " + String.format("%.2f", priceInPLN) + " zl" + "\nBuy Link: " + buyLink;
    }
}
