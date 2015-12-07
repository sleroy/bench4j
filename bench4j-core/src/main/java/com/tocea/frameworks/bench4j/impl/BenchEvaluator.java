package com.tocea.frameworks.bench4j.impl;

import org.junit.runners.model.Statement;

import com.tocea.frameworks.bench4j.BenchmarkOptions;

/**
 * The Class BenchEvaluator.
 */
public class BenchEvaluator {
	
	/** The record. */
	private final BenchRecord	   record;
	
	/** The resolve options. */
	private final BenchmarkOptions	resolveOptions;
	
	/** The base. */
	private final Statement	       base;
	
	/**
	 * Instantiates a new bench evaluator.
	 *
	 * @param _record
	 *            the _record
	 * @param _resolveOptions
	 *            the _resolve options
	 * @param _base
	 *            the _base
	 */
	public BenchEvaluator(final BenchRecord _record, final BenchmarkOptions _resolveOptions, final Statement _base) {
		record = _record;
		resolveOptions = _resolveOptions;
		base = _base;
		
	}
	
	/**
	 * Evaluate.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	public void evaluate() throws Throwable {
		final GCSnapshot gcSnapshot = new GCSnapshot();
		
		for (int w = 0; w < resolveOptions.warmupRounds(); w++) {
			final long beginTime = resolveOptions.clock().time();
			try {
				cleanupMemory();
				base.evaluate();
			} finally {
				final long endTime = resolveOptions.clock().time();
				record.addWarmupTime(endTime - beginTime); // millis
			}
		}
		
		for (int b = 0; b < resolveOptions.benchmarkRounds(); b++) {
			final long beginTime = resolveOptions.clock().time();
			try {
				base.evaluate();
			} finally {
				final long endTime = resolveOptions.clock().time();
				record.addBenchTime(endTime - beginTime); // millis
			}
		}
		
		record.setAccumulatedInvocations(gcSnapshot.accumulatedInvocations());
		record.setAccumulatedTime(gcSnapshot.accumulatedTime());
		
	}
	
	/**
	 * Best effort attempt to clean up the memory if
	 * {@link BenchmarkOptions#callgc()} is enabled.
	 */
	void cleanupMemory() {
		if (!resolveOptions.callgc()) { return; }
		
		/*
		 * Best-effort GC invocation. I really don't know of any other way to
		 * ensure a GC pass.
		 */
		System.gc();
		System.gc();
		Thread.yield();
	}
	
}
