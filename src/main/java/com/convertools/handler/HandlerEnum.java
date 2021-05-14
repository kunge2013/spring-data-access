package com.convertools.handler;

import java.util.HashSet;
import java.util.Set;

/**
 * @author fangkun
 * @date 2021/4/26 7:07
 * @description:
 */
public enum HandlerEnum {
    APIPOST(1, "postApi"),
    DBSAVE(2, "sqlServerHandler");
    int type;
    String service;

    HandlerEnum(int type, String service) {
        this.type = type;
        this.service = service;
    }

    public static Set<String> parse(int val) {
        Set<String> services = new HashSet<>();
        for (HandlerEnum value : values()) {
            if ((value.type & val) == value.type) {
                services.add(value.service);
            }
        }
        return services;
    }
}
