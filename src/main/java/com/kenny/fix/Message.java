package com.kenny.fix;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Message extends AbstractMessage {

    private MessagePool pool;

    private Map<Integer, String> tags;

    private Map<Integer, Message> singleGroupTagsMap;

    private Map<Integer, List<Message>> groupTagsMap;


    public void setPool(MessagePool pool) {
        this.pool = pool;
    }

    public void addToPool(){
        this.clear();
        this.pool.putMessage(this);
    }


    public void addTagValue(int tagId, String value){
        if(tags == null)
            tags = new Int2ObjectArrayMap<String>();
        tags.put(tagId, value);
    }




    /**
     *
     * @param tagId
     * @param groupIndex
     * @param groupMessage
     * @param noOfGroups
     */
    public void addGroupMessage(int tagId, int groupIndex, Message groupMessage, int noOfGroups){
        if(noOfGroups == 1){
            if(singleGroupTagsMap == null){
                singleGroupTagsMap = new Int2ObjectArrayMap<Message>();
            }
            singleGroupTagsMap.put(tagId, groupMessage);
        }else {
            if(groupTagsMap == null){
                groupTagsMap = new Int2ObjectArrayMap<List<Message>>();
            }
            List<Message> groupMessages = groupTagsMap.get(tagId);
            if(groupMessages == null){
                groupMessages = new ArrayList<Message>();
                groupTagsMap.put(tagId, groupMessages);
            }
            groupMessages.add(groupMessage);
        }
    }


    public String getValue(int tagId) {
        return tags.get(tagId);
    }

    /**
     *
     * @param tagId
     * @return
     */
    public Message getGroup(int tagId){
        return singleGroupTagsMap.get(tagId);
    }


    /**
     *
     * @param tagId
     * @return
     */
    public List<Message> getGroups(int tagId){
        return groupTagsMap.get(tagId);
    }


    /**
     *
     * @param tagId
     * @return
     */
    public int getGroupCount(int tagId){
        return Integer.parseInt(tags.get(tagId));
    }


    /**
     *
     */
    public void clear(){
        if(tags != null)
            tags.clear();


        if(singleGroupTagsMap != null) {
            for (Message message : singleGroupTagsMap.values()) {
                message.addToPool();
            }

            singleGroupTagsMap.clear();
        }

        if(groupTagsMap != null) {
            for (List<Message> groupMessages : groupTagsMap.values()) {
                for (Message message : groupMessages) {
                    message.addToPool();
                }
            }

            groupTagsMap.clear();
        }

        super.clear();
    }









    @Override
    public String toString() {
        return "Message{" +
                "tags=" + tags +
                ", singleGroupTagsMap=" + singleGroupTagsMap +
                ", groupTagsMap=" + groupTagsMap +
                '}';
    }
}
