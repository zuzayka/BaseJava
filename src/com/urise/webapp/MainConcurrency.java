package com.urise.webapp;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    public static int counter;
    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                System.out.println(getName());
                throw new IllegalStateException();
            }
        };
        System.out.println(thread0.getState());
        thread0.setDaemon(true);
        thread0.start();
        System.out.println(thread0.getState());
//        new Thread(new Runnable() {                                        // Это без лямбда
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName());
//            }
//    }).start();

//      То же самое. Неявная запись Thread c интерфейсом Runnable с помощью лямбда:
        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        System.out.println(thread0.getState());

        final MainConcurrency mainConcurrency = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {

                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
//                System.out.println(counter+ " " + Thread.currentThread().getName());
            });
            thread.start();
            threads.add(thread);
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
//          При использовании .join() идет работа в один поток с ожиданием завершения потока начатого командой .start()
//            thread.join();
        }
//        Thread.sleep(500);

        System.out.println(counter);
    }

    private synchronized void inc() {
//        synchronized (this) {     // Если не использовать synchronized снаружи,
        counter++;
//        }
    }
}
