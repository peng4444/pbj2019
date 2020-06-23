package cn.pbj2019.demo.javaconcurrency.java_concurrency;

/**
 * CountingSheep
 * <p/>
 * Counting sheep
 *
 * @author Brian Goetz and Tim Peierls
 */
public class CountingSheep {
    volatile boolean asleep;

    void tryToSleep() {
        while (!asleep)
            countSomeSheep();
    }

    void countSomeSheep() {
        // One, two, three...
    }
}








