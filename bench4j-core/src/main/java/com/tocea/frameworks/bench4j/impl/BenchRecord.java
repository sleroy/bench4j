package com.tocea.frameworks.bench4j.impl;

import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.apache.commons.math3.stat.descriptive.summary.Sum;

/**
 * This class contains the statistics of execution for a test case.
 *
 * @author sleroy
 *
 */
public class BenchRecord {
	
	private final Map<String, Object>	parameters	= new ConcurrentHashMap();
	
	private final List<Double> benchTimes = new Vector<Double>();
	
	private final List<Double> warmupTimes = new Vector<Double>();
	
	private long	                  accumulatedInvocations;
	private long	                  accumulatedTime;
	
	private String	                  methodName;
	
	private String	                  className;
	
	/**
	 *
	 */
	public BenchRecord() {
		super();
	}
	
	public BenchRecord(final String _className, final String _methodName) {
		super();
		className = _className;
		methodName = _methodName;
	}
	
	public void addBenchTime(final double _res) {
		benchTimes.add(_res);
	}
	
	public void addWarmupTime(final double _res) {
		warmupTimes.add(_res);
	}
	
	public void declareParameter(final String _fieldName) {
		parameters.put(_fieldName, "");
		
	}
	
	public long getAccumulatedInvocations() {
		return accumulatedInvocations;
	}
	
	public long getAccumulatedTime() {
		return accumulatedTime;
	}
	
	public List<Double> getBenchTimes() {
		return benchTimes;
	}
	
	public String getClassName() {
		return className;
	}
	
	/**
	 * Returns a text friendly name for this record.
	 *
	 * @return the display name.
	 */
	public String getDisplayName() {
		
		return getClassName() + ":" + getMethodName() + "(" + getParametersKey() + ")";
	}
	
	/**
	 * Returns the mean for the warmup time.
	 *
	 * @return the warmup time.
	 */
	public double getMeanRunTime() {
		final Mean median = new Mean();
		final double[] res = convertIntoArray(benchTimes);
		
		return median.evaluate(res);
	}
	
	/**
	 * Returns the mean for the bench time.
	 *
	 * @return the warmup time.
	 */
	public double getMeanWarmupTime() {
		final Mean median = new Mean();
		final double[] res = convertIntoArray(warmupTimes);
		
		return median.evaluate(res);
	}
	
	/**
	 * Returns the median for the warmup time.
	 *
	 * @return the warmup time.
	 */
	public double getMedianRunTime() {
		final Median median = new Median();
		final double[] res = convertIntoArray(benchTimes);
		
		return median.evaluate(res);
	}
	
	/**
	 * Returns the median for the bench time.
	 *
	 * @return the warmup time.
	 */
	public double getMedianWarmupTime() {
		final Median median = new Median();
		final double[] res = convertIntoArray(warmupTimes);
		
		return median.evaluate(res);
	}
	
	public String getMethodName() {
		
		return methodName;
	}
	
	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	/**
	 * Returns a key identifying the parameters
	 *
	 * @return the parameters as a key.
	 */
	public String getParametersKey() {
		return new ParameterKey(parameters).get();
		
	}
	
	/**
	 * Returns the sum for the warmup time.
	 *
	 * @return the warmup time.
	 */
	public double getSumBenchTime() {
		final Sum sum = new Sum();
		final double[] res = convertIntoArray(benchTimes);
		
		return sum.evaluate(res);
	}
	
	/**
	 * Returns the sum for the warmup time.
	 *
	 * @return the warmup time.
	 */
	public double getSumWarmupTime() {
		
		final Sum sum = new Sum();
		final double[] res = convertIntoArray(warmupTimes);
		
		return sum.evaluate(res);
	}
	
	public List<Double> getWarmupTimes() {
		return warmupTimes;
	}
	
	public void putParameter(final String _field, final Object _value) {
		parameters.put(_field, _value);
	}
	
	public void setAccumulatedInvocations(final long _accumlatedInvocations) {
		accumulatedInvocations = _accumlatedInvocations;
	}
	
	public void setAccumulatedTime(final long _accumulatedTime) {
		accumulatedTime = _accumulatedTime;
	}
	
	public void setClassName(final String _className) {
		className = _className;
	}
	
	public void setMethodName(final String _methodName) {
		methodName = _methodName;
	}
	
	@Override
	public String toString() {
		return "BenchRecord [parameters=" + parameters + ", benchTimes=" + benchTimes + ", warmupTimes=" + warmupTimes
				+ ", accumulatedInvocations=" + accumulatedInvocations + ", accumulatedTime=" + accumulatedTime
				+ ", methodName=" + methodName + ", className=" + className + "]";
	}
	
	private double[] convertIntoArray(final List<Double> _array) {
		
		final double[] res = new double[_array.size()];
		int i = 0;
		for (final Double d : _array) {
			res[i++] = d.doubleValue();
		}
		return res;
	}
}
