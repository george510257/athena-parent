package com.athena.omega.mvp.component;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 面包屑
 *
 * @author george
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class Breadcrumb extends BaseComponent {
    /**
     * 首页
     */
    private String home;
    /**
     * 面包屑
     */
    private List<Map<String, Object>> crumbs = new ArrayList<>();
}
