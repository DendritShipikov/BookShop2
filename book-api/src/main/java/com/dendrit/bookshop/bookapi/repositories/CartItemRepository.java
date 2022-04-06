package com.dendrit.bookshop.bookapi.repositories;

import com.dendrit.bookshop.bookapi.entities.CartItem;
import com.dendrit.bookshop.bookapi.entities.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

    List<CartItem> findByUserId(Long userId);

    void deleteAllByUserId(Long userId);

}
