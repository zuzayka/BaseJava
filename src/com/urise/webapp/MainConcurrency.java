package com.urise.webapp;

import java.text.SimpleDateFormat;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    public static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    //    private static final Object LOCK = new Object();
    private static final Lock lock = new ReentrantLock();
    private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println(Thread.currentThread().getName());

        Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getState() + " " + Thread.currentThread().getName());
                System.out.println(getName());
//                throw new IllegalStateException();
            }
        };
        System.out.println(thread0.getState() + " " + Thread.currentThread().getName());
        thread0.setDaemon(true);
        thread0.start();
        System.out.println(thread0.getState() + " " + Thread.currentThread().getName());
//        new Thread(new Runnable() {                                        // Это без лямбда
//            @Override
//            public void run() {
//                System.out.println(Thread.currentThread().getName());
//            }
//    }).start();

//      То же самое. Неявная запись Thread c интерфейсом Runnable с помощью лямбда:


        new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
        System.out.println(thread0.getState() + " " + Thread.currentThread().getName());

        final MainConcurrency mainConcurrency = new MainConcurrency();
//        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
//        CompletionService completionService = new ExecutorCompletionService(executorService);  // используется для
//        получения завершенной нити и получения результата с этой нити
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<?> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
//                    System.out.println(threadLocal.get().format(new Date()));
                }
                latch.countDown();
                return 5;
            });
        }
        executorService.shutdown();
        latch.await(10, TimeUnit.SECONDS);


/*      Вместо создания отдельных потоков используем executorService.submit(()
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();


//                System.out.println(counter+ " " + Thread.currentThread().getName());
            });
*/


/*  в лекции 12 заменяем на latch.
            thread.start();
            threads.add(thread);
            threads.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
*/
//          При использовании .join() идет работа в один поток с ожиданием завершения потока начатого командой .start()
//            thread.join();
//        }

//        Thread.sleep(500);

        System.out.println(mainConcurrency.atomicCounter.get());
//        System.out.println(counter);   теперь atomic...
    }

    //    private synchronized void inc() {   //  убрали synchronized когда ввели lock
    private void inc() {
//        synchronized (this) {     // Если не использовать synchronized снаружи,

        atomicCounter.incrementAndGet();

/*     если использовать AtomicInteger lock, try  не нужны
       lock.lock();
        try {
            counter++;
        } finally {
            lock.unlock();
        }*/


//        }
    }
}
