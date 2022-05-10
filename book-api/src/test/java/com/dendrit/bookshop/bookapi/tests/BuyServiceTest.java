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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        //buyService.setNotificationClient(notificationClient);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @ParameterizedTest
    @Disabled
    @MethodSource("buyAllFromCartProvider")
    public void buyAllFromCartTest(List<CartItem> cartItems, List<Book> books, List<Book> result) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setUsername("user");
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);
        List<Long> ids = cartItems.stream().map(CartItem::getBookId).collect(Collectors.toList());
        Mockito.when(bookRepository.findAllById(ids)).thenReturn(books);
        buyService.buyAllFromCart();
        Mockito.verify(authentication).getPrincipal();
        Mockito.verify(bookRepository).findAllById(ids);
        Mockito.verify(bookRepository).saveAll(result);
        Mockito.verify(cartItemRepository).deleteAllByUserId(1L);
        Mockito.verify(cartItemRepository).findByUserId(1L);
        Mockito.verify(notificationClient).send(Mockito.any());
    }

    public static Stream<Arguments> buyAllFromCartProvider() {
        return Stream.of(
                Arguments.arguments(
                        List.of(
                                new CartItem(10L, 1L, 2),
                                new CartItem(12L, 1L, 3)
                        ),
                        List.of(
                                new Book(10L, "book1", "author", 3L, 20),
                                new Book(12L, "book2", "author", 5L, 20)
                        ),
                        List.of(
                                new Book(10L, "book1", "author", 3L, 18),
                                new Book(12L, "book2", "author", 5L, 17)
                        )
                ),
                Arguments.arguments(
                        List.of(
                                new CartItem(10L, 1L, 3),
                                new CartItem(11L, 1L, 5),
                                new CartItem(12L, 1L, 2)
                        ),
                        List.of(
                                new Book(10L, "book1", "author", 3L, 40),
                                new Book(11L, "book2", "author", 7L, 40),
                                new Book(12L, "book3", "author", 5L, 30)
                        ),
                        List.of(
                                new Book(10L, "book1", "author", 3L, 37),
                                new Book(11L, "book2", "author", 7L, 35),
                                new Book(12L, "book3", "author", 5L, 28)
                        )
                )
        );
    }

    @ParameterizedTest
    @Disabled
    @MethodSource("buyAllFromCartIfIllegalBookCountProvider")
    public void buyAllFromCartTest_IfIllegalBookCount(List<CartItem> cartItems, List<Book> books) {
        UserData userData = new UserData();
        userData.setId(1L);
        userData.setUsername("user");
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);
        List<Long> ids = cartItems.stream().map(CartItem::getBookId).collect(Collectors.toList());
        Mockito.when(bookRepository.findAllById(ids)).thenReturn(books);
        Assertions.assertThrows(IllegalBookCountException.class, () -> buyService.buyAllFromCart());
        Mockito.verify(authentication).getPrincipal();
        Mockito.verify(bookRepository).findAllById(ids);
        Mockito.verify(cartItemRepository).findByUserId(1L);
    }

    public static Stream<Arguments> buyAllFromCartIfIllegalBookCountProvider() {
        return Stream.of(
                Arguments.arguments(
                        List.of(
                                new CartItem(10L, 1L, 2),
                                new CartItem(12L, 1L, 3)
                        ),
                        List.of(
                                new Book(10L, "book1", "author", 3L, 2),
                                new Book(12L, "book2", "author", 5L, 2)
                        )
                ),
                Arguments.arguments(
                        List.of(
                                new CartItem(10L, 1L, 3),
                                new CartItem(11L, 1L, 5),
                                new CartItem(12L, 1L, 2)
                        ),
                        List.of(
                                new Book(10L, "book1", "author", 3L, 4),
                                new Book(11L, "book2", "author", 7L, 4),
                                new Book(12L, "book3", "author", 5L, 3)
                        )
                )
        );
    }

}
