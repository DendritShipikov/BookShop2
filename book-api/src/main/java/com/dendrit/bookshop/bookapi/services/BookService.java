package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;

public interface BookService {

    BookDataPage getAll(int page, int size);

    BookData getById(Long id);

    BookDataPage searchByTitle(String query, int page, int size);

    BookDataPage getByUserId(Long userId, int page, int size);

    BookData save(BookData bookData);

    BookData edit(BookData bookData);

    void deleteById(Long id);

}
