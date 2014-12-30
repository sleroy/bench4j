Bench4J
=====


[![Travis Build Status](http://img.shields.io/travis/sleroy/bench4j.svg)](https://travis-ci.org/sleroy/bench4j)
[![Coverage Status](http://img.shields.io/coveralls/sleroy/bench4j.svg)](https://coveralls.io/r/sleroy/bench4j)
[![Semantic Versioning](http://img.shields.io/:semver-0.1.1-blue.svg)](http://semver.org)
[![Bintray](http://img.shields.io/badge/download-latest-bb00bb.svg)](https://bintray.com/sleroy/kordamp/bench4j)


This framework is a fork of the junit micro benchmark framework <http://labs.carrotsearch.com/junit-benchmarks.html>.

Installation
----------------

To use it, simply adds the following lines in your maven project :

```
  <dependency>
  	<groupId>com.tocea.frameworks</groupId>
  	<artifactId>bench4j-core</artifactId>
  	<version>0.1.1</version>
  	<scope>test</scope>
  </dependency>
```

To generate a html report, adds the following extension

```
  <dependency>
  	<groupId>com.tocea.frameworks</groupId>
  	<artifactId>bench4j-htmlreport</artifactId>
  	<version>0.1.1</version>
  	<scope>test</scope>
  </dependency>
```

Functionalities
-----------------------

Basically, from the fork has been kept the annotation @BenchmarkOptions, however the mecanism of reporting
has been rewriten.

The main functionalities are :

* Compatible with parameterized junit test cases to produce real benchmark with different configurations
* Possibility to rely on Junit functionalities to produce charts with line series.
* Html generation
* Easy customization of the report
* Free access to all bench data to write your own benchs and statistics

Basic example :
------------------------

```
// Plain old Junit test
public class SimpleTest {

	@Rule
	public final BenchRule	rule	= new BenchRule();

	@BenchmarkOptions(callgc = true, benchmarkRounds = 10, warmupRounds = 10)
	@Test
	public void testGC() {
		for (int i = 0; i < 100000; ++i) {
			new ArrayList<>();
		}
	}
}

```

The output is :

```
23:03:49.431 [main] DEBUG c.t.f.b.impl.BenchmarkStatement - Launching benchmark testGC
23:03:49.784 [main] INFO  c.t.f.b.r.console.ConsoleReport - com.tocea.frameworks.bench4j.GcTest:testGC(default) ==>  ( warmupTime(median)=0.0255s, benchTime(median)=5.0E-4s, gc_calls=40, gc_time=0.291s
23:03:49.786 [main] DEBUG c.t.f.b.impl.BenchmarkStatement - Benchmark finished testGC

```

Advanced examples :
------------------------

**Parameterized tests**

```
@RunWith(Parameterized.class)
public class ParameterizedConsoleTest {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 100 }, { 200 }, { 400 }, { 600 } });
	}

	@Parameter(value = 0)
	public int	                     steps;

	public static final IBenchReport	report	= new ConsoleReport();

	@Rule
	public BenchRule	             rule	   = new BenchRule(report);

	@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 5)
	@Test
	public void iterationCostTime() {
		final int[][][] board = new int[steps][steps][steps];
		for (int i = 0; i < steps; ++i) {
			for (int z = 0; z < steps; ++z) {
				for (int k = 0; k < steps; ++k) {
					board[i][z][k] = 2;
				}
			}

		}
	}
}
```

In this example, we are using a custom report (ConsoleReport). Each test case is launched with a set of parameters (100, 200, ...).
Each combination of (testcase, parameter) is benched through many rounds of  warmup + benchs.

The report prints the results of the execution as :

```
com.tocea.frameworks.bench4j.ParameterizedConsoleTest:iterationCostTime(steps=100) ==>  ( warmupTime(median)=0.002s, benchTime(median)=0.002s, gc_calls=1, gc_time=0.005s
c.t.f.b.r.console.ConsoleReport - com.tocea.frameworks.bench4j.ParameterizedConsoleTest:iterationCostTime(steps=200) ==>  ( warmupTime(median)=0.015s, benchTime(median)=0.01s, gc_calls=4, gc_time=0.021s
c.t.f.b.r.console.ConsoleReport - com.tocea.frameworks.bench4j.ParameterizedConsoleTest:iterationCostTime(steps=400) ==>  ( warmupTime(median)=0.124s, benchTime(median)=0.106s, gc_calls=13, gc_time=0.592s
c.t.f.b.r.console.ConsoleReport - com.tocea.frameworks.bench4j.ParameterizedConsoleTest:iterationCostTime(steps=600) ==>  ( warmupTime(median)=0.913s, benchTime(median)=0.678s, gc_calls=56, gc_time=5.969s

```


Example of generated charts :
-----------------------------------

![example1](https://raw.githubusercontent.com/sleroy/bench4j/master/docs/Benchmark_mean.png)

![example2](https://raw.githubusercontent.com/sleroy/bench4j/master/docs/Benchmark_median.png)

![example3](https://raw.githubusercontent.com/sleroy/bench4j/master/docs/compare.png)







