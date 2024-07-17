package com.athena.common.core.base;

import com.athena.common.bean.base.BaseVo;
import com.athena.common.bean.page.PageRequest;
import com.athena.common.bean.page.PageResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * Feign接口
 *
 * @param <Vo> VO
 */
public interface IFeign<Vo extends BaseVo> {

    /**
     * 新增
     *
     * @param vo VO
     * @return VO
     */
    @PostMapping("/insert")
    Vo insert(@RequestBody @Validated Vo vo);

    /**
     * 修改
     *
     * @param vo VO
     * @return VO
     */
    @PostMapping("/update")
    Vo update(@RequestBody @Validated Vo vo);

    /**
     * 删除
     *
     * @param id ID
     * @return 是否成功
     */
    @PostMapping("/delete/{id}")
    Boolean delete(@PathVariable Long id);

    /**
     * 查询
     *
     * @param id ID
     * @return VO
     */
    @PostMapping("/get/{id}")
    Vo get(@PathVariable Long id);

    /**
     * 查询列表
     *
     * @param vo VO
     * @return VO列表
     */
    @PostMapping("/list")
    List<Vo> list(@RequestBody @Validated Vo vo);

    /**
     * 分页查询
     *
     * @param pageRequest 分页查询参数
     * @return 分页查询结果
     */
    @PostMapping("/page")
    PageResponse<Vo> page(@RequestBody @Validated PageRequest<Vo> pageRequest);

    /**
     * 批量保存
     *
     * @param voList VO列表
     * @return 是否成功
     */
    @PostMapping("/saveBatch")
    Boolean saveBatch(@RequestBody @Validated List<Vo> voList);
}
