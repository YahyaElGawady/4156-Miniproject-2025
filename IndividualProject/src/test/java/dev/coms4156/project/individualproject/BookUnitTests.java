package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.coms4156.project.individualproject.model.Book;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains the unit tests for the Book class.
 */
@SpringBootTest
public class BookUnitTests {

  public static Book book;

  @BeforeAll
  public static void setUpBookForTesting() {
    book = new Book("When Breath Becomes Air", 0);
  }

  @Test
  public void equalsBothAreTheSameTest() {
    Book cmpBook = book;
    assertEquals(cmpBook, book);
  }


  @Test
  public void deleteCopyWithOnlyOneCopyTest() {
    assertEquals(book.getCopiesAvailable(), 1);
    book.deleteCopy();
    assertEquals(book.getCopiesAvailable(), 0);
    boolean deleted = book.deleteCopy();
    assertEquals(deleted, false);
  }

  @Test
  public void checkGetAndSetMethodsTest() {
    assertEquals(book.getTitle(), "When Breath Becomes Air");
    book.setTitle("Call of the Wild");
    assertEquals(book.getTitle(), "Call of the Wild");
    book.setLanguage("English");
    assertEquals(book.getLanguage(), "English");
    book.setPublicationDate("9/12/25");
    assertEquals(book.getPublicationDate(), "9/12/25");

  }

  @Test
  public void equalsNullObjectTest() {
    assertEquals(book.equals(null), false);
  }

  @Test
  public void equalsDifferentSameBookIdObjectTest() {
    Book cmpBook = new Book("When Breath Becomes Air", 0);
    assertEquals(book.equals(cmpBook), true);
  }


  @Test
  public void checkoutCopyTest() {
    String dueDate = book.checkoutCopy();
    String expectedDueDate = LocalDate.now().plusWeeks(2).format(DateTimeFormatter.ISO_LOCAL_DATE);
    assertEquals(dueDate, expectedDueDate);
    assertEquals(book.checkoutCopy(), null);
    String falseDueDate = LocalDate.now().plusWeeks(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
    assertEquals(book.returnCopy(falseDueDate), false);
    assertEquals(book.returnCopy(expectedDueDate), true);
  }
}
