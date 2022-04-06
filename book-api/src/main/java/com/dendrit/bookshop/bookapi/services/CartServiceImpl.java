package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.entities.CartItemId;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.exceptions.IllegalBookCountException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    @Transactional
    public void addBookToCart(Long bookId, int bookCount) {
        if (bookCount <= 0) throw new IllegalBookCountException("bookCount <= 0");
        bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book with id = '" + bookId + "' not found"));
        Long userId = getUserId();
        CartItem cartItem = cartItemRepository.findById(new CartItemId(bookId, userId))
                .orElse(new CartItem(bookId, userId, 0));
        cartItem.setBookCount(bookCount);
        cartItem = cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void deleteBookFromCart(Long bookId) {
        Long userId = getUserId();
        CartItemId cartItemId = new CartItemId(bookId, userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new BookNotFoundException("Book with id = '" + bookId + "' is not in users cart"));
        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public List<CartItemData> getCartByUserId() {
        Long userId = getUserId();
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        List<Long> bookIds = cartItems.stream().map(CartItem::getBookId).collect(Collectors.toList());
        List<Book> books = bookRepository.findAllById(bookIds);
        List<CartItemData> cartItemDataList = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); ++i) {
            Book book = books.get(i);
            BookData bookData = new BookData(book.getId(), book.getTitle(), book.getAuthor(), book.getUserId());
            cartItemDataList.add(new CartItemData(bookData, cartItems.get(i).getBookCount()));
        }
        return cartItemDataList;
    }

    private Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserData userData = (UserData) authentication.getPrincipal();
        return userData.getId();
    }

}
