package com.kenny.fix.definition;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

public class MessageDefinition {

    private HashMap<Integer, MessageGroupDefinition> groupDefinitionMap = new HashMap<Integer, MessageGroupDefinition>();

    public void loadDefinition(int tagId, int firstTag, Set<Integer> required, Set<Integer> optional) {
        groupDefinitionMap.put(tagId, new MessageGroupDefinition(tagId, firstTag, required, optional ));
    }


    public void loadTestDefinition(){

        loadDefinition(269, 277, getTreeSet(456), getTreeSet(231, 283));
        loadDefinition(123, 786, getTreeSet(398), getTreeSet(567, 496));

        loadDefinition(552, 54, getTreeSet(37, 453), getTreeSet(12,13, 58, 528));
        loadDefinition(453, 448, getTreeSet(447, 452, 802), getTreeSet());
        loadDefinition(802, 523, getTreeSet(803), getTreeSet());
    }



    private TreeSet<Integer> getTreeSet(int... tags){
        TreeSet<Integer> set = new TreeSet<Integer>();
        for(int tag : tags){
            set.add(tag);
        }

        return set;
    }


    public MessageGroupDefinition getGroupDefinition(int groupId){
        return groupDefinitionMap.get(groupId);
    }


}
