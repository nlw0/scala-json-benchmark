# A Scala JSON parsing benchmark experiment

### Introduction
JSON data reading and writing is an "exciting" subject in Scala, because there are many competing libraries, and it seems all of them refuse to die. This project seeks to make another benchmark from these projects, more specifically from [Rapture](https://github.com/propensive/rapture-json), [Lift](https://github.com/lift/lift/tree/master/framework/lift-base/lift-json/), [Spray](https://github.com/spray/spray-json), [Play](https://github.com/playframework/playframework/tree/master/framework/src/play-json/src) and [Json4s](https://github.com/json4s/json4s).

This projects also serves to demonstrate how to use these different Scala JSON libraries to deserialize a `case class`. Of cource, there is also our code to run the benchmark and to generate test data, that might be useful to someone.

### About the libraries

Some of these libraries (Rapture and Json4s) use Jackson, the Java JSON-parsing library. Some of them are closer to native Scala (Lift). All of them offer similar ASTs to handle the parsed data, usually with some way to query the objects, and also some way to generate objects from a `case class` definition.

It seems from the documentation that Play took their time to offer a nice automatic way to extract case classes instead of forcing you to write code to pick the value from each field. But now all these libraries work in similar ways, although there is still some room for choosing one of them to your project based on taste or need. They have different suble restrictions on what kind of `implicit` you have to define in your classes for the extraction to work. And there are other practical concerns, of course. If you are using Play, Lift or Spray you probably would like to stick to their native JSON handling libraries, and some libraries are not available in any just platform.
     
     
### Experiment     
In our study here we were only concerned with a "big data" scenario. We have a large file with thousands of JSON objects, one per text line, and we just wanted to see which library could read them faster.
   
The `ScalaJsonBenchmark.scala` file contains classes with a JSON parser created using each library tested. That file also carries out the experiment, reading the file to memory first as an `Array[String]`. This array was then processed 11 times with each parser. The parsers were executed in sequence at each of these iterations, and not just one parser at a time.
    
The time taken in the exeution was measured using `System.currentTimeMillis()`, which is not the perfect method, but was good enough to provide some interesting results.
 
This experiment was performed in April 2014 with an old lousy notebook computer (4GB RAM + _Intel(R) Core(TM) i5-2450M CPU @ 2.50GHz_ )

##### Results from set 1
These are the test results from the `birds.dat` file, a file with roughly 10 megabytes. (We used to call that a "box of diskettes" back in the day...) This file is provided in this project for you to reproduce the experiment. We also provided the program used to generate this data from the model classes (`Bird` and `Place`) with a bunch of random data generation functions. The tables below show the minimum, median and maximum times in milliseconds taken by each library to parse all the data. 

First run:
<table><tr>
<th>library</th><th>min</th><th>med</th><th>max</th>
<tr><td>Play   </td><td> 759</td><td> 982</td><td> 4906</td></tr>
<tr><td>Lift   </td><td> 563</td><td> 813</td><td> 2125</td></tr>
<tr><td>Json4s </td><td> 667</td><td> 746</td><td> 3012</td></tr>
<tr><td>Rapture</td><td> 472</td><td> 583</td><td> 2501</td></tr>
<tr><td>Spray  </td><td> 440</td><td> 544</td><td> 2537</td></tr>
</table>

Second run:
<table><tr>
<th>library</th><th>min</th><th>med</th><th>max</th>
<tr><td>Lift   </td><td> 575</td><td> 1028</td><td> 3090</td></tr>
<tr><td>Play   </td><td> 782</td><td>  892</td><td> 3693</td></tr>
<tr><td>Json4s </td><td> 607</td><td>  806</td><td> 2462</td></tr>
<tr><td>Rapture</td><td> 495</td><td>  707</td><td> 2668</td></tr>
<tr><td>Spray  </td><td> 439</td><td>  539</td><td> 1753</td></tr>
</table>

##### Results from set 2
We have also performed the same test with another file with 4MB and about 10.000 lines. The objects were similar in having a lot of fields with different types, some of them optional, and some of them were lists of objects.
 
First run:
<table><tr>
<th>library</th><th>min</th><th>med</th><th>max</th>
<tr><td>Rapture</td><td>1113</td><td>1754</td><td>4229</td></tr>
<tr><td>Json4s </td><td> 438</td><td> 893</td><td>2221</td></tr>
<tr><td>Play   </td><td> 471</td><td> 612</td><td>3318</td></tr>
<tr><td>Spray  </td><td> 516</td><td> 582</td><td>2048</td></tr>
<tr><td>Lift   </td><td> 310</td><td> 375</td><td>1882</td></tr>
</table>

Second run:
<table><tr>
<th>library</th><th>min</th><th>med</th><th>max</th>
<tr><td>Rapture</td><td> 1014</td><td> 1566</td><td> 4452</td></tr>
<tr><td>Spray  </td><td>  470</td><td>  661</td><td> 1659</td></tr>
<tr><td>Play   </td><td>  507</td><td>  645</td><td> 2490</td></tr>
<tr><td>Json4s </td><td>  390</td><td>  520</td><td> 3924</td></tr>
<tr><td>Lift   </td><td>  340</td><td>  444</td><td> 2374</td></tr>
</table>


### Conclusion
This experiment was quite surprising because while the performance from the libraries was consistent in different runs on the same test file, they performed very differently on the two test cases. More specifically, Lift was kind of bad at parsing the `birds.dat`, but it kicked ass at the secret data file. At the same time Rapture showed the opposite performance.   

As for the other libraries, Spray, Play and Json4s all exhibited a roughly similar performance.  
