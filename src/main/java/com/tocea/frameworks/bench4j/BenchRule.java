package com.tocea.frameworks.bench4j;

import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.tocea.frameworks.bench4j.reports.console.ConsoleReport;

public class BenchRule implements MethodRule {

	private final IBenchReport	report;

	/**
	 * Creates a benchmark rule with a given sink for benchmark results.
	 */
	public BenchRule() {
		report = new ConsoleReport();

	}

	/**
	 * Creates a benchmark rule with a given sink for benchmark results.
	 */
	public BenchRule(IBenchReport _report) {
		report = _report;

	}

	/**
	 * Apply benchmarking to the given test description.
	 */
	@Override
	public Statement apply(Statement _base, FrameworkMethod _method, Object _target) {
		return new BenchmarkStatement(_base, _method, _target, report);
	}

}
