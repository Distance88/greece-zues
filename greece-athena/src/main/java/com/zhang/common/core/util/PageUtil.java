package com.zhang.common.core.util;

import org.springframework.data.domain.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author yanlv
 * @version 0.1 : PageUtil v0.1 2016/12/25 上午10:15 yanlv Exp $
 */

public class PageUtil {

    public static final <T> Page toPage(List<T> list) {

        PageImpl<T> page = new PageImpl(list);

        return page;

    }

    public static final <T> Page toPage(List<T> list, int pageNumer, int pageSize, long total) {

        PageImpl<T> page = new PageImpl(list, PageUtil.toPageRequest(pageNumer, pageSize), total);

        return page;

    }

    /**
     * 转化为page 请求
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static final Pageable toPageRequest(Integer pageNumber, Integer pageSize) {

        Integer realPage = pageNumber <= 1 ? 0 : pageNumber - 1;

        Pageable pageable = PageRequest.of(realPage, pageSize);

        return pageable;

    }

    /**
     * 转化为page 请求
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static final Pageable toPageRequest(Integer pageNumber, Integer pageSize,
                                               Map<String, Sort.Direction> orderBy) {

        Integer realPage = pageNumber <= 1 ? 0 : pageNumber - 1;

        Pageable pageable;

        if (orderBy != null) {

            List<Sort.Order> orderList = orderBy.keySet().stream().map(key -> {
                return new Sort.Order(orderBy.get(key), key);
            }).collect(Collectors.toList());

            Sort sort = Sort.by(orderList);

            pageable =  PageRequest.of(realPage, pageSize, sort);
        } else {
            pageable =  PageRequest.of(realPage, pageSize);
        }

        return pageable;

    }

    /**
     * 转化为page 请求
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public static final Pageable toPageRequest(Integer pageNumber, Integer pageSize, Sort sort) {

        Integer realPage = pageNumber <= 1 ? 0 : pageNumber - 1;

        Pageable pageable;

        if (sort != null) {
            pageable =  PageRequest.of(realPage, pageSize, sort);
        } else {
            pageable =  PageRequest.of(realPage, pageSize);
        }

        return pageable;

    }

}
