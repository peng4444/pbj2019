package cn.pbj2019.demo.javaconcurrency.my_concurrency.volatile_demo;

/**
 * [volatile保证共享变量的可见性](https://www.cnblogs.com/java-chen-hao/p/9968544.html)
 *   volatile是Java提供的一种轻量级的同步机制。同synchronized相比（synchronized通常称为重量级锁），volatile更轻量级。
 * 　　一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
 * 　　1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
 * 　　2）禁止进行指令重排序。
 */
public class VolatileTest3 {
    public static void main(String[] args) {

        ThreadDemo thread = new ThreadDemo();
        new Thread(thread).start();
        while (true){
            if (thread.isFlag()){
                System.out.println("--------------");
                break;
            }
        }
    }
}
class ThreadDemo implements Runnable {
    private  boolean flag = false; //volatile修饰，JMM会把该线程对应的本地内存中的变量强制刷新到主内存中去
    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
        }
        flag = true;
        System.out.println("flag=" + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }
}