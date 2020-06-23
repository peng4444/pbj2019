package cn.pbj2019.demo.javaconcurrency.java_concurrency.chapter1;


import cn.pbj2019.demo.javaconcurrency.java_concurrency.annotations.GuardedBy;
import cn.pbj2019.demo.javaconcurrency.java_concurrency.annotations.ThreadSafe;

/**
 * Sequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@ThreadSafe
public class Sequence {
    @GuardedBy("this") private int nextValue;

    public synchronized int getNext() {
        return nextValue++;
    }
}
