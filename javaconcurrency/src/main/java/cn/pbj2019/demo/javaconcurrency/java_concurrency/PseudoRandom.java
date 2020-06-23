package cn.pbj2019.demo.javaconcurrency.java_concurrency;

/**
 * PseudoRandom
 *
 * @author Brian Goetz and Tim Peierls
 */
public class PseudoRandom {
    int calculateNext(int prev) {
        prev ^= prev << 6;
        prev ^= prev >>> 21;
        prev ^= (prev << 7);
        return prev;
    }
}