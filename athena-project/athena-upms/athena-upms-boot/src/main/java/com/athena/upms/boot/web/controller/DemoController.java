package com.athena.upms.boot.web.controller;

import com.athena.starter.web.base.BaseController;
import com.athena.upms.boot.web.service.DemoService;
import com.athena.upms.sdk.feign.DemoFeign;
import com.athena.upms.sdk.vo.DemoVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo数据 控制器
 */
@Slf4j
@RestController
@RequestMapping("/demo")
@Tag(name = "demo", description = "demo数据")
public class DemoController extends BaseController<DemoVo, DemoService>
        implements DemoFeign {
}
