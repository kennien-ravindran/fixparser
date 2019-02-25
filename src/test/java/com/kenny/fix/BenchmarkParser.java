package com.kenny.fix;

import com.kenny.fix.definition.MessageDefinition;
import com.kenny.fix.parser.FixParser;
import com.kenny.fix.parser.AbstractFixParser;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

public class BenchmarkParser {

    static MessageDefinition definitions;
    static AbstractFixParser parser;

    static {
        definitions = new MessageDefinition();
        definitions.loadTestDefinition();
        parser = new FixParser(definitions);
//        parser = new FixParser_Base(definitions);
        FixParser.DEBUG = false;

    }

    @State(Scope.Thread)
    public static class MyState {
        public String message = "8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|123=2|786=9|398=ABC|786=QAS|567=12|496=SDF|398=12|44=12|";
    }


    @Benchmark
    @Warmup(iterations = 2)
    @BenchmarkMode({Mode.Throughput})
    @Measurement(iterations = 5 , timeUnit = TimeUnit.MICROSECONDS)
    public void testParserThroughput(MyState state, Blackhole bh){
        try {
            Message m1 = parser.parseMessage(state.message);
            //Do something

            //Return to pool

            m1.addToPool();
            bh.consume(m1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Benchmark
    @Warmup(iterations = 2)
    @BenchmarkMode({Mode.AverageTime})
    @Measurement(iterations = 5 , timeUnit = TimeUnit.NANOSECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void testParserLatency(MyState state, Blackhole bh){
        try {
            Message m1 = parser.parseMessage(state.message);
            //Do something

            //Return to pool

            m1.addToPool();
            bh.consume(m1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
