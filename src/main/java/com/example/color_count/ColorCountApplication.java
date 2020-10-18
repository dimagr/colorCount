package com.example.color_count;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLOutput;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootApplication
public class ColorCountApplication {

    public static final int MIN_WAIT = 100;
    public static final int MAX_WAIT = 700;
    public static final int COLORS_NUMBER = 5;

    public static void main(String[] args) throws InterruptedException{
        SpringApplication.run(ColorCountApplication.class, args);

        final ExecutorService executor = Executors.newFixedThreadPool(100);

        List<Callable<Void>> callables = new ArrayList<>();

        for(int i = 0; i < 100 ; i++){
            Callable<Void> callable = () -> {
                try {
                    increaseColors();
                    ArrayUtils.printArray();
                }catch (InterruptedException e) {
//                    System.out.println(Thread.currentThread().getName()+" was interrupted");
                }
                return null;
            };
            callables.add(callable);
        }
        try{
            executor.invokeAny(callables);
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        executor.shutdownNow();

    }

    private static void increaseColors() throws InterruptedException{
        while(ColorEnum.getMaxCount() < 100){
            int randomSleepTime = ThreadLocalRandom.current().nextInt(MIN_WAIT, MAX_WAIT+1);
            TimeUnit.MILLISECONDS.sleep(randomSleepTime);
            int randomColorIndex = ThreadLocalRandom.current().nextInt(0, COLORS_NUMBER);
            ColorEnum colorEnum = ColorEnum.valueOf(randomColorIndex);
            updateColorCount(colorEnum);
        }
    }

    private static void updateColorCount(ColorEnum colorEnum){

        if(!isUpdatesLastMillis(colorEnum.ordinal(), 500)){
            ArrayUtils.getColorArray()[colorEnum.ordinal()].increaseCount();
            return;
        }

        if(isUpdatesLastMillis(colorEnum.ordinal(), 200)){
            return;
        }

        //cell was updated between 200 and 500 for sure
        //check neighbors
        if(isUpdatesLastMillis(colorEnum.getPrevOrdinal(), 500) && isUpdatesLastMillis(colorEnum.getNextOrdinal(), 500)){
            ArrayUtils.getColorArray()[colorEnum.ordinal()].increaseCount();
        }
    }

    private static boolean isUpdatesLastMillis(int i, int millis){
        LocalDateTime now = LocalDateTime.now();
//        System.out.println("create time: " + ArrayUtils.getColorArray()[i].getCreateTime() + " + " + millis + " millis = " + ArrayUtils.getColorArray()[i].getCreateTime().plus(millis, ChronoUnit.MILLIS));
//        System.out.println("now: " + now);
//        System.out.println("is after : " + ArrayUtils.getColorArray()[i].getCreateTime().plus(millis, ChronoUnit.MILLIS).isAfter(now));
        return ArrayUtils.getColorArray()[i].getCreateTime().plus(millis, ChronoUnit.MILLIS).isAfter(now);
    }

}
