/**
 *
 */
package com.tocea.frameworks.bench4j;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;

import com.tocea.frameworks.bench4j.impl.BenchRule;

/**
 * @author sleroy
 *
 */

public class GcTest {

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
