package ru.calculator;

/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
*/

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("java:S106")
public class CalcDemo {
    public static void main(String[] args) {
        long counter = 100_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        for (var idx = 0; idx < counter; idx++) {
            var data = new Data(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                StringBuilder stringBuilder = new StringBuilder(LocalDateTime.now()
                        .format(DateTimeFormatter
                                .ofPattern("yyyy-MM-dd hh:mm:ss")));
                stringBuilder.append(" current idx:");
                stringBuilder.append(idx);
                System.out.println(stringBuilder);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());
        StringBuilder str = new StringBuilder("spend msec:");
        str.append(delta);
        str.append(", sec:");
        str.append((delta / 1000));
        System.out.println(str);
    }
}