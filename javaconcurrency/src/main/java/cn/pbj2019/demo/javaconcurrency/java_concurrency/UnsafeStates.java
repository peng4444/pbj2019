package cn.pbj2019.demo.javaconcurrency.java_concurrency;

/**
 * UnsafeStates
 * <p/>
 * Allowing internal mutable state to escape
 *
 * @author Brian Goetz and Tim Peierls
 */
class UnsafeStates {
    private String[] states = new String[]{
        "AK", "AL" /*...*/
    };

    public String[] getStates() {
        return states;
    }
}
