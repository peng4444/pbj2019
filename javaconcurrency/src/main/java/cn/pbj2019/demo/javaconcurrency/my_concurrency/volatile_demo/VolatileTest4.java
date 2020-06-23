package cn.pbj2019.demo.javaconcurrency.my_concurrency.volatile_demo;

import java.util.concurrent.CountDownLatch;

/**
 * [](https://www.cnblogs.com/java-chen-hao/p/9968544.html)
 * [](https://www.cnblogs.com/konck/p/9336940.html)
 * volatile关键字保证了操作的可见性和有序性，但是volatile能保证对变量的操作是原子性吗？//不能
 * 在多线程环境下，有可能线程A将count读取到本地内存中，此时其他线程可能已经将count增大了很多，
 * 线程A依然对过期的本地缓存count进行自加，重新写到主存中，最终导致了count的结果不合预期，而是小于5000。
 * Atomic包解决
 */
public class VolatileTest4 {

    // 请求总数
    public static int clientTotal = 5000;
    public static volatile int count = 0;

    public static void main(String[] args) throws Exception {
        //使用CountDownLatch来等待计算线程执行完
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        //开启clientTotal个线程进行累加操作
        for(int i=0;i<clientTotal;i++){
            new Thread(){
                public void run(){
                    count++;//自加操作,不是原子操作
                    countDownLatch.countDown();
                }
            }.start();
        }
        //等待计算线程执行完
        countDownLatch.await();
        System.out.println(count);
    }
}
