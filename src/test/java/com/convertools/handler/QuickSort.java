package com.convertools.handler;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author fangkun
 * @date 2020/12/30 12:52
 * @description:
 */
public class QuickSort {

    public static void main(String[] args) {
        final ScheduledExecutorService es = Executors.newSingleThreadScheduledExecutor();
        es.schedule(new Runnable() {
            public void run() {
                System.out.println("now " + new Date());
            }
        }, 1, TimeUnit.SECONDS);
        es.scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("now " + new Date());
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}
