package com.tocea.frameworks.bench4j;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public interface IBenchReport {

	/**
	 *
	 * Returns a new record based on the description.
	 *
	 * @param _description
	 *            the description
	 * @return the record;
	 */
	public BenchRecord newBenchRecord(FrameworkMethod _description, Object _target);

	/**
	 * Notify that a statement is finished.
	 *
	 * @param _statement
	 *            the statement
	 * @param _description
	 *            the description
	 * @param _target
	 *            the instance target
	 * @parm _record the record updated;
	 */
	public void update(Statement _statement, FrameworkMethod _description, Object _target, BenchRecord _record);

}