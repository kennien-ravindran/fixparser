# Fix Parser


**Configure**

Need to set appropriate Pool size for Messages
This reduces the new Object creation


**Reading Data of Message**

When reading group tag, always check the noOfGroups count.

Based on the count call
 noOfGroup = 1 then call getGroup, this will return 1 single group Message
 noOfGroup > 1 then call getGroups, this will return a List of group Messages



**Testing**

MessageDefinition.loadTestDefinition has sample message definition, with includes nest Group


**Benchmark**

Was run on a Macbook Pro 2.7 GHz Intel Core i7

```sh
Benchmark                              Mode  Cnt       Score       Error  Units
BenchmarkParser.testParserThroughput  thrpt    5  443712.170 ± 31581.702  ops/s
BenchmarkParser.testParserLatency      avgt    5    2243.113 ±    76.570  ns/op
```


