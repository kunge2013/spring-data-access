package com.convertools.entity;

/**
 * @author fangkun
 * @date 2020/11/13 17:07
 * @description:
 */
public class Certificate extends BaseEntity {

    private long starttime;

    private long endtime = System.currentTimeMillis();

    private String name = "data xxxxxxxxxxxxx";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    /*是否已经失效*/
    public boolean isInvalid() {
        long now = System.currentTimeMillis();
        if (now >= starttime && now <= endtime) {
            return false;
        }
        return true;
    }
}
