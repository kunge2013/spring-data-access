package com.convertools;

/**
 * @author fangkun
 * @date 2021/1/5 10:18
 * @description:
 */
public class A {

    public static void main(String[] args) {
        StringBuffer a = new StringBuffer("B");
        StringBuffer b = new StringBuffer("A");
        op(a, b);
        System.out.println(a + "   ," + b);
    }
    public static void op(StringBuffer x, StringBuffer y) {
        y.append(x);
        y= x;
    }
}
