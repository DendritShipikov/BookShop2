package com.dendrit.bookshop.bookapi.services;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.data.BookDataPage;

public interface BookService {

    BookDataPage getAll(int page, int size);

    BookData getById(Long id);

    BookDataPage searchByTitle(String query, int page, int size);

    BookDataPage getByUserId(Long userId, int page, int size);

    void save(BookData bookData);

    void edit(BookData bookData, Long id);

    void deleteById(Long id);

}
