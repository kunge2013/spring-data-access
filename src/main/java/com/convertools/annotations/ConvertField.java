package com.convertools.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author fangkun
 * @date 2020/11/13 9:18
 * @description:
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ConvertField {

     String name();
     /*描述*/
     String desc() default "";

}
