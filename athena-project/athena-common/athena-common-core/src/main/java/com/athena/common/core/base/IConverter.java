package com.athena.common.core.base;

import com.athena.common.bean.page.PageResponse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 转换器
 *
 * @param <S> 源
 * @param <T> 目标
 */
public interface IConverter<S, T> {
    /**
     * 转换 源 -> 目标
     *
     * @param source 源
     * @return 目标
     */
    T convert(S source);

    /**
     * 拷贝转换 源 -> 目标
     *
     * @param source 源
     * @param target 目标
     */
    @InheritConfiguration(name = "convert")
    void convertCopy(S source, @MappingTarget T target);

    /**
     * 转换列表 源列表 -> 目标列表
     *
     * @param sources 源列表
     * @return 目标列表
     */
    default List<T> convertList(Collection<S> sources) {
        return sources.stream().map(this::convert).toList();
    }

    /**
     * 转换列表 源列表 -> 目标列表
     *
     * @param sources 源列表
     * @return 目标列表
     */
    default Set<T> convertSet(Collection<S> sources) {
        return sources.stream().map(this::convert).collect(Collectors.toSet());
    }

    /**
     * 转换分页 源分页 -> 目标分页
     *
     * @param sourcePage 源分页
     * @return 目标分页
     */
    default PageResponse<T> convertPage(PageResponse<S> sourcePage) {
        return new PageResponse<T>()
                .setPage(sourcePage.getPage())
                .setSize(sourcePage.getSize())
                .setTotal(sourcePage.getTotal())
                .setPages(sourcePage.getPages())
                .setData(convertList(sourcePage.getData()));
    }

    /**
     * 转换 目标 -> 源
     *
     * @param target 目标
     * @return 源
     */
    @InheritInverseConfiguration(name = "convert")
    S reverse(T target);

    /**
     * 拷贝转换 目标 -> 源
     *
     * @param target 目标
     * @param source 源
     */
    @InheritConfiguration(name = "reverse")
    void reverseCopy(T target, @MappingTarget S source);

    /**
     * 转换列表 目标列表 -> 源列表
     *
     * @param targets 目标列表
     * @return 源列表
     */
    default List<S> reverseList(Collection<T> targets) {
        return targets.stream().map(this::reverse).toList();
    }

    /**
     * 转换列表 目标列表 -> 源列表
     *
     * @param targets 目标列表
     * @return 源列表
     */
    default Set<S> reverseSet(Collection<T> targets) {
        return targets.stream().map(this::reverse).collect(Collectors.toSet());
    }

    /**
     * 转换分页 目标分页 -> 源分页
     *
     * @param targetPage 目标分页
     * @return 源分页
     */
    default PageResponse<S> reversePage(PageResponse<T> targetPage) {
        return new PageResponse<S>()
                .setPage(targetPage.getPage())
                .setSize(targetPage.getSize())
                .setTotal(targetPage.getTotal())
                .setPages(targetPage.getPages())
                .setData(reverseList(targetPage.getData()));
    }
}
