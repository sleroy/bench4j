/**
 *
 */
package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.util.Map;

import org.thymeleaf.context.Context;

import com.tocea.frameworks.bench4j.impl.BenchRecord;

/**
 * This class defines a thymeleaf context with bench values.
 * 
 * @author sleroy
 *
 */
public class BenchContext extends Context {

	private final Map<RecordKey, BenchRecord>	records;

	/**
	 * @param _records
	 */
	public BenchContext(Map<RecordKey, BenchRecord> _records) {
		super();
		records = _records;
		setVariable("records", _records);

	}

}
