package com.dendrit.bookshop.ordersapi.util;

import com.dendrit.bookshop.ordersapi.data.UserData;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static Long getUserId() {
        UserData userData = getUserData();
        return userData.getId();
    }

    public static UserData getUserData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserData) authentication.getPrincipal();
    }

}
