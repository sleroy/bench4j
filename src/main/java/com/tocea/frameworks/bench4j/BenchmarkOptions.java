package com.tocea.frameworks.bench4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Benchmark options applicable to methods annotated as tests.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface BenchmarkOptions {

	/**
	 * Sets the number of benchmark rounds for the test. If negative, the
	 * default is taken from global options.
	 */
	int benchmarkRounds() default -1;

	/**
	 * @return Call {@link System#gc()} before each test. This may slow down the
	 *         tests in a significant way, so disabling it is sensible in most
	 *         cases.
	 */
	boolean callgc() default false;

	/**
	 * Sets the type of clock to be used for time measuring. See {@link Clock}
	 * for available values.
	 *
	 */
	Clock clock() default Clock.REAL_TIME;

	/**
	 * Sets the number of warmup rounds for the test. If negative, the default
	 * is taken from global options.
	 */
	int warmupRounds() default -1;

}