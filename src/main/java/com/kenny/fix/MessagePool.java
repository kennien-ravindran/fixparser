package com.kenny.fix;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.IntStream;

public class MessagePool {

    public static int POOL_SIZE = 1000000;
    private ArrayBlockingQueue<Message> messagePool = new ArrayBlockingQueue<Message>(POOL_SIZE);


    public MessagePool(){
        IntStream.rangeClosed(1, POOL_SIZE).forEach( i -> {
            Message m = new Message();
            m.setPool(this);
            messagePool.add(m);
        });
    }


    public int getPoolSize() {
        return messagePool.size();
    }

    public Message getMessage(){
        Message message = null;
        try {
            message = messagePool.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;

    }


    public void putMessage(Message message){
        try {
           boolean su =  messagePool.offer(message);
           if(!su){
               System.err.println("EXCEPTION POOL is fULL ");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
