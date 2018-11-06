package ihs;

import ihs.APIdownloaders.API;
import ihs.models.Book;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Main class handling user interface.
 * For a given title downloads a list of books from database.
 * User chooses which book from a list will be searched in bookstores.
 */
public class BookPriceMiner {

    public static void main(String[] args) {

        while(true){
            System.out.println("Type book title:");
            Scanner scanner = new Scanner(System.in);
            String userRawTitle = scanner.nextLine();
            if(userRawTitle.isEmpty()){
                return;
            }

            List<Book> booksFromDataBase = API.getBooksFromDatabase(userRawTitle);
            if(booksFromDataBase == null || booksFromDataBase.isEmpty()){
                System.out.println("There are no such books in database.");
                continue;
            }

            for (int i = 0; i < booksFromDataBase.size(); i++) {
                System.out.println(i+1 + ". " + booksFromDataBase.get(i).simplePrint());
            }

            int bookIndex = -1;
            while(bookIndex < 0 || bookIndex > booksFromDataBase.size() - 1) {
                System.out.println("Which book from the list did you mean?");
                bookIndex = scanner.nextInt() - 1;
            }
            Book queriedBook = booksFromDataBase.get(bookIndex);

            List<Book> cheapestBooks = API.getCheapestBookFromEachBookstore(queriedBook.getTitle(), queriedBook.getIsbn13());

            if(!cheapestBooks.isEmpty()) {
                System.out.println("List of books:\n" + cheapestBooks.stream().map(Book::fullPrint).collect(Collectors.joining("\n")));
            } else {
                System.out.println("There are no books for sale.");
            }
        }
    }
}