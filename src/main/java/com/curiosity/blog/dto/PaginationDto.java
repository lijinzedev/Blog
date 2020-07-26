package com.curiosity.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: lijinze
 * @createDate: 2020/7/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto<T> {

    private List<T> data;
    private boolean shoowPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages;

    public void setPagination(int totalPage, Integer page, Integer size) {


        this.currentPage = page;
        this.totalPage = totalPage;
        pages = new ArrayList<>();
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0, page - i);
            }
            if (page + i <= totalPage) {
                pages.add(page + i);
            }
        }

        // 是否展示上一页
        shoowPrevious = page != 1;
        // 是否展示下一页
        showNext = page != totalPage;
        // 是否展示第一页
        showFirstPage = !pages.contains(1);
        // 是否包含最后一页
        showEndPage = !pages.contains(totalPage);


    }
}
