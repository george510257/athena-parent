package com.gls.athena.starter.amap.support;

import com.gls.athena.starter.amap.config.AmapProperties;
import com.gls.athena.starter.amap.domain.GeoRequest;
import com.gls.athena.starter.amap.domain.GeoResponse;
import com.gls.athena.starter.amap.domain.ReGeoRequest;
import com.gls.athena.starter.amap.domain.ReGeoResponse;
import com.gls.athena.starter.amap.feign.GeoCodeFeign;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 高德工具类
 *
 * @author george
 */
@Component
public class AmapHelper {
    /**
     * 高德配置
     */
    @Resource
    private AmapProperties amapProperties;
    /**
     * 地理编码feign
     */
    @Resource
    private GeoCodeFeign geoCodeFeign;

    /**
     * 地理编码
     *
     * @param address 地址
     * @param city    城市
     * @return 地理编码信息
     */
    public List<GeoResponse.GeoCode> getGeoCode(String address, String city) {
        String key = amapProperties.getKey();
        GeoRequest request = new GeoRequest();
        request.setKey(key);
        request.setAddress(address);
        request.setCity(city);
        GeoResponse geoResponse = geoCodeFeign.geoCode(request);
        return geoResponse.getGeoCodes();
    }

    /**
     * 逆地理编码
     *
     * @param location 经纬度
     * @return 逆地理编码信息
     */
    public ReGeoResponse.ReGeoCode getReGeoCode(String location) {
        String key = amapProperties.getKey();
        ReGeoRequest request = new ReGeoRequest();
        request.setKey(key);
        request.setLocation(location);
        ReGeoResponse reGeoResponse = geoCodeFeign.reGeoCode(request);
        return reGeoResponse.getReGeoCode();
    }
}

