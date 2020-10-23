package com.huang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * t1, t2, t3 三个线程按顺序调用t1->t2->t3，t1打印5次，t2打印10次，t3打印15次，接着t1打印5次...进行10轮？
 * Condition精确唤醒
 * @author :huangao
 */

class Resoure3{
    private int flag = 1;
    Lock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();
    Condition condition3 = lock.newCondition();

    public void t1do() throws InterruptedException {
        lock.lock();
        try{
            while (flag!=1){
                condition1.await();
            }
            for(int i=0;i<5;i++){
                System.out.println("t1开始执行。。。"+Thread.currentThread().getName()+":"+flag);
            }
            flag = 2;
            condition2.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }


    }
    public void t2do() throws InterruptedException {
        lock.lock();
        try{
            while (flag!=2){
                condition2.await();
            }
            for(int i=0;i<10;i++) {
                System.out.println("t2开始执行。。。" + Thread.currentThread().getName() + ":" + flag);
            }
            flag = 3;
            condition3.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    public void t3do() throws InterruptedException {
        lock.lock();
        try{
            while (flag!=3){
                condition3.await();
            }
            for(int i=0;i<15;i++) {
                System.out.println("t3开始执行。。。" + Thread.currentThread().getName() + ":" + flag);
            }
            flag = 1;
            condition1.signal();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
}

public class ThreeThreadCirc{

    public static void main(String[] args) {
        Resoure3 resoure = new Resoure3();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resoure.t1do();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1").start();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resoure.t2do();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2").start();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resoure.t3do();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t3").start();
    }
}
