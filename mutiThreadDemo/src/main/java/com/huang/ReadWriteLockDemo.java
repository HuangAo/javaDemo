package com.huang;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author :huangao
 * 多个线程同时读一个资源没有问题，为了满足并发，读取共享资源应该同时进行
 * 但是
 * 如果有一个线程想去写共享资源，就不应该有其他线程可以对该资源进行读或写
 * 读-读 能共存
 * 读-写 不能共存
 * 写-写 不能共存
 */
class MyCache{
   private volatile Map map = new HashMap<String,Object>();
   private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

   public void put(String key,Object value){
       readWriteLock.writeLock().lock(); //加写锁
       try{System.out.println("-----开始写入："+key);
           map.put(key,value);
           System.out.println("-----结束写入："+key);}
       finally {
           readWriteLock.writeLock().unlock();
       }

   }
   public void get(String key){
       readWriteLock.readLock().lock(); //加读锁
       try{
       System.out.println("开始读取："+key);
       Object result = map.get(key);
       System.out.println("读取结束："+result);}finally {
           readWriteLock.readLock().lock();
       }
   }
}
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for(int i=0;i<5;i++){
            final int temp = i;
            new Thread(()->{myCache.put(String.valueOf(temp),temp);},String.valueOf(i)).start();
        }

        for(int i=0;i<5;i++){
            final int temp = i;
            new Thread(()->{myCache.get(String.valueOf(temp));},String.valueOf(i)).start();
        }
    }
}

