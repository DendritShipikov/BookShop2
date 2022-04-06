package com.dendrit.bookshop.bookapi.repositories;

import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.entities.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    Iterable<CartItem> findByUserId(Long userId);

}
