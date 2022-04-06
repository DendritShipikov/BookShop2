package com.dendrit.bookshop.bookapi.mappers;

import com.dendrit.bookshop.bookapi.data.BookData;
import com.dendrit.bookshop.bookapi.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<Book, BookData> {

    @Override
    public Book toEntity(BookData data) {
        Book entity = new Book();
        entity.setId(data.getId());
        entity.setTitle(data.getTitle());
        entity.setAuthor(data.getAuthor());
        entity.setUserId(data.getUserId());
        entity.setCount(data.getCount());
        return entity;
    }

    @Override
    public BookData toData(Book entity) {
        BookData data = new BookData();
        data.setId(entity.getId());
        data.setTitle(entity.getTitle());
        data.setAuthor(entity.getAuthor());
        data.setUserId(entity.getUserId());
        data.setCount(entity.getCount());
        return data;
    }
}