package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.common.audit.aspects.CalculateTime;
import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;
import com.dendrit.bookshop.bookapi.services.BookService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get page of books")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return page of books")
    })
    @GetMapping
    @CalculateTime(name = "GET /books")
    public BookDataPage books(
            @Parameter(description = "Index of page to be selected") @RequestParam Integer page,
            @Parameter(description = "Size of page") @RequestParam Integer size) {
        LOGGER.info("GET /books");
        return bookService.getAll(page, size);
    }

    @Operation(summary = "Get book by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return book"),
            @ApiResponse(code = 404, message = "Book not found")
    })
    @GetMapping("/{id}")
    @CalculateTime(name = "GET /books/{id}")
    public BookData getBook(@Parameter(description = "Id of book to be selected") @PathVariable Long id) {
        LOGGER.info("GET /books/" + id);
        return bookService.getById(id);
    }

    @Operation(summary = "Get page of books by search query")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return page of books"),
    })
    @GetMapping("/search/title")
    @CalculateTime(name = "GET /books/search/title")
    public BookDataPage searchBooksByTitle(
            @Parameter(description = "Search query") @RequestParam String query,
            @Parameter(description = "Index of page to be selected") @RequestParam Integer page,
            @Parameter(description = "Size of page") @RequestParam Integer size) {
        LOGGER.info("GET /books/search/title");
        return bookService.searchByTitle(query, page, size);
    }

    @Operation(summary = "Get page of books by user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return page of books"),
    })
    @GetMapping("/user/{userId}")
    @CalculateTime(name = "GET books/user/{userId}")
    public BookDataPage getBooksByUserId(
            @Parameter(description = "User id") @PathVariable Long userId,
            @Parameter(description = "Index of page to be selected") @RequestParam Integer page,
            @Parameter(description = "Size of page") @RequestParam Integer size) {
        LOGGER.info("GET /books/user/" + userId);
        return bookService.getByUserId(userId, page, size);
    }

    @Operation(summary = "Save book in database")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Book saved"),
            @ApiResponse(code = 403, message = "User hasn't authority to save book")
    })
    @PostMapping
    @CalculateTime(name = "POST /books")
    public ResponseEntity<BookData> saveBook(@Parameter(description = "Book to be saved") @RequestBody @Valid BookData bookData) {
        LOGGER.info("POST /books, book = {title = '" + bookData.getTitle() + "', author = '" + bookData.getAuthor() + "'}");
        bookService.save(bookData);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookData);
    }

    @Operation(summary = "Update book in database")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "Book updated"),
            @ApiResponse(code = 403, message = "User hasn't authority to update book")
    })
    @PutMapping("/{id}")
    @CalculateTime(name = "PUT /books/{id}")
    public ResponseEntity<BookData> editBook(
            @Parameter(description = "New book data") @RequestBody @Valid BookData bookData,
            @Parameter(description = "Id of book to be updated") @PathVariable Long id) {
        LOGGER.info("PUT /books/" + id + ", book = {title = '" + bookData.getTitle() + "', author = '" + bookData.getAuthor() + "'}");
        bookService.edit(bookData, id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bookData);
    }

    @Operation(summary = "Delete book from database")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Book deleted"),
            @ApiResponse(code = 403, message = "User hasn't authority to delete book")
    })
    @DeleteMapping("/{id}")
    @CalculateTime(name = "DELETE /books/{id}")
    public ResponseEntity<Void> deleteBook(@Parameter(description = "Id of book to be deleted") @PathVariable Long id) {
        LOGGER.info("DELETE /books/" + id);
        bookService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
