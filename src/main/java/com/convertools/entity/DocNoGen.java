package com.convertools.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author fangkun
 * @date 2021/4/22 23:19
 * @description:
 */
@Data
@Builder
public class DocNoGen {
    private String docNo;
    private String mdbName;
}
