# Book-Price-Miner
The application downloads book sale information for a given title from various online bookstores and shows the cheapest one with a buy link.

## API documentation
The application is ready to be used with external frontend technology.
Main engine class is **ihs.api.API**

Two methods in class 'API' that can be used in external frontend are:
**List<Book> getBooksFromDatabase(String userPhrase)**
Downloads list of books in database to let user choose one that he meant by typing **userPhrase**.

**List<Book> getCheapestBookFromEachBookstore(String bookTitle, String isbn13)**

