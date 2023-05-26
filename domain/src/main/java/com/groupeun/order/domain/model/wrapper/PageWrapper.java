package com.groupeun.order.domain.model.wrapper;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Data
public class PageWrapper <T> implements Serializable {
    private int total;
    private List<T> content = new ArrayList();
    private final PageableWrapper PageableWrapper;

    public int getNumber() {
        return this.PageableWrapper.isPaged() ? this.PageableWrapper.getPageNumber() : 0;
    }

    public int getSize() {
        return this.PageableWrapper.isPaged() ? this.PageableWrapper.getPageSize() : this.content.size();
    }

    public int getNumberOfElements() {
        return this.content.size();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int)Math.ceil((double)this.total / (double)this.getSize());
    }
    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    public PageableWrapper getPageableWrapper() {
        return this.PageableWrapper;
    }

    public Iterator<T> iterator() {
        return this.content.iterator();
    }

}
