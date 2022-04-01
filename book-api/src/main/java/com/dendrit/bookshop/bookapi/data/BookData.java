package com.dendrit.bookshop.bookapi.data;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
}
