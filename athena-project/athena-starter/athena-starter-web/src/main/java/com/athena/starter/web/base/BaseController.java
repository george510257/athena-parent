package com.athena.starter.web.base;

import com.athena.common.bean.base.BaseVo;
import com.athena.common.bean.page.PageRequest;
import com.athena.common.bean.page.PageResponse;
import com.athena.common.core.base.IFeign;
import com.athena.common.core.base.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class BaseController<Vo extends BaseVo, S extends IService<Vo>> implements IFeign<Vo> {

    @Autowired
    protected S service;

    @Override
    public Vo insert(@RequestBody @Validated Vo vo) {
        return service.insert(vo);
    }

    @Override
    public Vo update(@RequestBody @Validated Vo vo) {
        return service.update(vo);
    }

    @Override
    public Boolean delete(@PathVariable Long id) {
        return service.delete(id);
    }

    @Override
    public Vo get(@PathVariable Long id) {
        return service.get(id);
    }

    @Override
    public List<Vo> list(@RequestBody @Validated Vo vo) {
        return service.list(vo);
    }

    @Override
    public PageResponse<Vo> page(@RequestBody @Validated PageRequest<Vo> pageRequest) {
        return service.page(pageRequest);
    }

    @Override
    public Boolean saveBatch(@RequestBody @Validated List<Vo> vos) {
        return service.saveBatch(vos);
    }

}
