package com.company;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        final int ARRAY_SIZE = 100;
        final int BOUND = 10000;
        Random random = new Random();
        LongAdder sum = new LongAdder();
        int[] array1 = generateArray(ARRAY_SIZE, BOUND, random);
        int[] array2 = generateArray(ARRAY_SIZE, BOUND, random);
        int[] array3 = generateArray(ARRAY_SIZE, BOUND, random);

        List<int[]> list = Arrays.asList(array1, array2, array3);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        list.forEach(list2 -> executorService.submit(() -> {
            int sum2 = Arrays.stream(list2)
                    .reduce(0, Integer::sum);
            sum.add(sum2);
        }));
        executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Суммарная выручка: " + sum.sum());
        executorService.shutdown();
    }

    public static int[] generateArray(int arraySize, int bound, Random random) {
        int[] array = new int[arraySize];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(bound);
        }
        return array;
    }
}
