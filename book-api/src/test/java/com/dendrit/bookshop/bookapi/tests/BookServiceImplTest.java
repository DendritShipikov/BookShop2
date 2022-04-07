package com.dendrit.bookshop.bookapi.tests;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.mappers.BookMapper;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.services.BookServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public class BookServiceImplTest {

    BookServiceImpl bookService;

    BookRepository bookRepository = Mockito.mock(BookRepository.class);

    BookMapper bookMapper = Mockito.mock(BookMapper.class);

    Authentication authentication = Mockito.mock(Authentication.class);

    @BeforeEach
    public void setup() {
        bookService = new BookServiceImpl();
        bookService.setBookRepository(bookRepository);
        bookService.setBookMapper(bookMapper);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void getAllTest() {
        Pageable pageable = PageRequest.of(0, 2);
        Book book1 = new Book(1L, "title1", "author1", 1L, 13);
        Book book2 = new Book(2L, "title2", "author2", 2L, 4);
        List<Book> books = List.of(book1, book2);
        Page<Book> page = new PageImpl<>(books, pageable, 3);
        Mockito.when(bookRepository.findAll(PageRequest.of(0, 2))).thenReturn(page);
        BookData bookData1 =  new BookData(1L, "title1", "author1", 1L, 13);
        BookData bookData2 =  new BookData(2L, "title2", "author2", 2L, 4);
        List<BookData> bookDataList = List.of(bookData1, bookData2);
        Mockito.when(bookMapper.toDataList(page)).thenReturn(bookDataList);
        BookDataPage bookDataPage = bookService.getAll(0, 2);
        BookDataPage result = new BookDataPage(bookDataList, 3, 0);
        Assertions.assertEquals(result, bookDataPage);
    }

    @Test
    public void getByIdTest() {
        Book book1 = new Book(1L, "title1", "author1", 1L, 13);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        BookData bookData1 =  new BookData(1L, "title1", "author1", 1L, 13);
        Mockito.when(bookMapper.toData(book1)).thenReturn(bookData1);
        BookData bookData = bookService.getById(1L);
        Assertions.assertEquals(bookData1, bookData);
    }

    @Test
    public void getByIdTest_IfNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookService.getById(1L));
    }

    @Test
    public void searchByTitleTest() {
        String query = "title";
        Book book1 = new Book(1L, "title1", "author1", 1L, 13);
        Book book2 = new Book(2L, "title2", "author2", 2L, 4);
        List<Book> books = List.of(book1, book2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Book> page = new PageImpl<>(books, pageable, 3);
        Mockito.when(bookRepository.findByTitleContainingIgnoreCase(query, pageable)).thenReturn(page);
        BookData bookData1 =  new BookData(1L, "title1", "author1", 1L, 13);
        BookData bookData2 =  new BookData(2L, "title2", "author2", 2L, 4);
        List<BookData> bookDataList = List.of(bookData1, bookData2);
        Mockito.when(bookMapper.toDataList(page)).thenReturn(bookDataList);
        BookDataPage bookDataPage = bookService.searchByTitle(query, 0, 2);
        BookDataPage result = new BookDataPage(bookDataList, 3, 0);
        Assertions.assertEquals(result, bookDataPage);
    }

    @Test
    public void getByUserIdTest() {
        Long userId = 1L;
        Book book1 = new Book(1L, "title1", "author1", 1L, 13);
        Book book2 = new Book(2L, "title2", "author2", 1L, 4);
        List<Book> books = List.of(book1, book2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<Book> page = new PageImpl<>(books, pageable, 3);
        Mockito.when(bookRepository.findByUserId(userId, pageable)).thenReturn(page);
        BookData bookData1 =  new BookData(1L, "title1", "author1", 1L, 13);
        BookData bookData2 =  new BookData(2L, "title2", "author2", 1L, 4);
        List<BookData> bookDataList = List.of(bookData1, bookData2);
        Mockito.when(bookMapper.toDataList(page)).thenReturn(bookDataList);
        BookDataPage bookDataPage = bookService.getByUserId(userId, 0, 2);
        BookDataPage result = new BookDataPage(bookDataList, 3, 0);
        Assertions.assertEquals(result, bookDataPage);
    }

}
