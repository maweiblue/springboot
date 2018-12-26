package org.springframework.util;

/**
 *
 */
public abstract class ObjectUtils {
    private  static final  int INITIAL_HASH=7;
    private static final  int MULTIPLIER=31;

    private static final String EMPTY_STRING="";
    private static final String NULL_STRING="null";
    private static final String ARRAY_START="{";
    private static final String ARRAY_END="}";

    private static final String EMPTY_ARRAY=ARRAY_START+ARRAY_END;
    private static final String ARRAY_ELEMENT_SEPARATOR=", ";

    public static boolean isCheckException(Throwable ex){
        return !(ex instanceof RuntimeException || ex instanceof Error);
    }
}
