### **1. Introduction**

In this 

article we’re going to focus on a practical, code-focused intro to Spring Batch. Spring Batch is a processing framework designed for robust execution of jobs.

It’s current version 3.0, which supports Spring 4 and Java 8. It also accommodates JSR-352, which is new 

java specification for batch processing.

[Here are](http://docs.spring.io/spring-batch/reference/htmlsingle/#springBatchUsageScenarios) a few interesting and practical use-cases of the framework.

### **2. Workflow Basics **

![Image](http://docs.spring.io/spring-batch/reference/html/images/spring-batch-reference-model.png)

Spring batch follows the traditional batch architecture where a job repository does the work of scheduling and interacting with the job.

A job can have more than one steps – and every step typically follows the sequence of reading data, processing it and writing it.

And of 

course the framework will do most of the heavy lifting for us here – especially when it comes to the 

low level persistence work of dealing with the jobs – using _

sqlite_ for the job repository