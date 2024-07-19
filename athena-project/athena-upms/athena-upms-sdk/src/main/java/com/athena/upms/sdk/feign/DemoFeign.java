package com.athena.upms.sdk.feign;

import com.athena.common.core.base.IFeign;
import com.athena.upms.sdk.vo.DemoVo;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * demo数据 Feign
 */
@FeignClient(name = "athena-upms", contextId = "demo", path = "/demo")
public interface DemoFeign extends IFeign<DemoVo> {
}
