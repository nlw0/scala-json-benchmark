JSON parsing in Scala with multiple libraries

JSON 

This projects demonstrates how to use 5 different Scala JSON libraries to deserialize a `case class`. There is also a program to benchmark the libraries by reading a file multiple times.

Test results from the `birds.dat` file:

<table><tr>
<th>library</th><th>min</th><th>med</th><th>max</th>
<tr><td>   play</td><td> 759</td><td> 982</td><td> 4906</td></tr>
<tr><td> json4s</td><td> 667</td><td> 746</td><td> 3012</td></tr>
<tr><td>   lift</td><td> 563</td><td> 813</td><td> 2125</td></tr>
<tr><td>  spray</td><td> 440</td><td> 544</td><td> 2537</td></tr>
<tr><td>rapture</td><td> 472</td><td> 583</td><td> 2501</td></tr>
</table>

Another test result (unpublished proprietary data):
<table><tr>
<th>library</th><th>min</th><th>med</th><th>max</th>
<tr><td>Rapture</td><td>1113</td><td>1754</td><td>4229</td></tr>
<tr><td>Json4s </td><td> 438</td><td> 893</td><td>2221</td></tr>
<tr><td>Play   </td><td> 471</td><td> 612</td><td>3318</td></tr>
<tr><td>Spray  </td><td> 516</td><td> 582</td><td>2048</td></tr>
<tr><td>Lift   </td><td> 310</td><td> 375</td><td>1882</td></tr>
</table>


   

