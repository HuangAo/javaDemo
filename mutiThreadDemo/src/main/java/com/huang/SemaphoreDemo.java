package com.huang;

import java.util.concurrent.Semaphore;

/**
 * @author :huangao
 * 在信号量上的2种操作：acquire（获取） 当一个线程调用acquire操作时，它要么通过成功获取信号量（信号量减1），
 * 要么一直等下去，直到有线程释放信号量，或超时。
 * release（释放）实际上会将信号量的值加1，然后唤醒等待的线程。
 * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制。
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3); //3个资源，当为1时相当于Sychronized
        //5个线程去抢占3个资源
        for(int i=0;i<5;i++){
            final int temInt = i;
            new Thread(()->{
                try {
                    semaphore.acquire();
                    System.out.println("第"+temInt+"号线程抢到了资源");
                    Thread.sleep(3000);
                    System.out.println(temInt+"号占用结束。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                    System.out.println("第"+temInt+"号线程释放了资源");
                }
                System.out.println();
            },String.valueOf(i)).start();
        }
    }
}
