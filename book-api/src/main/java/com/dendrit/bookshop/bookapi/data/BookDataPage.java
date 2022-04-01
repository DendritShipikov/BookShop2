package com.dendrit.bookshop.bookapi.data;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class BookDataPage {

    @ApiModelProperty(notes = "List of books")
    private List<BookData> bookDataList;

    @ApiModelProperty("Total amount of books")
    private long count;

    @ApiModelProperty("Page size * index of the page")
    private long offset;

    public BookDataPage(List<BookData> bookDataList, long count, long offset) {
        this.bookDataList = bookDataList;
        this.count = count;
        this.offset = offset;
    }

    public List<BookData> getBookDataList() {
        return bookDataList;
    }

    public void setBookDataList(List<BookData> bookDataList) {
        this.bookDataList = bookDataList;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
