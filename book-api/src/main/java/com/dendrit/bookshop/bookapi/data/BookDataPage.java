package com.dendrit.bookshop.bookapi.data;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDataPage that = (BookDataPage) o;
        return count == that.count && offset == that.offset && Objects.equals(bookDataList, that.bookDataList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookDataList, count, offset);
    }
}
