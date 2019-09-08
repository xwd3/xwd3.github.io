package cn.pn.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的四种创建方式
 */
public class ThreadPool {
    public static void main(String[] args) throws Exception {
        //第一种   可缓存线程池
//       这里的线程池是无限大的，当一个线程完成任务之后，这个线程可以接下来完成将要分配的任务，而不是创建一个新的线程。
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Thread.sleep(10);
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("ThreadName" + Thread.currentThread().getName() + "---" + index);
                }
            });
        }
        System.out.println("第二种");
        //第二种   创建一个可重用固定个数的线程池
//        创建固定大小的线程池。每次提交一个任务就创建一个线程，直到线程达到线程池的最大大小。
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println("ThreadName" + Thread.currentThread().getName() + "---" + index);
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        //第三种   创建一个可重用固定个数的线程池
        //创建一个定长线程池
        System.out.println("第三种");
        ScheduledExecutorService scheduledThreadPool =
                Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.schedule(new Runnable() {
                public void run() {
                    System.out.println("delay 3 seconds");
                }
            }, 3, TimeUnit.SECONDS);
        }

        //第四种       创建一个单线程化的线程池
        System.out.println("第四种");
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    /*                  System.out.println(index);*/
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

}
