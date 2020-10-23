package com.huang;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程轮流执行，循环10次
 * lock + Condition对象 / (await, signal)
 * @author :huangao
 */
class Resource2{
    private int flag =0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void t1do() throws InterruptedException {
        lock.lock();
        try {
            while(flag!=0){
                condition.await();
            }
            System.out.println("t1开始执行。。"+Thread.currentThread().getName()+":"+flag);
            flag += 1;
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public void t2do() throws InterruptedException {
        lock.lock();
        try {
            while(flag!=1){
                condition.await();
            }
            System.out.println("t2开始执行。。"+Thread.currentThread().getName()+":"+flag);
            flag -= 1;
            condition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
public class TwoThreadCirc2 {
    public static void main(String[] args) {

        Resource2 resource = new Resource2();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resource.t1do();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1线程").start();

        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resource.t2do();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2线程").start();
    }

}

