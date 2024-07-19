package com.athena.upms.boot.web.converter;

import com.athena.common.core.base.IConverter;
import com.athena.upms.boot.web.entity.DemoEntity;
import com.athena.upms.sdk.vo.DemoVo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Demo 转换器
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class DemoConverter implements IConverter<DemoVo, DemoEntity> {
}
