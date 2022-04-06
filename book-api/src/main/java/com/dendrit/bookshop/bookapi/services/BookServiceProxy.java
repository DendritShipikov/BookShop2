package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;
import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.exceptions.UserHasNoAuthorityException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BookServiceProxy implements BookService {

    private BookRepository bookRepository;

    private BookService bookService;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBookService(@Qualifier("bookServiceImpl") BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public BookDataPage getAll(int page, int size) {
        return bookService.getAll(page, size);
    }

    @Override
    public BookData getById(Long id) {
        return bookService.getById(id);
    }

    @Override
    public BookDataPage searchByTitle(String query, int page, int size) {
        return bookService.searchByTitle(query, page, size);
    }

    @Override
    public BookDataPage getByUserId(Long userId, int page, int size) {
        return bookService.getByUserId(userId, page, size);
    }

    @Override
    public void save(BookData bookData) {
        bookService.save(bookData);
    }

    @Override
    public void edit(BookData bookData, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id = " + id + " does not exist"));
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) && !userData.getId().equals(book.getUserId())) {
            throw new UserHasNoAuthorityException("User has no authority to edit the book");
        }
        bookService.edit(bookData, id);
    }

    @Override
    public void deleteById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id = " + id + " does not exist"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) && !userData.getId().equals(book.getUserId())) {
            throw new UserHasNoAuthorityException("User has no authority to delete the book");
        }
        bookService.deleteById(id);
    }

}
