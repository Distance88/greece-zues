package com.zhang.project.biz.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName PageUtil
 * @description
 * @date 2021-11-12 16:03
 */
public class PageUtils {

    //总记录数
    private int totalCount;

    //每页记录数
    private int pageSize;

    //总页数
    private int totalPage;

    //当前第几页
    private int currPage;

    //列表数据
    private List<?> list;

    public PageUtils(int totalCount, int pageSize, int totalPage, int currPage, List<?> list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.currPage = currPage;
        this.list = list;
    }

}
