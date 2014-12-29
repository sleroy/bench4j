/**
 *
 */
package com.tocea.frameworks.bench4j.reports.console;

import java.util.HashMap;
import java.util.Map;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tocea.frameworks.bench4j.BenchRecord;
import com.tocea.frameworks.bench4j.IBenchReport;
import com.tocea.frameworks.bench4j.reports.jfreechart.RecordKey;

/**
 * Display results of the report into the console;
 *
 * @author sleroy
 *
 */
public class ConsoleReport implements IBenchReport {
	/**
	 *
	 */
	private static final double	              PER_SECOND	= 1000.0;

	private final Map<RecordKey, BenchRecord>	records	 = new HashMap<RecordKey, BenchRecord>();

	private static final Logger	              LOGGER	 = LoggerFactory.getLogger(ConsoleReport.class);

	public Map<RecordKey, BenchRecord> getRecords() {
		return records;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tocea.frameworks.bench4j.IBenchReport#newBenchRecord(org.junit.runners
	 * .model.FrameworkMethod)
	 */
	@Override
	public BenchRecord newBenchRecord(FrameworkMethod _description, Object _target) {

		return new BenchRecord(_description.getDeclaringClass().getName(), _description.getName());
	}

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
