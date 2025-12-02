package com.tour_web_app.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class SearchResult<T> {
    private final List<T> content;
    private final long totalElements;
    private final int pageNumber;
    private final int pageSize;
    private final boolean last;

    public SearchResult(Page<T> page){
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.pageNumber = page.getNumber() + 1;
        this.pageSize = page.getSize();
        this.last = page.isLast();
    }
}
