package com.kenny.fix.definition;

import java.util.Set;
import java.util.TreeSet;

public final class MessageGroupDefinition {
    final private int tagId;
    final private int firstTag;
    final private Set<Integer> required;
    final private Set<Integer> optional;

    public MessageGroupDefinition(int tagId, int firstTag, Set<Integer> required, Set<Integer> optional){
        this.tagId = tagId;
        this.firstTag = firstTag;
        this.required = required;
        this.optional = optional;
    }


    public int getTagId() {
        return tagId;
    }


    public int getFirstTag() {
        return firstTag;
    }


//    public List<Integer> getRequired() {
//        return required;
//    }
//
//
//    public List<Integer> getOptional() {
//        return optional;
//    }

    public Set<Integer> getRequired(){
        return required;
    }

    public Set<Integer> getOptional(){
        return optional;
    }

    public boolean isRequired(int tagId){
        return required.contains(tagId);
    }

    public boolean isOptional(int tagId){
        return optional.contains(tagId);
    }

    /**
     * The Required validator can be implemented in two ways
     * 1. Use the new set iterator for each group and remove the processed tags.
     * When the iterator size is zero that means that all required tags was processed.
     * Adv: We will know if the required tag is missing as soon as we start the next group first tag or find a tag that does not belong to the group.
     * DisAdv: We have to create new Set for each group. meaning if the noOfGroups is 5, we will have to create 5 Set.
     * 2. Use a HashMap, and increment the count of tag.
     * At the end of Groups Tag, we have to check if the count of all required tag is same a the group count.
     * Adv: Unlike the set implementation , We use only 1 map for processing the entire group.
     * DisAdv: We can validate only after processing all the groups or we need to validate
     * if the all the map has same value as soon as we start the next group first tag or find a tag that does not belong to the group.
     *
     * @return
     */
    public Set<Integer> getRequiredValidator(){
        return new TreeSet<Integer>(required);
    }



}
