package com.tocea.frameworks.bench4j.impl;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tocea.frameworks.bench4j.BenchmarkOptions;
import com.tocea.frameworks.bench4j.IBenchReport;

/**
 * This class defines the benchmark statement.
 *
 * @author sleroy
 *
 */
public class BenchmarkStatement extends Statement {

	private static final String	  DEFAULT_METHOD_OPTIONS	= "defaultOptions";
	private static final Logger	  LOGGER	             = LoggerFactory.getLogger(BenchmarkStatement.class);
	private final Statement	      base;
	private final FrameworkMethod	description;

	private final IBenchReport	  report;
	private final Object	      target;

	public BenchmarkStatement(final Statement _base, final FrameworkMethod _method, final Object _target, final IBenchReport _report) {
		base = _base;
		description = _method;
		target = _target;
		report = _report;

	}

	@Override
	public void evaluate() throws Throwable {
		LOGGER.debug("Launching benchmark {}", description.getName());

		final BenchRecord record = newBenchRecord(description, target);
		final BenchEvaluator benchEvaluator = new BenchEvaluator(record, resolveOptions(), base);
		try {
			benchEvaluator.evaluate();
		} finally {
			report.update(base, description, target, record);
		}
		LOGGER.debug("Benchmark finished {}", description.getName());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.frameworks.bench4j.IBenchReport#newBenchRecord(org.junit.runner
	 * .Description)
	 */
	public BenchRecord newBenchRecord(final FrameworkMethod _description, final Object _target) {

		final BenchRecord benchRecord = new BenchRecord(_description.getDeclaringClass().getName(),
				_description.getName());
		final Map<String, Object> parameters = new GetJUnitParameters(_description, _target).searchParameters();
		for (final Entry<String, Object> field : parameters.entrySet()) {
			benchRecord.putParameter(field.getKey(), field.getValue().toString());
		}
		return benchRecord;

	}

	/* Resolve the options from the junit declaration */
	private BenchmarkOptions resolveOptions() {
		// Method-level
		BenchmarkOptions options = description.getAnnotation(BenchmarkOptions.class);
		if (options != null) { return options; }

		// Class-level
		Class<?> clz = description.getDeclaringClass();
		while (clz != null) {
			options = clz.getAnnotation(BenchmarkOptions.class);
			if (options != null) { return options; }

			clz = clz.getSuperclass();
		}

		// Defaults.
		try {
			return getClass().getDeclaredMethod(DEFAULT_METHOD_OPTIONS).getAnnotation(BenchmarkOptions.class);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

}
