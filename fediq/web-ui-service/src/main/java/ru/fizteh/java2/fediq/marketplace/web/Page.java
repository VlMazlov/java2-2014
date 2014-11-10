package ru.fizteh.java2.fediq.marketplace.web;

import java.util.Collections;
import java.util.List;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 13/10/14
 */
public class Page<T> {
    private final List<T> data;
    private final int totalCount;
    private final int pageNumber;

    public Page(List<T> data, int totalCount, int pageNumber) {
        this.pageNumber = pageNumber;
        this.data = Collections.unmodifiableList(data);
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
