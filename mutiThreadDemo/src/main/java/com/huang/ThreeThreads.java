package com.huang;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 假设有三个线程t1,t2,t3 怎样让他们按照特定顺序执行？t1 --> t2 --> t3
 * @author :huangao
 */
public class ThreeThreads {

    public static void main(String[] args) {
        //method1();
        //method2();

    }

   //方法一：使用join
   static void method1(){
       final Thread t1 = new Thread(()->{System.out.println("t1开始执行");});

       final Thread t2 = new Thread(()->{try {
           t1.join();
           System.out.println("t2开始执行");
       } catch (InterruptedException e) {
           e.printStackTrace();
       }});

       Thread t3 = new Thread(()->{
           try {
               t2.join();
               System.out.println("t3开始执行");
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });
       t3.start();
       t2.start();
       t1.start();
    }

    //方法二：使用线程池
    static void method2(){
        final Thread t1 = new Thread(() -> {
            System.out.println("t1开始执行。。。");
        });

        final Thread t2 = new Thread(() -> {
            System.out.println("t3开始执行。。。");
        });

        final Thread t3 = new Thread(() -> {
            System.out.println("t3开始执行。。。");
        });

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(t1);
        executorService.submit(t2);
        executorService.submit(t3);
        executorService.shutdown();
    }



}
