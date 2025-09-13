1. The deleteCopy function returned the opposite values then were specificed.
2. The returnCopy function checked if it was empty rather than not empty.
3. The updateBook in mockAPIService said this.books = this.books, but this would not use the new tmp books that has the updated version of books.
4. There was a missing return statement in RouterController.java.
5. There was an implicit type case from object where it needed to be explicitly type cast.
6. There was a missing return statement in book.java for getLanguage.
7. The function addCopy in routeController had a meaningless httpStatus (I_AM_TEAPOT) replaced with more meaningful status (NOT_FOUND).
8. The AddCopy in book.java was empty so I added the implementation.
9. The checkout copy decreased the amount of times checked out rather than increasing it.
10. There was an error in mockAPI service constructor there was a error statement commented out.
11. In getAvailableBooks in RouteController http status did not reflect behavior.
