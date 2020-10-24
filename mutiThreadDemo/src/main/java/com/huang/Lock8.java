package com.huang;

/**
 * 八锁问题
 * @author :huangao
 *
 * 1 标准访问，t1还是t2?  (t1)
 * 2 停4秒在t1方法内，先打t1还是t2 (t1)
 * 3 普通的hello方法，是先打短信还是hello (hello)
 * 4 现在有两个Resource，先t1还是t2 (t2)
 * 5 两个静态同步方法，1个Resource，先t1还是t2 (t1)
 * 6 两个静态同步方法，2个，先t1还是t2 (t1)
 * 7 1个静态同步方法，1个普通同步方法，1部手机，先打印短信还是邮件 (t2)
 * 8 1个静态同步方法，1个普通同步方法，2部手机，先打印短信还是邮件 (t2)
 *
 */
class Resoure{

    //public synchronized void t1do1() throws InterruptedException {
    public static synchronized void t1do1() throws InterruptedException {
        Thread.sleep(4000);
        System.out.println("----t1 do-----");
    }

    public synchronized void t2do2() throws InterruptedException{
    //public static synchronized void t2do2() throws InterruptedException{
        System.out.println("---t2 do---");
    }

    public void hello(){
        System.out.println("---hello---");
    }
}

public class Lock8 {

    public static void main(String[] args) {
        Resoure resoure = new Resoure();
        Resoure resoure2 = new Resoure();
        new Thread(()->{
            try {
                resoure.t1do1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();
        // resoure.hello();
        new Thread(()->{
            try {
                //resoure.t2do2();
                resoure2.t2do2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}
