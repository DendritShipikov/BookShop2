package com.dendrit.bookshop.bookapi.tests;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.entities.CartItemId;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.exceptions.IllegalBookCountException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.repositories.CartItemRepository;
import com.dendrit.bookshop.bookapi.services.CartServiceImpl;
import com.dendrit.bookshop.core.usersclient.data.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

public class CartServiceTest {

    BookRepository bookRepository = Mockito.mock(BookRepository.class);

    CartItemRepository cartItemRepository = Mockito.mock(CartItemRepository.class);

    Authentication authentication = Mockito.mock(Authentication.class);

    CartServiceImpl cartService;

    @BeforeEach
    public void setup() {
        cartService = new CartServiceImpl();
        cartService.setBookRepository(bookRepository);
        cartService.setCartItemRepository(cartItemRepository);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void addBookToCartTest_IfIllegalBookCount() {
        Assertions.assertThrows(IllegalBookCountException.class, () -> cartService.addBookToCart(1L, -1));
    }

    @Test
    public void addBookToCartTest_IfNotFound() {
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> cartService.addBookToCart(1L, 2));
    }

    @Test
    public void addBookToCartTest_IfExists() {
        Book book = new Book(1L, "book", "author", 1L, 20);
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(1L);
        CartItem cartItem = new CartItem(1L, 1L, 2);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findById(new CartItemId(1L, 1L))).thenReturn(Optional.of(cartItem));
        cartService.addBookToCart(1L, 3);
        Mockito.verify(cartItemRepository).save(new CartItem(1L, 1L, 3));
    }

    @Test
    public void addBookToCartTest_IfNotExists() {
        Book book = new Book(1L, "book", "author", 1L, 20);
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findById(new CartItemId(1L, 1L))).thenReturn(Optional.empty());
        cartService.addBookToCart(1L, 3);
        Mockito.verify(cartItemRepository).save(new CartItem(1L, 1L, 3));
    }

    @Test
    public void deleteBookFromCartTest_IfNotFound() {
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(1L);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findById(new CartItemId(7L, 1L))).thenReturn(Optional.empty());
        Assertions.assertThrows(BookNotFoundException.class, () -> cartService.deleteBookFromCart(7L));
    }

    @Test
    public void deleteBookFromCartTest() {
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(1L);
        CartItem cartItem = new CartItem(7L, 1L, 3);
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findById(new CartItemId(7L, 1L))).thenReturn(Optional.of(cartItem));
        cartService.deleteBookFromCart(7L);
        Mockito.verify(cartItemRepository).delete(cartItem);
    }

    @Test
    public void getCartTest() {
        UserData userData = new UserData();
        userData.setUsername("user");
        userData.setId(1L);
        List<CartItem> cartItems = List.of(new CartItem(10L, 1L, 2), new CartItem(11L, 1L, 3));
        List<Long> ids = List.of(10L, 11L);
        List<Book> books = List.of(
                new Book(10L, "book1", "author", 5L, 30),
                new Book(11L, "book2", "author", 8L, 40)
        );
        Mockito.when(authentication.getPrincipal()).thenReturn(userData);
        Mockito.when(cartItemRepository.findByUserId(1L)).thenReturn(cartItems);
        Mockito.when(bookRepository.findAllById(ids)).thenReturn(books);
        List<CartItemData> cartItemDataList = cartService.getCart();
        List<CartItemData> expected = List.of(
                new CartItemData(new BookData(10L, "book1", "author", 5L, 30), 2),
                new CartItemData(new BookData(11L, "book2", "author", 8L, 40), 3)
        );
        Assertions.assertEquals(expected, cartItemDataList);
    }

}
