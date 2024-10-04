package com.info_board.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal
 */
@SuppressWarnings("all")
public class ThreadLocalUtil {
    private static final ThreadLocal THREAD_LOCAL = new ThreadLocal();

    // Get value by key
    public static <T> T get(){
        return (T) THREAD_LOCAL.get();
    }
	
    // Set key-value pair
    public static void set(Object value){
        THREAD_LOCAL.set(value);
    }

    // Clear Threadlocal
    public static void remove(){
        THREAD_LOCAL.remove();
    }
}
