package com.kenny.fix.parser;

import com.kenny.fix.Constants;
import com.kenny.fix.Message;
import com.kenny.fix.MessagePool;



/**
 * Base Benchmark
 * Time for 100000000 times : 7817047858ns using 02-21-2019
 *
 * Time for 100000000 times : 12436997406ns using V2Parser
 *
 */
public abstract class AbstractFixParser {
    public boolean DEBUG = false;
    public abstract Message parseMessage(String strMsg) throws Exception;
    private StringBuilder bldr = new StringBuilder();
    protected MessagePool messagePool = new MessagePool();

    public int getPoolSize(){
        return messagePool.getPoolSize();
    }

    /**

     * @param message
     */
    protected  int parseNextTag(Message message){
        char ch;
        int unit = 1;
        int tagId = 0;
        char[] charArray = message.getCharArray();
        for (int index = message.getOffset(); index < charArray.length; index++) {
            ch = charArray[index];
            if(ch == Constants.TAG_VALUE_DELIMITER_CHAR){
                message.setOffset(++index);
                return tagId;
            }

            tagId *= unit;
            tagId += ch - '0';
            unit = 10;

        }

        return -1;
    }




    /**
     * @param message
     */
    protected String parseValue( Message message){
        char ch;
        bldr.setLength(0);
        char[] charArray = message.getCharArray();

        for (int index = message.getOffset(); index < charArray.length; index++) {
            ch = charArray[index];
            if(ch == Constants.TAG_DELIMITER_CHAR){
                message.setOffset(++index);
                return bldr.toString();
            }
            bldr.append(ch);
        }

        message.setOffset(charArray.length);

        return null;
    }



}
