package com.convertools.convertors;

/**
 * @author fangkun
 * @date 2021/5/13 13:05
 * @description:
 */
public interface IConverter<T, F> {
    F convert(T t);
}
