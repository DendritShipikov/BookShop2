package com.dendrit.bookshop.bookapi.controllers;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;
import com.dendrit.bookshop.bookapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public BookDataPage books(@RequestParam Integer page, @RequestParam Integer size) {
        return bookService.getAll(page, size);
    }

    @GetMapping("/{id}")
    public BookData getBook(@PathVariable Long id) {
        return bookService.getById(id);
    }

    @GetMapping("/search/title")
    public BookDataPage searchBooksByTitle(@RequestParam String query, @RequestParam Integer page, @RequestParam Integer size) {
        return bookService.searchByTitle(query, page, size);
    }

    @GetMapping("/user/{userId}")
    public BookDataPage getBooksByUserId(@PathVariable Long userId, @RequestParam Integer page, @RequestParam Integer size) {
        return bookService.getByUserId(userId, page, size);
    }

    @PostMapping
    public ResponseEntity<BookData> saveBook(@RequestBody BookData bookData) {
        bookService.save(bookData);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookData);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookData> editBook(@RequestBody BookData bookData, @PathVariable Long id) {
        bookService.edit(bookData, id);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(bookData);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

}
