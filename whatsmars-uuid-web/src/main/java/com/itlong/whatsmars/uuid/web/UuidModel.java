package com.itlong.whatsmars.uuid.web;

import java.io.Serializable;

/**
 * Created by shenhongxi on 2016/8/12.
 */
public class UuidModel implements Serializable {
    private static final long serialVersionUID = 972714740313784893L;

    private String name;

    private long start;

    private long end;

    // above is DB column

    private long oldStart;

    private long oldEnd;

    private long now;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getOldStart() {
        return oldStart;
    }

    public void setOldStart(long oldStart) {
        this.oldStart = oldStart;
    }

    public long getOldEnd() {
        return oldEnd;
    }

    public void setOldEnd(long oldEnd) {
        this.oldEnd = oldEnd;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }
}
