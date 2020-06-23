package cn.pbj2019.demo.javaconcurrency.my_concurrency.volatile_demo;

/**
 * 出现了x=1，y=1的情况
 * 造成这种结果的原因可能有：
 *
 * 即时编译器的重排序；
 * 处理器的乱序执行。
 * 　　即时编译器和处理器可能将代码中没有数据依赖的代码进行重排序。但如果代码存在数据依赖关系，那么这部分代码不会被重排序。
 *      上面的示例代码中，t1线程中对a、x的赋值就不存在依赖关系，所以可能会发生重排序。t2线程同理。
 */
//public class VolatileTest2 {
//    private  static int count = 0;
//    static int a = 0,b=0,x=0,y=0;//volatile修饰的，可以禁止重排序
//
//    public static void main(String[] args) throws InterruptedException {
//        boolean flag  = true;
//        while (flag){
//            Thread t1 = new Thread(() ->{
//                x = b;
//                a = 1;
//            });
//
//            Thread t2 = new Thread(() ->{
//                y = a;
//                b = 1;
//            });
//
//            t1.start();
//            t2.start();
//            //t1,t2线程先执行
//            t1.join();
//            t2.join();
//            System.out.println("第"+ ++count +"x = "+x+"y = "+y);
//        }
//
//    }
//}
