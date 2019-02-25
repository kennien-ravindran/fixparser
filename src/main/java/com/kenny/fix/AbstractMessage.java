package com.kenny.fix;

public class AbstractMessage {

    protected char[] charArray;
    private int offset;


    public AbstractMessage(){

    }


    public void setCharArray(char[] charArray) {
        this.charArray = charArray;
        this.offset = 0;
    }


    public char[] getCharArray() {
        return charArray;
    }


    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getOffset() {
        return offset;
    }




    /**
     * Check if we have reached End Of Stream
     * @return
     */
    public boolean eos(){
        return offset == charArray.length;
    }


    public void clear(){

    }
}
