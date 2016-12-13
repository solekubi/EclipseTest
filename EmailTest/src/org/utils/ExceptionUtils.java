package org.utils;



/**
 * Exception Utils
 */
public class ExceptionUtils {

    /**
     * Throw Runtime Exception
     *
     * @param e : Exception
     * @return : RuntimeException
     */
    public static RuntimeException unchecked(Exception e) {
        if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        } else {
            return new RuntimeException(e);
        }
    }
}
