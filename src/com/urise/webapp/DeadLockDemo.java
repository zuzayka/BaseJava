package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class DeadLockDemo {

    public static void main (String[] args) throws InterruptedException {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            list2.add(i);
            list1.add(i * 3);
        }

        Thread thread1 = new Thread(() -> {
            System.out.println("thread1 start");
            for (int i = 0; i < 10000; i++) {
                moveListItem(list1, list2, list1.get(i));
            }
        });
        Thread thread2 = new Thread(() -> {
//            System.out.println("thread2 start");
            for (int i = 0; i < 10000; i++) {
                moveListItem(list2, list1, list2.get(i));
            }
        });

        thread1.start();
        thread2.start();
        System.out.println("main completed");
        System.out.println(list1);
    }

    private static void moveListItem (List<Integer> from, List<Integer> to, Integer item) {
        synchronized (from) {
            synchronized (to) {
                if(from.remove(item)){
                    to.add(item);
                }
            }
        }
    }
}
