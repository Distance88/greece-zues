package com.zhang.project.web.vo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author Yaohang Zhang
 * @ClassName DeptVO
 * @description TODO
 * @date 2021-10-19 17:12
 */
@Data
@Builder
public class DeptVO {

    private String userOpenId;

    private String label;

    private List<DeptVO> children;

}
