package com.yupi.project.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 接口请求
 *
 * @author yupi
 */
@Data
public class IdRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}