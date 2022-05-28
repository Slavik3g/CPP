package com.example.shift.counter;

import java.util.concurrent.atomic.AtomicInteger;
public class CountAtomic {
    private static final AtomicInteger count = new AtomicInteger(0);            // счетчик подключений

    public static void  increment() {
        count.getAndIncrement();
    }

    public static int value() {
        return count.get();
    }
}
