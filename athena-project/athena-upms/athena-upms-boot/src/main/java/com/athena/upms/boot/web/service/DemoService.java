package com.athena.upms.boot.web.service;

import com.athena.starter.mybatis.base.BaseService;
import com.athena.upms.boot.web.converter.DemoConverter;
import com.athena.upms.boot.web.entity.DemoEntity;
import com.athena.upms.boot.web.mapper.DemoMapper;
import com.athena.upms.sdk.vo.DemoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DemoService extends BaseService<DemoVo, DemoEntity, DemoConverter, DemoMapper> {
}
