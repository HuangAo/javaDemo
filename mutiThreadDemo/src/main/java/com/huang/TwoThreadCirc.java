package com.huang;

/**
 * 两个线程轮流执行，循环10次
 * synchronized  +  wait() / notifyAll()
 * @author :huangao
 */
public class TwoThreadCirc {

    public static void main(String[] args) {
        Resource resource = new Resource();
        new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resource.upFlag();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t1线程").start();

         new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    resource.downFlag();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"t2线程").start();
    }

   static class Resource{
        int flag = 0;
        public synchronized void upFlag() throws InterruptedException {
            while(flag!=0){
               this.wait();
            }
            System.out.println("t1开始执行。。"+Thread.currentThread().getName()+":"+flag);
            flag += 1;
            notifyAll();
        }
        public synchronized void downFlag() throws InterruptedException {
            while(flag!=1){
                this.wait();
            }
            System.out.println("t2开始执行。。"+Thread.currentThread().getName()+":"+flag);
            flag -= 1;
            notifyAll();
        }
    }

}
