package org.hongxi.whatsmars.javase.lambda.stream;

/**
 * Created by shenhongxi on 2018/1/11.
 */
public class Task {
    private Status status;
    private int point;

    public Task() {
    }

    public Task(Status status, int point) {
        this.status = status;
        this.point = point;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return String.format("(%s, %d)", status, point);
    }
}
