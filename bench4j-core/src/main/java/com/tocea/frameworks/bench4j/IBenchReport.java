package com.tocea.frameworks.bench4j;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.tocea.frameworks.bench4j.impl.BenchRecord;

public interface IBenchReport {

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