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

    public BookData() {}

    public BookData(Long id, String title, String author, Long userId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
                Objects.equals(id, bookData.id) &&
                Objects.equals(title, bookData.title) &&
                Objects.equals(author, bookData.author) &&
                Objects.equals(userId, bookData.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, userId);
    }
}
