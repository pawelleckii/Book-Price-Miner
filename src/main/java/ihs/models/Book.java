package ihs.models;

import ihs.api.Bookstore;
import ihs.api.NBPCurrencyRateDownloader;

/***
 * Universal model of a single book.
 * Every API Downloader converts data to this model.
 */
public class Book implements Comparable<Book>{
    private final String title;
    private final String isbn13;
    private final String author;
    private final double price;
    private final double priceInPLN;
    private final String currencyCode;
    private final String buyLink;
    private final Bookstore bookStore;

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

    public Book() {
        this(null, "", "", "", -1.0, "", "");
    }

    private double priceToPLN() {
        if("PLN".equals(currencyCode) || price <= 0){
            return price;
        }
        if(currencyCode != null && !currencyCode.isEmpty()){
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

    public String simplePrint() {
        return "'" + title + "' by " + author + " | Price: " + price + " " + currencyCode;
    }

    public String fullPrint() {
        if(title == null || title.isEmpty())
        {
            return bookStore.getName() + ":\n'" + "No such book.";
        }
        String auth = this.author;
        if(author == null || author.isEmpty()){
            auth = "unknown author";
        }
        return bookStore.getName() + ":\n'" + title + "' by " + auth + " | Price: " + String.format("%.2f", priceInPLN) + " zl" + "\nBuy Link: " + buyLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (Double.compare(book.price, price) != 0) return false;
        if (Double.compare(book.priceInPLN, priceInPLN) != 0) return false;
        if (title != null ? !title.equals(book.title) : book.title != null) return false;
        if (author != null ? !author.equals(book.author) : book.author != null) return false;
        if (isbn13 != null ? !isbn13.equals(book.isbn13) : book.isbn13 != null) return false;
        if (currencyCode != null ? !currencyCode.equals(book.currencyCode) : book.currencyCode != null) return false;
        if (buyLink != null ? !buyLink.equals(book.buyLink) : book.buyLink != null) return false;
        return bookStore == book.bookStore;
    }

}
