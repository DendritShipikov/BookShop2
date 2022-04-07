package com.dendrit.bookshop.bookapi.data;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class BookData {

    @ApiModelProperty(notes = "Id")
    private Long id;

    @ApiModelProperty(notes = "Title")
    @NotNull
    @NotEmpty(message = "Book title can't be empty")
    private String title;

    @ApiModelProperty(notes = "Author(s)")
    @NotNull
    @NotEmpty(message = "Book author can't be empty")
    private String author;

    @ApiModelProperty(notes = "Id of user, who owns this book")
    private Long userId;

    private int count;

    public BookData() {}

    public BookData(Long id, String title, String author, Long userId, int count) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.userId = userId;
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public BookData setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookData setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public BookData setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Long getUserId() {
        return userId;
    }

    public BookData setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public int getCount() {
        return count;
    }

    public BookData setCount(int count) {
        this.count = count;
        return this;
    }

    @Override
    public String toString() {
        return "BookData{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookData bookData = (BookData) o;
        return
                count == bookData.count &&
                Objects.equals(id, bookData.id) &&
                Objects.equals(title, bookData.title) &&
                Objects.equals(author, bookData.author) &&
                Objects.equals(userId, bookData.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, userId, count);
    }

}
