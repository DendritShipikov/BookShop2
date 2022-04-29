package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.entities.Book;
import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.exceptions.IllegalBookCountException;
import com.dendrit.bookshop.bookapi.repositories.BookRepository;
import com.dendrit.bookshop.bookapi.repositories.CartItemRepository;
import com.dendrit.bookshop.bookapi.security.OrderData;
import com.dendrit.bookshop.bookapi.security.OrderItemData;
import com.dendrit.bookshop.bookapi.security.OrderStatus;
import com.dendrit.bookshop.bookapi.util.UserUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BuyServiceImpl implements BuyService {

    private CartItemRepository cartItemRepository;

    private BookRepository bookRepository;

    private RabbitTemplate rabbitTemplate;

    @Value("${orders-api.key}")
    private String queueName;

    @Autowired
    public void setCartItemRepository(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    @Transactional
    public void buyAllFromCart() {
        UserData userData = UserUtil.getUserData();
        Long userId = userData.getId();
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
        List<Long> ids = cartItems.stream().map(CartItem::getBookId).collect(Collectors.toList());
        List<Book> books = bookRepository.findAllById(ids);
        Map<Long, Integer> bookIdToCount = new HashMap<>();
        for (CartItem cartItem : cartItems) {
            bookIdToCount.put(cartItem.getBookId(), cartItem.getBookCount());
        }
        OrderData orderData = new OrderData();
        orderData.setOrderItemDataList(new ArrayList<>());
        orderData.setDate(new Date());
        orderData.setStatus(OrderStatus.IN_PROCESS);
        orderData.setUserId(userId);
        for (Book book : books) {
            int count = bookIdToCount.get(book.getId());
            if (count > book.getCount()) throw new IllegalBookCountException("provided bookCount greater then in database");
            book.setCount(book.getCount() - count);
            OrderItemData orderItemData = new OrderItemData();
            orderItemData.setBookId(book.getId());
            orderItemData.setBookCount(count);
            orderData.getOrderItemDataList().add(orderItemData);
        }
        bookRepository.saveAll(books);
        cartItemRepository.deleteAllByUserId(userId);
        rabbitTemplate.convertAndSend(queueName, orderData);
    }

}
