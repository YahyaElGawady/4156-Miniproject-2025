package dev.coms4156.project.individualproject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.coms4156.project.individualproject.model.Book;
import dev.coms4156.project.individualproject.service.MockApiService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains the unit tests for the Mock API Service class.
 */
@SpringBootTest
public class MockApiServiceUnitTests {

    public MockApiService mockApiService;

    @BeforeEach
    public void setup() {
        mockApiService = new MockApiService();
    }

    @Test
    public void updateBooksTest() {
        assertEquals("All the mighty world :", mockApiService.getBooks().get(1).getTitle());
        Book newBook = new Book("Temp Title", 2);
        mockApiService.updateBook(newBook);
        assertEquals("Temp Title", mockApiService.getBooks().get(1).getTitle());
    }
}
