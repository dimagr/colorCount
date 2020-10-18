package com.example.color_count;

import java.time.LocalDateTime;

public enum ColorEnum {
    WHITE,BLACK,RED,BLUE,GREEN;

    private LocalDateTime createTime;
    private int count;

    ColorEnum() {
        this.createTime = LocalDateTime.now();
        this.count = 0;
    }

    public static ColorEnum valueOf(int num) {

        for (ColorEnum c : values()) {
            if (c.ordinal() == num) {
                return c;
            }
        }
        return null;
    }

    public int getPrevOrdinal(){
        return (ordinal() - 1 + values().length) % values().length;
    }

    public int getNextOrdinal(){
        return (ordinal() + 1)  % values().length;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    synchronized public void increaseCount() {
        count++;
        createTime = LocalDateTime.now();
    }

    public static int getMaxCount(){
        int maxCount = 0;
        for (ColorEnum c : values()) {
            if (c.count > maxCount) {
                maxCount = c.count;
            }
        }
        return maxCount;
    }

    public String toString(){
        return "color: " + name() +
                " create time: " + createTime +
                " count: " + count + "\n";
    }
}
