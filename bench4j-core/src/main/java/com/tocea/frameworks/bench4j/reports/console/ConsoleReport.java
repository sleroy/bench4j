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

// TODO: Auto-generated Javadoc
/**
 * Display results of the report into the console;.
 *
 * @author sleroy
 */
public class ConsoleReport implements IBenchReport {
	
	/**
	 * The Enum TimeUnit.
	 */
	public enum TimeUnit {
		
		/** The second. */
		SECOND(1000d, "s"), /** The millis. */
		MILLIS(1d, "ms");
		
		/** The value. */
		public double value;
		
		/** The unit name. */
		public String unitName;
		
		/**
		 * Instantiates a new time unit.
		 *
		 * @param _value
		 *            the _value
		 * @param _unitName
		 *            the _unit name
		 */
		TimeUnit(final double _value, final String _unitName) {
			value = _value;
			unitName = _unitName;
		}
		
		/**
		 * Gets the unit.
		 *
		 * @return the unit
		 */
		public String getUnit() {
			
			return unitName;
		}
	}
	
	/** The Constant LOGGER. */
	public static final Logger LOGGER = LoggerFactory.getLogger(ConsoleReport.class);
	
	/** The unit. */
	private TimeUnit unit = TimeUnit.SECOND;
	
	/**
	 * Instantiates a new console report.
	 */
	public ConsoleReport() {
	}
	
	/**
	 * Instantiates a new console report.
	 *
	 * @param _unit
	 *            the _unit
	 */
	public ConsoleReport(final TimeUnit _unit) {
		unit = _unit;
	}
	
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.tocea.frameworks.bench4j.IBenchReport#update(org.junit.runners.model
	 * .Statement, org.junit.runners.model.FrameworkMethod, java.lang.Object)
	 */
	@Override
	public void update(final Statement _statement, final FrameworkMethod _description, final Object _target,
			final BenchRecord _record) {
		
		LOGGER.info(
		        "{} ==>\n\twarmupTime(SUM)={}\n\tbenchTime(SUM)={}\n\twarmupTime(median)={}\n\tbenchTime(median)={}\n\tgc_calls={}\n\tgc_time={}",
				_record.getDisplayName(), getUnitStr(_record.getSumWarmupTime()), getUnitStr(_record.getSumBenchTime()),
				getUnitStr(_record.getMedianWarmupTime()),
				getUnitStr(_record.getMedianRunTime()),
				_record.getAccumulatedInvocations(),
				getUnitStr(_record.getAccumulatedTime()));
	}
	
	private String getUnitStr(final double _timeValue) {
		
		return _timeValue / unit.value + " " + unit.unitName;
	}
}
