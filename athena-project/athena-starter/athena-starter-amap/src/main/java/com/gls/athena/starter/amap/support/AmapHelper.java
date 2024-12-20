package com.gls.athena.starter.amap.support;

import com.gls.athena.starter.amap.config.AmapProperties;
import com.gls.athena.starter.amap.domain.GeoRequest;
import com.gls.athena.starter.amap.domain.GeoResponse;
import com.gls.athena.starter.amap.domain.RegeoRequest;
import com.gls.athena.starter.amap.domain.RegeoResponse;
import com.gls.athena.starter.amap.domain.dto.Geocode;
import com.gls.athena.starter.amap.domain.dto.Regeocode;
import com.gls.athena.starter.amap.feign.GeocodeFeign;
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
    private GeocodeFeign geoCodeFeign;

    /**
     * 地理编码
     *
     * @param address 地址
     * @param city    城市
     * @return 地理编码信息
     */
    public List<Geocode> getGeocodes(String address, String city) {
        String key = amapProperties.getKey();
        GeoRequest request = new GeoRequest();
        request.setKey(key);
        request.setAddress(address);
        request.setCity(city);
        GeoResponse geoResponse = geoCodeFeign.geo(request);
        return geoResponse.getGeocodes();
    }

    /**
     * 逆地理编码
     *
     * @param location 经纬度
     * @return 逆地理编码信息
     */
    public Regeocode getRegeocode(String location) {
        String key = amapProperties.getKey();
        RegeoRequest request = new RegeoRequest();
        request.setKey(key);
        request.setLocation(location);
        RegeoResponse reGeoResponse = geoCodeFeign.regeo(request);
        return reGeoResponse.getRegeocode();
    }
}

