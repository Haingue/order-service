package com.groupeun.order.domain.model.wrapper;

import lombok.Data;

@Data
public class PageableWrapper {
    private final int page;
    private final int size;

    public PageableWrapper(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        } else if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        } else {
            this.page = page;
            this.size = size;
        }
    }

    public int getPageSize() {
        return this.size;
    }

    public int getPageNumber() {
        return this.page;
    }

    public long getOffset() {
        return (long)this.page * (long)this.size;
    }

    public boolean hasPrevious() {
        return this.page > 0;
    }

    public PageableWrapper previousOrFirst() {
        return this.hasPrevious() ? this.previous() : this.first();
    }

    public PageableWrapper next() {
        return new PageableWrapper(this.getPageNumber() + 1, this.getPageSize());
    }

    public PageableWrapper previous() {
        return this.getPageNumber() == 0 ? this : new PageableWrapper(this.getPageNumber() - 1, this.getPageSize());
    }

    public PageableWrapper first() {
        return new PageableWrapper(0, this.getPageSize());
    }
    public boolean isPaged () { return true; };
}
