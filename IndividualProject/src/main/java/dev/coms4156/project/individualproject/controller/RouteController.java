package dev.coms4156.project.individualproject.controller;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * The {@code} Route Controller acts as the main controller for
 * HTTP requests regarding the book data.
 *
 *
 * @author yahya
 * @version 1.0.0
 * @since 9/12/2025
 */
@RestController
public class RouteController {

  private final MockApiService mockApiService;

  public RouteController(MockApiService mockApiService) {
    this.mockApiService = mockApiService;
  }

  @GetMapping({"/", "/index"})
  public String index() {
    return "Welcome to the home page! In order to make an API call direct your browser"
        + "or Postman to an endpoint.";
  }

  /**
   * Returns the details of the specified book.
   *
   * @param id An {@code int} representing the unique identifier of the book to retrieve.
   *
   * @return A {@code ResponseEntity} containing either the matching {@code Book} object with an
   *         HTTP 200 response, or a message indicating that the book was not
   *         found with an HTTP 404 response.
   */
  @GetMapping({"/book/{id}"})
  public ResponseEntity<?> getBook(@PathVariable int id) {
    for (Book book : mockApiService.getBooks()) {
      if (book.getId() == id) {
        return new ResponseEntity<>(book, HttpStatus.OK);
      }
    }

    return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
  }

  /**
   * Get and return a list of all the books with available copies.
   *
   * @return A {@code ResponseEntity} containing a list of available {@code Book} objects with an
   *         HTTP 200 response if sucessful, or a message indicating an error occurred with an
   *         HTTP 500 response.
   */
  @GetMapping({"/books/available"}
  )
  //@PutMapping({"/books/available"})
  public ResponseEntity<?> getAvailableBooks() {
    try {
      ArrayList<Book> availableBooks = new ArrayList<>();

      for (Book book : mockApiService.getBooks()) {
        if (book.hasCopies()) {
          availableBooks.add(book);
        }
      }

      return new ResponseEntity<>(mockApiService.getBooks(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>("Error occurred when getting all available books",
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Adds a copy to the {@code} Book object if it exists.
   *
   * @param bookId An {@code Integer} representing the unique id of the book.
   * @return A {@code ResponseEntity} containing the updated {@code Book} object with an
   *         HTTP 200 response if successful or HTTP 404 if the book is not found,
   *         or a message indicating an error occurred with an HTTP 500 code.
   */
  @GetMapping({"/book/{bookId}/add"})
  //@PatchMapping({"/book/{bookId}/add"})
  public ResponseEntity<?> addCopy(@PathVariable Integer bookId) {
    try {
      for (Book book : mockApiService.getBooks()) {
        if (bookId.equals(book.getId())) {
          book.addCopy();
          return new ResponseEntity<>(book, HttpStatus.OK);
        }
      }

      return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>("Error in making copy.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  /**
   * Returns a list of exactly 10 unique books
   *
   * @return A list of 10 unique books half based on popularity and half based randomly
   *
   */
  @GetMapping({"/books/recommendation"})
  //@PatchMapping({"/books/recommendation"})
  public ResponseEntity<?> getRecommendations() {
    try {
      List<Book> books = new ArrayList<Book>(mockApiService.getBooks());

      if(books.size() < 10){
        return new ResponseEntity<>("Not enough books.", HttpStatus.BAD_REQUEST);
      }

      Collections.sort(books, new Comparator<Book>() {
        public int compare (Book x, Book y){
          return Integer.compare(y.getAmountOfTimesCheckedOut(), x.getAmountOfTimesCheckedOut());
        }
      });
      List<Book> recommendations = new ArrayList<Book>();
      for(int i = 0; i < 5; i++){
        recommendations.add(books.get(i));
      }
      while(recommendations.size() != 10){
        int random = (int)(Math.random() * (books.size() - 5)) + 5;
        Book randomBook = books.get(random);
        if(!recommendations.contains(randomBook))
          recommendations.add(randomBook);


      }
      return new ResponseEntity<>(recommendations, HttpStatus.OK);
    }
    catch(Exception e) {
      return  new ResponseEntity<>("Unable to generate recommendations", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  @GetMapping({"/book/{bookId}/checkout"})
  //@PatchMapping({"/book/{bookId}/checkout"})
  public ResponseEntity<?> checkout(@PathVariable Integer bookId) {
    try {
      for (Book book : mockApiService.getBooks()) {
        if (bookId.equals(book.getId())) {
          if (book.hasCopies()) {
            book.checkoutCopy();
            return new ResponseEntity<>(book, HttpStatus.OK);
          } else {
            return new ResponseEntity<>("No copies available to checkout.", HttpStatus.BAD_REQUEST);
          }
        }
      }
      return new ResponseEntity<>("Book not found.", HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>("Error processing checkout.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
