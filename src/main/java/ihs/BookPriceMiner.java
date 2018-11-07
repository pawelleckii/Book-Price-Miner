package ihs;

import ihs.api.API;
import ihs.models.Book;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class for handling user interface.
 * For a given title downloads a list of books from database.
 * User chooses which book from a list will be searched in bookstores.
 * Shows a list of cheapest books in each bookstore sorting it by price converted to PLN ascending.
 */
public class BookPriceMiner {

    public static void main(String[] args) {

        while(true){
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n________________");
            System.out.println("Type book title: ");
            String userPhrase = scanner.nextLine();
            if(userPhrase.isEmpty()){
                System.out.println("Finishing program...");
                return;
            }

            List<Book> booksFromDataBase = API.getBooksFromDatabase(userPhrase);
            if(booksFromDataBase == null || booksFromDataBase.isEmpty()){
                System.out.println("There are no such books in database.");
                continue;
            }

            for (int i = 0; i < booksFromDataBase.size(); i++) {
                System.out.println(i+1 + ". " + booksFromDataBase.get(i).simplePrint());
            }

            int bookIndex = scanBookIndex(scanner, booksFromDataBase);
            Book queriedBook = booksFromDataBase.get(bookIndex);

            List<Book> cheapestBooks = API.getCheapestBookFromEachBookstore(queriedBook.getTitle(), queriedBook.getIsbn13());

            if(!cheapestBooks.isEmpty()) {
                System.out.println("\n________________");
                System.out.println("Cheapest books:\n\n" + cheapestBooks.stream().map(Book::fullPrint).collect(Collectors.joining("\n\n")));
            } else {
                System.out.println("There are no books for sale.");
            }
        }
    }

    private static int scanBookIndex(Scanner scanner, List<Book> booksFromDataBase) {
        int bookIndex = -1;
        while(bookIndex < 0 || bookIndex > booksFromDataBase.size() - 1) {
            System.out.println("\nWhich book from the list did you mean? [1-" + booksFromDataBase.size() +"]");
            try {
                bookIndex = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e){
                continue;
            }
        }
        return bookIndex;
    }
}