package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tocea.frameworks.bench4j.IBenchReport;
import com.tocea.frameworks.bench4j.impl.BenchRecord;
import com.tocea.frameworks.bench4j.impl.GetJUnitParameters;

public class JFreeChartBenchmarkReport implements IBenchReport {

	private final Map<RecordKey, BenchRecord>	records	 = new HashMap<RecordKey, BenchRecord>();

	private static final Logger	              LOGGER	 = LoggerFactory.getLogger(JFreeChartBenchmarkReport.class);

	private static final int	              MIN_HEIGHT	= 300;

	private static final int	              MIN_WIDTH	 = 400;

	private final File	                      reportFolder;

	private int	                              height	 = MIN_HEIGHT;

	private int	                              width	     = MIN_WIDTH;

	public JFreeChartBenchmarkReport(File _reportFolder) {
		this(_reportFolder, MIN_WIDTH, MIN_HEIGHT, true);

	}

	public JFreeChartBenchmarkReport(File _reportFolder, int width, int height) {
		this(_reportFolder, width, height, true);

	}

	public JFreeChartBenchmarkReport(File _reportFolder, int width, int height, boolean _clearFolder) {
		reportFolder = _reportFolder;
		this.width = width;
		this.height = height;
		reportFolder.mkdirs();
		if (_clearFolder) {
			for (final File file : reportFolder.listFiles()) {
				file.delete();
			}
		}

	}

	/**
	 * Creates a Html report using as subfolder the test name.
	 * 
	 * @param _reportFolder
	 *            the report folder
	 * @param _testName
	 *            the test name
	 * @param width
	 *            the width of the graphics
	 * @param height
	 *            the height of the graphics.
	 */
	public JFreeChartBenchmarkReport(File _reportFolder, String _testName, int width, int height) {
		this(new File(_reportFolder, _testName), width, height, true);
	}

	public Map<RecordKey, BenchRecord> getRecords() {
		return records;
	}

	/*
	 *
	 * (non-Javadoc)
	 *
	 * @see com.tocea.frameworks.bench4j.IBenchReport#update()
	 */
	@Override
	public synchronized void update(Statement _statement, FrameworkMethod _description, Object _target,
			BenchRecord _record) {
		try {
			final Map<String, Object> parameters = new GetJUnitParameters(_description, _target).searchParameters();
			final RecordKey recordKey = new RecordKey(_description, parameters);
			registerRecord(_record, recordKey);

			/**
			 * Refresh site
			 */
			final HtmlIndexPage htmlIndexPage = new HtmlIndexPage(reportFolder);
			htmlIndexPage.generate(records);
			/**
			 * Refresh time variation charts
			 */
			generateTimevariationChart(_statement, _description, _target);
			/**
			 * Refresh bench charts
			 */
			generateBenchChartPerParameter();

		} catch (final Exception e) {

			LOGGER.error(e.getMessage(), e);
		}

	}

	private void generateBenchChartPerParameter() throws IOException {
		new BenchGraphGenerator(records, reportFolder, width, height).generate();
	}

	private void generateTimevariationChart(Statement _statement, FrameworkMethod _description, Object _target)
			throws IOException {
		final Map<String, Object> parameters = new GetJUnitParameters(_description, _target).searchParameters();
		final BenchRecord benchRecord = records.get(new RecordKey(_description, parameters));
		new TimeVariationGraphGenerator(_statement, _description, benchRecord, reportFolder, width, height).generate();
	}

	private void registerRecord(BenchRecord _record, final RecordKey recordKey) {
		final BenchRecord benchRecord = records.get(recordKey);
		if (benchRecord == null) {
			records.put(recordKey, _record);
		}

	}

}
