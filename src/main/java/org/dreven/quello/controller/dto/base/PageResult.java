package org.dreven.quello.controller.dto.base;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResult<T> implements Serializable {

    private List<T> list;

    private Long total;

    protected Long pageSize;

    protected Long currentPage;

    protected Long totalPages;


    public PageResult() {

    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }

    public <U> PageResult<U> convert(Function<T, U> converter) {
        PageResult<U> res = new PageResult<>();
        res.total = this.total;
        res.pageSize = this.pageSize;
        res.currentPage = this.currentPage;
        res.totalPages = this.totalPages;
        res.list = this.list.stream().map(converter).collect(Collectors.toList());
        return res;
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

    public static <T> PageResult<T> empty(Long total) {
        return new PageResult<>(total);
    }

}
