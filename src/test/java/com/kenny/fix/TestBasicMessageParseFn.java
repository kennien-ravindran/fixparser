package com.kenny.fix;


import com.kenny.fix.definition.MessageDefinition;
import com.kenny.fix.parser.FixParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestBasicMessageParseFn {
    static MessageDefinition definitions;

    @BeforeAll
    static void initAll() {
        definitions = new MessageDefinition();
        definitions.loadTestDefinition();
    }


    @Test
    public void testParseMessage(){
        FixParser.DEBUG = true;
        FixParser parser = new FixParser(definitions);
        try {
            Message m1  = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|44=12|");




//            Message m2  = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|123=2|786=9|398=ABC|786=QAS|567=12|496=SDF|398=12|44=12|");
//
//            Message m3  = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|44=12|");
//
//            Message m4 = parser.parseMessage("8=FIX.4.4|9=392|35=AE|34=2|49=LQNTSWXTR|52=20120525-15:14:58.134|56=SWXLQNTTR|15=CHF|22=4|31=10.52|32=1000|48=CH0023405456|55=[N/A]|60=20110613-15:15:15.234|75=20120525|207=LQNT|487=0|552=2|54=1|37=NONE|453=1|448=9234|447=D|452=7|802=1|523=13173|803=25|528=R|54=2|37=NONE|453=1|448=9209|447=D|452=17|802=1|523=60584|803=25|570=N|571=_LN45|768=1|769=20110613-15:15:14.222|770=1|856=0|880=LN3434343|10=193|");

            //MEssage with no END tag
            //Message m5  = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|");

            m1.addToPool();
//            m2.addToPool();
//            m3.addToPool();
//            m4.addToPool();
            //MessagePool.INSTANCE.putMessage(m5);

            //Complex
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




    @Test
    public void testParseMessageComplexType(){
        FixParser parser = new FixParser(definitions);
        FixParser.DEBUG = true;
        try {


            Message m = parser.parseMessage("8=FIX.4.4|9=392|35=AE|34=2|49=LQNTSWXTR|52=20120525-15:14:58.134|56=SWXLQNTTR|15=CHF|22=4|31=10.52|32=1000|48=CH0023405456|55=[N/A]|60=20110613-15:15:15.234|75=20120525|207=LQNT|487=0|552=2|54=1|37=NONE|453=1|448=9234|447=D|452=7|802=1|523=13173|803=25|528=R|54=2|37=NONE|453=1|448=9209|447=D|452=17|802=1|523=60584|803=25|570=N|571=_LN45|768=1|769=20110613-15:15:14.222|770=1|856=0|880=LN3434343|10=193|");

            //Do something

            //Return to pool
            m.addToPool();
            //Complex
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void testParseMessageGroupTypeNTimes(){
        n = 10;
        FixParser parser = new FixParser(definitions);
        FixParser.DEBUG = false;
        long startTime = System.nanoTime();
        final AtomicInteger errCount = new AtomicInteger();

        Thread t = new Thread(r);
        t.start();

        try {
            IntStream.rangeClosed(1, n).forEach(i -> {
                try {

                    long time = System.currentTimeMillis();
                    Message m1 = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|60=" + time+"|44=12|");

                    assertEquals(2, m1.getGroupCount(269));

                    List<Message> groups = m1.getGroups(269);
                    assertEquals("12", groups.get(0).getValue(277));
                    assertEquals("1", groups.get(1).getValue(277));


                    Message m2 = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|123=2|786=9|398=ABC|786=QAS|567=12|496=SDF|398=12|60=" + time+"|44=12|");


                    assertEquals(2, m2.getGroupCount(269));

                    List<Message> groups2 = m2.getGroups(269);
                    assertEquals("12", groups2.get(0).getValue(277));
                    assertEquals("1", groups2.get(1).getValue(277));

                    assertEquals(2, m2.getGroupCount(123));

                    List<Message> groups3 = m2.getGroups(123);
                    assertEquals("9", groups3.get(0).getValue(786));
                    assertEquals("QAS", groups3.get(1).getValue(786));


                    Message m4 = parser.parseMessage("8=FIX.4.4|9=392|35=AE|34=2|49=LQNTSWXTR|52=20120525-15:14:58.134|56=SWXLQNTTR|15=CHF|22=4|31=10.52|32=1000|48=CH0023405456|55=[N/A]|60=20110613-15:15:15.234|75=20120525|207=LQNT|487=0|552=2|54=1|37=NONE|453=1|448=9234|447=D|452=7|802=1|523=13173|803=25|528=R|54=2|37=NONE|453=1|448=9209|447=D|452=17|802=1|523=60584|803=25|570=N|571=_LN45|768=1|769=20110613-15:15:14.222|770=1|856=0|880=LN3434343|10=193|");

//                    assertEquals(2, m2.getGroupCount(269));
//
//                    List<Message> groups2 = m2.getGroups(269);
//                    assertEquals("12", groups2.get(0).getValue(277));
//                    assertEquals("1", groups2.get(1).getValue(277));
//
//                    assertEquals(2, m2.getGroupCount(123));
//
//                    List<Message> groups3 = m2.getGroups(123);
//                    assertEquals("9", groups3.get(0).getValue(786));
//                    assertEquals("QAS", groups3.get(1).getValue(786));

                    long count = counter.incrementAndGet();

                    int ran = (int)(Math.random() * 1000);







//                    //Do something
//
//                    //Return to pool
                        m1.addToPool();
                        m2.addToPool();

//                    assertEquals (MessagePool.POOL_SIZE , parser.getPoolSize());
//                    Message m3  = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|44=12|60=" + System.currentTimeMillis());


                }catch(Exception e){
                    e.printStackTrace();
                    assertEquals (MessagePool.POOL_SIZE , parser.getPoolSize());
                    counter.incrementAndGet();
                    System.exit(1);
                }


            });

            long endTime = System.nanoTime();

            System.out.println("Time for " + (n * 2) + " times : " +  (endTime - startTime) + "ns using V2Parser");
            //Complex
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    AtomicLong counter = new AtomicLong();
    Runnable r = () -> {
        while (true) {
            try {
                Thread.sleep(1000);
                System.out.println("Counter " + counter.getAndSet(0));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    @Test
    public void testParseMessageGroupTypeKeepRunning(){
        n = 50000000;
        FixParser parser = new FixParser(definitions);
        FixParser.DEBUG = false;
        long startTime = System.nanoTime();


        Thread t = new Thread(r);
        t.start();

        try {
            boolean keepRunning = true;
            while(keepRunning){
                try {

                    long time = System.currentTimeMillis();
                    Message m2 = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|123=2|786=9|398=ABC|786=QAS|567=12|496=SDF|398=12|60=" + time+"|44=12|");
                    counter.incrementAndGet();
                    //Do something

                    //Return to pool
                    m2.addToPool();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }

            long endTime = System.nanoTime();

            System.out.println("Time for " + (n * 2) + " times : " +  (endTime - startTime) + "ns using V2Parser");
            //Complex
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void testParseMessageComplexTypeNTimes(){
        FixParser parser = new FixParser(definitions);
        FixParser.DEBUG = false;
        long startTime = System.nanoTime();
        try {
            IntStream.rangeClosed(1, n).forEach(i -> {
                try {
                    Message m1 = parser.parseMessage("8=FIX.4.4|9=392|35=AE|34=2|49=LQNTSWXTR|52=20120525-15:14:58.134|56=SWXLQNTTR|15=CHF|22=4|31=10.52|32=1000|48=CH0023405456|55=[N/A]|60=20110613-15:15:15.234|75=20120525|207=LQNT|487=0|552=2|54=1|37=NONE|453=1|448=9234|447=D|452=7|802=1|523=13173|803=25|528=R|54=2|37=NONE|453=1|448=9209|447=D|452=17|802=1|523=60584|803=25|570=N|571=_LN45|768=1|769=20110613-15:15:14.222|770=1|856=0|880=LN3434343|10=193|");

                    //Do something

                    //Return to pool
                    m1.addToPool();
                }catch(Exception e){
                    e.printStackTrace();
                }
            });

            long endTime = System.nanoTime();

            System.out.println("Time for " + n + " times : " +  (endTime - startTime) + "ns using V2Parser");
            //Complex
        } catch (Exception e) {
            e.printStackTrace();
        }


    }



    int n = 100000000;



    @Test
    public void testParseMessageNTimes(){
        final FixParser parser = new FixParser(definitions);

        long startTime = System.nanoTime();
        for(int i =0 ;i < n; i++){
            try {
                Message m1 = parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|456=7|123=2|786=9|398=ABC|786=QAS|567=12|496=SDF|398=12|44=12|");

                //Do something

                //Return to pool
                m1.addToPool();
            } catch (Exception e) {
                e.printStackTrace();
            }

            //parser.parseMessage("8=345|9=12|55=IBM|40=P|269=2|277=12|283=5|456=7|277=1|231=56|44=12|");
        }
        long endTime = System.nanoTime();

        System.out.println("Time for " + n + " times : " +  (endTime - startTime) + "ns using CharArray");
    }



}
