package cn.pbj2019.demo.javaconcurrency.java_concurrency.chapter1;


import cn.pbj2019.demo.javaconcurrency.java_concurrency.annotations.NotThreadSafe;

/**
 * UnsafeSequence
 *
 * @author Brian Goetz and Tim Peierls
 */

@NotThreadSafe
public class UnsafeSequence {
    private int value;

    /**
     * Returns a unique value.
     */
    public int getNext() {
        return value++;
    }
}
