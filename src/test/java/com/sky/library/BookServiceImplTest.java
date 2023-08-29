package com.sky.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class BookServiceImplTest {
    BookRepository bookRepository;
    BookService bookService;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepositoryStub();
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void testRetrieveBookInvalidReference() {
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.retrieveBook("INVALID-TEXT");
        });

        String expectedMessage = "Book reference must begin with BOOK-";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRetrieveBookMissingReference() {
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.retrieveBook("BOOK-999");
        });

        String expectedMessage = "Could not retrieve book with reference BOOK-999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRetrieveBookCorrectReference() throws BookNotFoundException {
        assertTrue(bookService.retrieveBook("BOOK-GRUFF472").getTitle() == "The Gruffalo");
    }

    @Test
    void testGetBookSummaryInvalidReference() {
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookSummary("INVALID-TEXT");
        });

        String expectedMessage = "Book reference must begin with BOOK-";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetBookSummaryMissingReference() {
        Exception exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookSummary("BOOK-999");
        });

        String expectedMessage = "BookNotFoundException: Could not retrieve Summary for book with reference BOOK-999";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testGetBookSummaryCorrectGruffaloReference() throws BookNotFoundException {
        assertEquals("[BOOK-GRUFF472] The Gruffalo - A mouse taking a walk in the woods.", bookService.getBookSummary("BOOK-GRUFF472"));
    }

    @Test
    void testGetBookSummaryCorrectWindReference() throws BookNotFoundException {
        assertEquals("[BOOK-WILL987] The Wind In The Willows - With the arrival of spring and fine weather outside...", bookService.getBookSummary("BOOK-WILL987"));
    }

    @Test
    void testGetBookSummaryCorrectPoohReference() throws BookNotFoundException {
        assertEquals("[BOOK-POOH222] Winnie The Pooh - In this first volume, we meet all the friends...", bookService.getBookSummary("BOOK-POOH222"));
    }
}