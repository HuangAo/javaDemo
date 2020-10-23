package com.huang;

/**
 * 两个线程轮流执行，循环10次
 * synchronized  +  wait() / notifyAll()
 * @author :huangao
 */
class Resource{
    private int flag = 0;
    public synchronized void t1do() throws InterruptedException {
        while(flag!=0){
            this.wait();
        }
        System.out.println("t1开始执行。。"+Thread.currentThread().getName()+":"+flag);
        flag += 1;
        notifyAll();
    }
    public synchronized void t2do() throws InterruptedException {
        while(flag!=1){
            this.wait();
        }
        System.out.println("t2开始执行。。"+Thread.currentThread().getName()+":"+flag);
        flag -= 1;
        notifyAll();
    }
}
public class TwoThreadCirc {

    public static void main(String[] args) {
        Resource resource = new Resource();
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
