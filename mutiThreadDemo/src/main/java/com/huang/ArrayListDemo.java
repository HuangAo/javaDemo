package com.huang;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author :huangao
 * 1.ArrayList线程不安全：大量线程同时读写会报java.util.ConcurrentModificationException异常
 *
 * 2.解决办法：
 *   一：Vector
 *   二：Collections.synchronizedList(new ArrayList<>());
 *   三：CopyOnWriteArrayList（JUC包中）写时复制，读写分离的思想
 *   写时复制：CopyOnWrite容器即写时复制容器。往容器中添加元素的时候不直接加，而是先将当前的容器进行Copy
 *           然后往新的容器中添加，完成后将原容器引用指向新容器。这样的好处是可以对CopyOnWrite容器进行并发的读
 *           而不需要加锁。
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        //List list = new ArrayList();
        //List list = new Vector();
        //List list = Collections.synchronizedList(new ArrayList<>());
          List list = new CopyOnWriteArrayList();
        for(int i=0; i<=30;i++){
            new Thread(()->{
               list.add(UUID.randomUUID().toString().substring(0,3));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
