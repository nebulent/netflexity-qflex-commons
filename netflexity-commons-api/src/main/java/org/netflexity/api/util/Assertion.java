package org.netflexity.api.util;


/**
 * @author Max Fedorov
 *
 *  A helper class that gives us functionality equivalent to C's stdlib
 *  assert() macro.  Note that assertions should be guarded within code
 *  that is conditionally compiled.  Try to keep to Eiffel's assertion
 *  system.  Use static variables defined in a globally used interface
 *  class to control the inclusion/exclusion of the blocks of code that
 *  contain our assertions.
 */
public class Assertion {

    /**
     * The most basic assertion test - a boolean test 
     * with a message explaining it.
     * 
     * @param b
     * @param msg
     */
    public static void asert(boolean b, String msg) {
        if (!b)
            fail(msg);
    }

    /**
     * @param b
     */
    public static void asert(boolean b) {
        if (!b)
            fail(null);
    }
    
    /**
     * @param msg
     */
    private static void fail(String msg) {
        throw new AssertionFailedException(msg);
    }
}
