package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;
import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.mappers.BookMapper;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    private BookMapper bookMapper;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public BookDataPage getAll(int page, int size) {
        Page<Book> books = bookRepository.findAll(PageRequest.of(page, size));
        return new BookDataPage(bookMapper.toDataList(books), books.getTotalElements(), (long) page * size);
    }

    @Override
    @Transactional
    public BookData getById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book with id = " + id + " does not exist"));
        return bookMapper.toData(book);
    }

    @Override
    @Transactional
    public BookDataPage searchByTitle(String query, int page, int size) {
        Page<Book> books = bookRepository.findByTitleContainingIgnoreCase(query, PageRequest.of(page, size));
        return new BookDataPage(bookMapper.toDataList(books), books.getTotalElements(), (long) page * size);
    }

    @Override
    @Transactional
    public BookDataPage getByUserId(Long userId, int page, int size) {
        Page<Book> books = bookRepository.findByUserId(userId, PageRequest.of(page, size));
        return new BookDataPage(bookMapper.toDataList(books), books.getTotalElements(), (long) page * size);
    }

    @Override
    @Transactional
    public void save(BookData bookData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        bookData.setId(null);
        bookData.setUserId(userData.getId());
        Book book = bookMapper.toEntity(bookData);
        book = bookRepository.save(book);
        bookData.setId(book.getId());
    }

    @Override
    @Transactional
    public void edit(BookData bookData, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        bookData.setId(id);
        bookData.setUserId(userData.getId());
        Book book = bookMapper.toEntity(bookData);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}
