Bench4J
=====


[![Travis Build Status](http://img.shields.io/travis/sleroy/bench4j.svg)](https://travis-ci.org/sleroy/bench4j)
[![Coverage Status](http://img.shields.io/coveralls/sleroy/bench4j.svg)](https://coveralls.io/r/sleroy/bench4j)
[![Semantic Versioning](http://img.shields.io/:semver-0.1.0-blue.svg)](http://semver.org)
[![Bintray](http://img.shields.io/badge/download-latest-bb00bb.svg)](https://bintray.com/sleroy/kordamp/bench4j)


This framework is a fork of the junit micro benchmark framework <http://labs.carrotsearch.com/junit-benchmarks.html>.

Installation
----------------

To use it, simply adds the following lines in your maven project :

```
  <dependency>
  	<groupId>com.tocea.frameworks</groupId>
  	<artifactId>bench4j-core</artifactId>
  	<version>0.1.0</version>
  	<scope>test</scope>
  </dependency>
```

To generate a html report, adds the following extension

```
  <dependency>
  	<groupId>com.tocea.frameworks</groupId>
  	<artifactId>bench4j-htmlreport</artifactId>
  	<version>0.1.0</version>
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

Example of Charts generated :
-----------------------------------

![example1](https://raw.githubusercontent.com/sleroy/bench4j/master/docs/Benchmark_mean.png)

![example2](https://raw.githubusercontent.com/sleroy/bench4j/master/docs/Benchmark_median.png)

![example3](https://raw.githubusercontent.com/sleroy/bench4j/master/docs/compare.png)







