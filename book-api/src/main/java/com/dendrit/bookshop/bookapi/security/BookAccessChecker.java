package com.dendrit.bookshop.bookapi.security;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.services.BookService;
import com.dendrit.bookshop.common.data.Role;
import com.dendrit.bookshop.common.data.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class BookAccessChecker {

    private BookService bookService;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    public boolean check(Authentication authentication, Long id) {
        UserData userData = (UserData) authentication.getPrincipal();
        if (userData.getRoles().contains(Role.ADMIN)) return true;
        if (!userData.getRoles().contains(Role.PUBLISHER)) return false;
        BookData bookData = bookService.getById(id);
        return Objects.equals(bookData.getUserId(), userData.getId());
    }
}
