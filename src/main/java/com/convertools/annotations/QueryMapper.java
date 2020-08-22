package com.convertools.annotations;

import org.springframework.jdbc.object.MappingSqlQuery;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryMapper {
    Class<? extends MappingSqlQuery> clazz() ;
}
