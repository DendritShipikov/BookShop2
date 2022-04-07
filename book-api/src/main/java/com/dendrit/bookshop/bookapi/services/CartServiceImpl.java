package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.CartItemData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.entities.CartItemId;
import com.dendrit.bookshop.bookapi.exceptions.BookNotFoundException;
import com.dendrit.bookshop.bookapi.exceptions.IllegalBookCountException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.repositories.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dendrit.bookshop.bookapi.util.UserUtil.getUserId;

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
    public List<CartItemData> getCart() {
        Long userId = getUserId();
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        Map<Long, Integer> bookIdToBookCountMap = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            bookIdToBookCountMap.put(cartItem.getBookId(), cartItem.getBookCount());
        }
        List<Long> bookIds = cartItems.stream().map(CartItem::getBookId).collect(Collectors.toList());
        List<Book> books = bookRepository.findAllById(bookIds);
        List<CartItemData> cartItemDataList = new ArrayList<>();
        for (Book book : books) {
            BookData bookData = new BookData()
                    .setUserId(book.getUserId())
                    .setId(book.getId())
                    .setAuthor(book.getAuthor())
                    .setTitle(book.getTitle())
                    .setCount(book.getCount());
            cartItemDataList.add(new CartItemData(bookData, bookIdToBookCountMap.get(bookData.getId())));
        }
        return cartItemDataList;
    }

}
