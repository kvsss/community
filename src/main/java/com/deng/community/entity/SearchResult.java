package com.deng.community.entity;

import java.util.List;

/**
 * @author :deng
 * @version :1.0
 * @description :
 * @since :1.8
 */
public class SearchResult<E> {
    private long total;
    private List<E> data;

    public SearchResult() {
    }

    public SearchResult(long total, List<E> data) {
        this.total = total;
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "total=" + total +
                ", data=" + data +
                '}';
    }
}
