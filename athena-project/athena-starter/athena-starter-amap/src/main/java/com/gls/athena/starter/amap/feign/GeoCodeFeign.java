package com.gls.athena.starter.amap.feign;

import com.gls.athena.starter.amap.domain.GeoRequest;
import com.gls.athena.starter.amap.domain.GeoResponse;
import com.gls.athena.starter.amap.domain.RegeoRequest;
import com.gls.athena.starter.amap.domain.RegeoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 高德地图逆地理编码feign
 *
 * @author george
 */
@FeignClient(name = "amap", contextId = "geocode", path = "/geocode", url = "${amap.url}")
public interface GeoCodeFeign {
    /**
     * 地理编码
     *
     * @param request 地理编码请求
     * @return 地理编码响应
     */
    @GetMapping("/geo")
    GeoResponse geoCode(@SpringQueryMap GeoRequest request);

    /**
     * 逆地理编码
     *
     * @param request 逆地理编码请求
     * @return 逆地理编码响应
     */
    @GetMapping("/regeo")
    RegeoResponse regeoCode(@SpringQueryMap RegeoRequest request);
}
