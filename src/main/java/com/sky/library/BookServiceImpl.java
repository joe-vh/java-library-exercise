package com.sky.library;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl (BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book retrieveBook(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith("BOOK-")) {
            throw new BookNotFoundException("Book reference must begin with BOOK-");
        }

        Book book = bookRepository.retrieveBook(bookReference);

        if (book != null) {
            return book;
        } else {
            throw new BookNotFoundException("Could not retrieve book with reference " + bookReference);
        }
    }

    public String getBookSummary(String bookReference) throws BookNotFoundException {
        if (!bookReference.startsWith("BOOK-")) {
            throw new BookNotFoundException("Book reference must begin with BOOK-");
        }

        Book book = bookRepository.retrieveBook(bookReference);

        if (book != null) {
            String[] reviewWords = book.getReview().split(" ");
            String ellipsis = reviewWords.length > 9 ? "..." : "";
            String reviewSummary = String.join(" ", Arrays.copyOfRange(reviewWords, 0, reviewWords.length < 9 ? reviewWords.length : 9));
            char lastChar = reviewSummary.charAt(reviewSummary.length() - 1);

            if (reviewWords.length > 9 && !Character.isDigit(lastChar) && !Character.isLetter(lastChar)) {
                reviewSummary = StringUtils.chop(reviewSummary);
            }

            // format + reference + title + first nine words + ellipsis
            return "[" + book.getReference() + "] " + book.getTitle() + " - " + reviewSummary + ellipsis;
        } else {
            throw new BookNotFoundException("Could not retrieve Summary for book with reference " + bookReference);
        }
    }
}
