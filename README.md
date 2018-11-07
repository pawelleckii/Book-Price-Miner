# Book-Price-Miner
The application downloads book sale information for a given title from various online bookstores and shows the cheapest one with a buy link.
* User types phrase to be found in database (for now using Google Books database).
* User chooses book he meant from a list of books.
* Application shows a list of cheapest books in every bookstore sorted by price.

## External frontend
The application is ready to be used with external frontend engine by using only class **ihs.api.API**.

The only two methods in class 'API' that can be used for external frontend are:

**List[Book] getBooksFromDatabase(String userPhrase)**
  
Downloads list of books in database to let user choose the one that he meant by providing **userPhrase**.

**List[Book] getCheapestBookFromEachBookstore(String bookTitle, String isbn13)**
  
Downloads List of cheapest Book with buy links from each bookstore sorted by price to show on the screen.
  
## Adding a new bookstore API
*To add a new bookstore API, developer has to make three simple steps:*
* Create a new class handling this bookstore API in package ihs.api.bookstores 
(created class has to implement CheapestBookDownloader interface)
* Add new instance of created class to list of bookstores in 'ihs.api.API'
* Add a new instance in Enum 'Bookstore'

## Running the application
The application has been provided with sample implementation.
To see how it works, user has to run ihs.api.BookPriceMiner class.
