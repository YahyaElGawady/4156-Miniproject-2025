package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.coms4156.project.individualproject.controller.RouteController;
import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * This class contains the unit tests for the Route Controller class.
 */
@SpringBootTest
public class RouteControllerUnitTests {

    public RouteController routeController;
    public MockApiService mockApiService;

    public Book testBook;

    @BeforeEach
    void setUp() {
        mockApiService = new MockApiService();
        routeController = new RouteController(mockApiService);
    }

    @Test
    void getBookValidTest() {
        testBook = new Book("All the mighty world :", 2);
        ResponseEntity<?> response = routeController.getBook(2);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testBook, response.getBodygit ());
    }

    @Test
    void getBookInvalidTest() {
        ResponseEntity<?> response = routeController.getBook(-1);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Book not found.", response.getBody());
    }

    @Test
    void makeCopyValidTest() {
        testBook = mockApiService.getBooks().get(1);
        ResponseEntity<?> response = routeController.addCopy(2);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(testBook, response.getBody());
    }

    @Test
    void makeCopyInvalidTest() {
        ResponseEntity<?> response = routeController.addCopy(-1);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Book not found.", response.getBody());
    }
}
