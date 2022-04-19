package com.dendrit.bookshop.bookapi.tests;

import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.exceptions.IllegalBookCountException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.repositories.CartItemRepository;
import com.dendrit.bookshop.bookapi.services.BuyServiceImpl;
import com.dendrit.bookshop.notificationclient.client.NotificationClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class BuyServiceTest {

    CartItemRepository cartItemRepository = Mockito.mock(CartItemRepository.class);

    BookRepository bookRepository = Mockito.mock(BookRepository.class);

    NotificationClient notificationClient = Mockito.mock(NotificationClient.class);

    Authentication authentication = Mockito.mock(Authentication.class);

    BuyServiceImpl buyService;

    @BeforeEach
    public void setup() {
        buyService = new BuyServiceImpl();
        buyService.setBookRepository(bookRepository);
        buyService.setCartItemRepository(cartItemRepository);
        buyService.setNotificationClient(notificationClient);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void buyAllFromCartTest() {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setUsername("user");
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        List<CartItem> cartItems = List.of(new CartItem(10L, 1L, 2), new CartItem(12L, 1L, 3));
        Mockito.when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);
        List<Long> ids = List.of(10L, 12L);
        Book book1 = new Book(10L, "book1", "author", 1L, 20);
        Book book2 = new Book(12L, "book2", "author", 1L, 20);
        List<Book> books = List.of(book1, book2);
        Mockito.when(bookRepository.findAllById(ids)).thenReturn(books);
        buyService.buyAllFromCart();
        Mockito.verify(authentication).getPrincipal();
        Mockito.verify(bookRepository).findAllById(ids);
        Mockito.verify(cartItemRepository).deleteAllByUserId(1L);
        Mockito.verify(cartItemRepository).findByUserId(1L);
        Mockito.verify(notificationClient).send(Mockito.any());
    }

    @Test
    public void buyAllFromCartTest_IfIllegalBookCount() {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setUsername("user");
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        List<CartItem> cartItems = List.of(new CartItem(10L, 1L, 2), new CartItem(12L, 1L, 3));
        Mockito.when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);
        List<Long> ids = List.of(10L, 12L);
        Book book1 = new Book(10L, "book1", "author", 1L, 2);
        Book book2 = new Book(12L, "book2", "author", 1L, 2);
        List<Book> books = List.of(book1, book2);
        Mockito.when(bookRepository.findAllById(ids)).thenReturn(books);
        Assertions.assertThrows(IllegalBookCountException.class, () -> buyService.buyAllFromCart());
        Mockito.verify(authentication).getPrincipal();
        Mockito.verify(bookRepository).findAllById(ids);
        Mockito.verify(cartItemRepository).findByUserId(1L);
    }

}
