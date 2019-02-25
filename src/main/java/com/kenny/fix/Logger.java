package com.kenny.fix;

public class Logger {


    /**
     *
     * @param prefix
     * @param tagId
     * @param value
     */
    public static void logTag(String prefix, int tagId, String value){
        System.out.println(prefix + "    TAG VALUE " + tagId + " == " + value );
    }


    /**
     *
     * @param message
     */
    public static void debugLog(String message){
        System.out.println("DEBUG: "+ message);
    }
}
