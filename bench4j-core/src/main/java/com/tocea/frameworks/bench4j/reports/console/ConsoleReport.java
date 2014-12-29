/**
 *
 */
package com.tocea.frameworks.bench4j.reports.console;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tocea.frameworks.bench4j.IBenchReport;
import com.tocea.frameworks.bench4j.impl.BenchRecord;

/**
 * Display results of the report into the console;
 *
 * @author sleroy
 *
 */
public class ConsoleReport implements IBenchReport {

	public static final Logger	LOGGER	   = LoggerFactory.getLogger(ConsoleReport.class);

	/**
	 *
	 */
	private static final double	PER_SECOND	= 1000.0;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.tocea.frameworks.bench4j.IBenchReport#update(org.junit.runners.model
	 * .Statement, org.junit.runners.model.FrameworkMethod, java.lang.Object)
	 */
	@Override
	public void update(Statement _statement, FrameworkMethod _description, Object _target, BenchRecord _record) {
		LOGGER.info("{} ==>  ( warmupTime(median)={}s, benchTime(median)={}s, gc_calls={}, gc_time={}s",
				_record.getDisplayName(), _record.getMedianWarmupTime() / PER_SECOND, _record.getMedianRunTime()
		                / PER_SECOND, _record.getAccumulatedInvocations(), _record.getAccumulatedTime() / PER_SECOND);
	}
}
