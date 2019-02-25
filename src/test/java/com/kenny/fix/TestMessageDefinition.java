package com.kenny.fix;

import com.kenny.fix.definition.MessageDefinition;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

public class TestMessageDefinition {

    @Test
    public void testLoadMessageDefinition(){
        MessageDefinition messageDefinition = new MessageDefinition();
        messageDefinition.loadTestDefinition();

    }


    public TreeSet<Integer> getTreeSet(int... tags){
        TreeSet<Integer> set = new TreeSet<Integer>();
        for(int tag : tags){
            set.add(tag);
        }

        return set;
    }
}
