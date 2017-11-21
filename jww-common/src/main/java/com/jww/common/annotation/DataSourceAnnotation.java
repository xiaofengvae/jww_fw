package com.jww.common.annotation;

import com.jww.common.Constants;

import java.lang.annotation.*;

/**
 * @author wanyong
 * @description: 数据源注解
 * @date 2017/11/17 13:56
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSourceAnnotation {
    Constants.DataSourceEnum value() default Constants.DataSourceEnum.MASTER;
}