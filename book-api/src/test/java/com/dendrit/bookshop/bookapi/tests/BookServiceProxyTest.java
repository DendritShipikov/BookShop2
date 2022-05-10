package com.dendrit.bookshop.bookapi.tests;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.exceptions.UserHasNoAuthorityException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.services.BookServiceImpl;
import com.dendrit.bookshop.bookapi.services.BookServiceProxy;
import com.dendrit.bookshop.core.usersclient.data.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class BookServiceProxyTest {

    BookServiceProxy bookServiceProxy;

    BookRepository bookRepository = Mockito.mock(BookRepository.class);

    Authentication authentication = Mockito.mock(Authentication.class);

    BookServiceImpl bookServiceImpl = Mockito.mock(BookServiceImpl.class);

    @BeforeEach
    public void setup() {
        bookServiceProxy = new BookServiceProxy();
        bookServiceProxy.setBookRepository(bookRepository);
        bookServiceProxy.setBookService(bookServiceImpl);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void editTest_IfNotFound() {
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(1L);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookServiceProxy.edit(new BookData(1L, "book", "author", 1L, 20)));
    }

    @Test
    public void editTest_IfHasNoAuthority() {
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(2L);
        Book book = new Book(1L, "book", "author", 1L, 20);
        BookData bookData = new BookData(1L, "book", "author", 1L, 20);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("PUBLISHER"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Assertions.assertThrows(UserHasNoAuthorityException.class, () -> bookServiceProxy.edit(bookData));
    }

    @Test
    public void editTest() {
        UserData userData = new UserData();
        userData.setUsername("publisher");
        userData.setId(1L);
        Book book = new Book(1L, "book", "author", 1L, 20);
        BookData bookData = new BookData(1L, "book", "author", 1L, 20);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("PUBLISHER"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookServiceImpl.edit(bookData)).thenReturn(bookData);
        Assertions.assertEquals(bookData, bookServiceProxy.edit(bookData));
        Mockito.verify(bookServiceImpl).edit(bookData);
    }

    @Test
    public void editTest_IfAdmin() {
        UserData userData = new UserData();
        userData.setUsername("admin");
        userData.setId(2L);
        Book book = new Book(1L, "book", "author", 1L, 20);
        BookData bookData = new BookData(1L, "book", "author", 1L, 20);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ADMIN"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(bookServiceImpl.edit(bookData)).thenReturn(bookData);
        Assertions.assertEquals(bookData, bookServiceProxy.edit(bookData));
        Mockito.verify(bookServiceImpl).edit(bookData);
    }

    @Test
    public void deleteByIdTest_IfNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> bookServiceProxy.deleteById(1L));
    }

    @Test
    public void deleteByIdTest_IfHasNoAuthority() {
        Book book = new Book(1L, "book", "author", 1L, 20);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        UserData userData = new UserData();
        userData.setUsername("publisher");
        userData.setId(2L);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("PUBLISHER"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        Assertions.assertThrows(UserHasNoAuthorityException.class, () -> bookServiceProxy.deleteById(1L));
    }

    @Test
    public void deleteByIdTest_IfAdmin() {
        Book book = new Book(1L, "book", "author", 1L, 20);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        UserData userData = new UserData();
        userData.setUsername("admin");
        userData.setId(2L);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ADMIN"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        bookServiceProxy.deleteById(1L);
        Mockito.verify(bookServiceImpl).deleteById(1L);
    }

    @Test
    public void deleteByIdTest() {
        Book book = new Book(1L, "book", "author", 1L, 20);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        UserData userData = new UserData();
        userData.setUsername("publisher");
        userData.setId(1L);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Collection<? extends GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("PUBLISHER"));
        Mockito.doReturn(authorities).when(authentication).getAuthorities();
        bookServiceProxy.deleteById(1L);
        Mockito.verify(bookServiceImpl).deleteById(1L);
    }

}
