package com.tocea.frameworks.bench4j;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.tocea.frameworks.bench4j.impl.BenchRule;
import com.tocea.frameworks.bench4j.reports.jfreechart.JFreeChartBenchmarkReport;

@RunWith(Parameterized.class)
public class ParameterizedJFreeChartTest {

	@Parameters(name = "{0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] { { 100 }, { 200 }, { 400 }, { 600 } });
	}

	@Parameter(value = 0)
	public int	                     steps;

	public static final IBenchReport	report	= new JFreeChartBenchmarkReport(new File("target"), 800, 600);

	@Rule
	public BenchRule	             rule	   = new BenchRule(report);

	@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 5)
	@Test
	public void iterationThreeSize() {
		final int[][][] board = new int[steps][steps][steps];
		for (int i = 0; i < steps; ++i) {
			for (int z = 0; z < steps; ++z) {
				for (int k = 0; k < steps; ++k) {
					board[i][z][k] = 2;
				}
			}

		}
	}

	@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 5)
	@Test
	public void iterationThreeSizeModified() {
		final int[][][] board = new int[steps][steps][steps];
		for (int i = 0; i < steps; ++i) {
			for (int z = 0; z < steps; ++z) {
				for (int k = 0; k < steps; ++k) {
					board[z][k][i] = 2;
				}
			}

		}
	}

	@BenchmarkOptions(warmupRounds = 5, benchmarkRounds = 5)
	@Test
	public void iterationTwoSize() {
		final int[][] board = new int[steps][steps];
		for (int i = 0; i < steps; ++i) {
			for (int z = 0; z < steps; ++z) {
				board[i][z] = 2;
			}

		}
	}
}
