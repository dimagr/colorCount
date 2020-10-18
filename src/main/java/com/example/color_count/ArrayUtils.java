package com.example.color_count;

import java.time.LocalDateTime;
import java.util.Arrays;

public class ArrayUtils {

    private static ColorEnum[] colorArray = {ColorEnum.WHITE,ColorEnum.BLACK,ColorEnum.RED,ColorEnum.BLUE,ColorEnum.GREEN};

    public static ColorEnum[] getColorArray() {
        return colorArray;
    }

    public static void printArray(){
        System.out.println(Arrays.toString(colorArray));
    }
}
