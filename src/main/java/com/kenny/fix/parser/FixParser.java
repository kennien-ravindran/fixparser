package com.kenny.fix.parser;

import com.kenny.fix.Logger;
import com.kenny.fix.Message;
import com.kenny.fix.definition.MessageDefinition;
import com.kenny.fix.definition.MessageGroupDefinition;

import java.util.Set;


/**
 *
 *
 */
public class FixParser extends AbstractFixParser {

    public static boolean DEBUG = false;

    private final MessageDefinition messageDefinition;

    public FixParser(MessageDefinition messageDefinition){
        this.messageDefinition = messageDefinition;
    }


    /**
     *
     * @param messageStr
     * @return
     * @throws Exception
     */
    public Message parseMessage(String messageStr ) throws Exception {
        if(DEBUG)
            Logger.debugLog("-------------------------- Start -----------------------------\n"
                    + messageStr
                    + "\n-------------------------------------------------------------");
        char[] charArray = messageStr.toCharArray();
        Message message = messagePool.getMessage();
        message.setCharArray(charArray);
        try {
            parseMessage( message);
        } catch (Exception e) {
            message.addToPool();

            throw e;
        }

        if(DEBUG)
            Logger.debugLog("-------------------------- Completed -----------------------------\n"
                    + messageStr + "\n"
                    + "Message " + message + "\n"
                   // + "GROUP Message " +message.getGroupTags().toString() + "\n"
                    + "-----------------------------------------------------------------");

        return message;

    }


    /**
     *
     * @param message
     * @throws Exception
     */
    private void parseMessage( Message message) throws Exception{
        boolean processing = true;
        int nextTagId = -1;
        while(processing){
            nextTagId = parseMessage(message, nextTagId);

            if(message.eos()) {
                processing = false;
            }


        }

    }


    /**
     *
     * @param message
     * @throws Exception
     */
    private int parseMessage( Message message, int nextTagId) throws Exception{

        if(message.eos()) {
            return -1;
        }

        int tagId = nextTagId;
        if(tagId == -1)
            tagId = parseNextTag(message);

        String tagValue =  parseValue(message);

        message.addTagValue(tagId, tagValue);

        if(DEBUG)
            Logger.logTag(null + "", tagId, tagValue);

        MessageGroupDefinition grpDef = messageDefinition.getGroupDefinition(tagId);
        if(grpDef != null) {
           return parseGroup(message, tagId, tagValue, grpDef, message);
        }

        return -1;

    }


    /**
     *
     * @param message
     * @param tagId
     * @param value
     * @param grpDef
     * @throws Exception
     */
    private int parseGroup(Message message, int tagId, String value, MessageGroupDefinition grpDef, Message parentMessage) throws Exception {
        String newPath = null;
        int noOfGroups = Integer.parseInt(value);

        boolean parsingGroup = true;
        int groupCount = 0;
        Set<Integer> requiredTags = null;

        Message groupMessage = null;

        int nextTagId = -1;

        while(parsingGroup){
            if(message.eos()){
                break;
            }

            //Need to have a tag offset, so that we can reset if we have processed all group tag and identify non group tag.
            int grpTagId = nextTagId;
            if(grpTagId == -1){
                grpTagId = parseNextTag(message);
            }


            //Check is this is the first tag in the group or if its in required or optional tags
            if(grpTagId == grpDef.getFirstTag()){

                if(requiredTags == null || requiredTags.isEmpty()) {
                    requiredTags = grpDef.getRequiredValidator();
                    groupMessage = messagePool.getMessage();
                    groupCount++;
                }
                else {
                    groupMessage.addToPool();
                    throw new Exception("TAG " + tagId + " missing required tags " + requiredTags + " but found first tag");
                }


            } else if(grpDef.isRequired(grpTagId) || grpDef.isOptional(grpTagId)){
                //This tag belongs to the required or optional tags.

                if(requiredTags != null) {
                    boolean removed = requiredTags.remove(grpTagId);
                    if (removed && requiredTags.isEmpty()) {
                        parentMessage.addGroupMessage(tagId, groupCount, groupMessage, noOfGroups);
                    }
                }else{
                    throw new Exception("TAG " + tagId + " missing first tag [" + grpDef.getFirstTag() +"] group, found tag " + grpTagId + " not in group");
                }

            } else {
                if(requiredTags != null) {
                    //Anytime a unknown tag is found that does not belong to the group,
                    //check if all the expected number of groups is parse and all required tag is parse in the last parsed group.
                    if (groupCount != noOfGroups && requiredTags.isEmpty()) {
                        //groupMessage.addToPool();
                        throw new Exception("TAG " + tagId + " missing group, found tag " + grpTagId + " not in group");
                    } else if (groupCount == noOfGroups && !requiredTags.isEmpty()) {
                        groupMessage.addToPool();
                        throw new Exception("TAG " + tagId + " missing required tags " + requiredTags + " but found tag " + grpTagId + " not in group");
                    }
                }else{
                   //groupMessage.addToPool();
                    throw new Exception("Required tag is empty for " + grpDef.getTagId() + ", " + grpDef.getFirstTag() + ", " + grpDef.getRequired() + ", but found tag " + grpTagId + " not in group");
                }

                //This is the next tag
                //so reuse that is already parsed by assigning to nextTagId and returning it
                nextTagId = grpTagId;
                parsingGroup = false;
                if(DEBUG)
                    Logger.debugLog("Completed Group " + tagId + ". Last Parsed " + grpTagId + ", parseGroupCount "+ groupCount + ", expectedGroupCount " + noOfGroups);


                break;
            }


            String grpTagValue = parseValue(message);

            if(DEBUG)
                Logger.logTag(newPath + "->G"+groupCount, grpTagId, grpTagValue);


            groupMessage.addTagValue(grpTagId, grpTagValue);

            MessageGroupDefinition subGrpDef = messageDefinition.getGroupDefinition(grpTagId);
            if(subGrpDef != null){
                nextTagId = parseGroup(message , grpTagId, grpTagValue, subGrpDef, groupMessage);
            }

        }

        return nextTagId;
    }








}
