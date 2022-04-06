package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.entities.CartItemId;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private CartItemRepository cartItemRepository;

    private BookRepository bookRepository;

    @Autowired
    public void setCartItemRepository(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void addBookToCart(Long bookId) {
        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id = '" + bookId + "' not found"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        Long userId = userData.getId();
        CartItem cartItem = cartItemRepository.findById(new CartItemId(bookId, userId))
                .orElse(new CartItem(bookId, userId, 0));
        cartItem.setBookCount(cartItem.getBookCount() + 1);
        cartItem = cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteBookFromCart(Long bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        Long userId = userData.getId();
        CartItemId cartItemId = new CartItemId(bookId, userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BookNotFoundException("Book with id = '" + bookId + "' is not in users cart"));
        cartItem.setBookCount(cartItem.getBookCount() - 1);
        if (cartItem.getBookCount() == 0) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            cartItemRepository.save(cartItem);
        }
    }

    @Override
    public List<CartItemData> getCartByUserId(Long userId) {
        Iterable<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        List<CartItemData> cartItemDataList = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            Book book = bookRepository.findById(cartItem.getBookId())
                    .orElseThrow(() -> new BookNotFoundException("Book with id = '" + cartItem.getBookId() + "' not found"));
            CartItemData cartItemData = new CartItemData(
                    new BookData(book.getId(), book.getTitle(), book.getAuthor(), book.getUserId()),
                    cartItem.getBookCount());
            cartItemDataList.add(cartItemData);
        }
        return cartItemDataList;
    }

}
