package cn.pbj2019.demo.javaconcurrency.my_concurrency.volatile_demo;

/**
 * Java中的volatile关键字:https://www.cnblogs.com/pedlar/p/10703705.html
 */
//public class VolatileTest1 {
//
//    static int a = 0,b=0,x=0,y=0;
//
//    public static void main(String[] args) throws InterruptedException {
//
//        Thread t1 = new Thread(() ->{
//            x = b;
//            a = 1;
//        });
//
//        Thread t2 = new Thread(() ->{
//            y = a;
//            b = 1;
//        });
//
//        t1.start();
//        t2.start();
//        t1.join();
//        t2.join();
//        System.out.println("x = "+x+"y = "+y);
//    }
    /**
     * 上面这段代码中，x、y的结果可能会有如下三种情况：
     *
     * x=0，y=0（例如t1执行完第一个赋值语句后，再切换到t2执行赋值语句）；
     *
     * x=0，y=1（t1先执行完，再执行t2）；
     *
     * x=1，y=0（t2先执行完，再执行t1）。
     *
     * （注：本文中，在非代码片段中的“=”均念作等于，非赋值操作。）
     *
     * 　　但是，还存在一种看起来不可能的结果x=1，y=1。
     *
     * 　　我们可以对上面的代码稍加修改，以便展示x=1，y=1的情况： ==>VolatileTest2
     */
//}
