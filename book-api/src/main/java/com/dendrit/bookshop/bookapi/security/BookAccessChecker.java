package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.UserData;
import com.dendrit.bookshop.bookapi.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookAccessChecker {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public boolean check(Authentication authentication, Long id) {
        Set<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        if (authorities.contains("ADMIN")) return true;
        if (!authorities.contains("PUBLISHER")) return false;
        UserData userData = (UserData) authentication.getPrincipal();
        BookData bookData = bookService.getById(id);
        return Objects.equals(bookData.getUserId(), userData.getId());
    }
}
