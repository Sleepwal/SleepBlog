package com.sleep.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Bean复制工具类
 *
 * @author manson
 * @date 2023/05/14
 */
public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    /**
     * 复制一个Bean的属性
     *
     * @param source 源
     * @param clazz  clazz
     * @return {@link V}
     * @author SleepWalker
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        V result;
        try {
            //new目标对象
            result = clazz.getDeclaredConstructor().newInstance();

            //实现属性复制
            BeanUtils.copyProperties(source, result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 复制BeanList所有Bean的属性
     *
     * @param source 源
     * @param clazz  clazz
     * @return {@link List}<{@link V}>
     * @author SleepWalker
     */
    public static <O,V> List<V> copyBeanList(List<O> source, Class<V> clazz) {
        return source.stream()
                .map(s -> copyBean(s, clazz))
                .collect(Collectors.toList());
    }
}
