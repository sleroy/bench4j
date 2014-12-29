package com.tocea.frameworks.bench4j.impl;

import org.junit.runners.model.Statement;

import com.tocea.frameworks.bench4j.BenchmarkOptions;

public class BenchEvaluator {

	private final BenchRecord	   record;
	private final BenchmarkOptions	resolveOptions;
	private final Statement	       base;

	public BenchEvaluator(BenchRecord _record, BenchmarkOptions _resolveOptions, Statement _base) {
		record = _record;
		resolveOptions = _resolveOptions;
		base = _base;

	}

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
