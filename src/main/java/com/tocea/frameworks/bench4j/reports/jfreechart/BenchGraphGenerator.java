/**
 *
 */
package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.tocea.frameworks.bench4j.BenchRecord;

/**
 * @author sleroy
 *
 */
public class BenchGraphGenerator {

	private final Map<RecordKey, BenchRecord>	records;
	private final File	                      reportFolder;
	private final int	                      width;
	private final int	                      height;

	/**
	 * @param _records
	 * @param _reportFolder
	 * @param _height
	 * @param _width
	 */
	public BenchGraphGenerator(Map<RecordKey, BenchRecord> _records, File _reportFolder, int _width, int _height) {
		super();
		records = _records;
		reportFolder = _reportFolder;
		width = _width;
		height = _height;
	}

	/**
	 * @throws IOException
	 *
	 */
	public void generate() throws IOException {

		generateComparisonGraphs();

	}

	/**
	 * @param _groupByParameters
	 * @throws IOException
	 */
	private void generateComparisonGraphs() throws IOException {

		final Multimap<String, BenchRecord> groupByParameters = groupByParameters();
		new AggregateBenchComparisonChart(groupByParameters, reportFolder, width, height).generate();
		for (final String parameter : groupByParameters.keySet()) {
			new BenchComparisonChart(groupByParameters.get(parameter), reportFolder, parameter, width, height)
			        .generate();
		}

	}

	/**
	 * Generates a multimap group by parameters.
	 *
	 * @param parameters
	 * @return
	 */
	private Multimap<String, BenchRecord> groupByParameters() {
		final Multimap<String, BenchRecord> parameters = MultimapBuilder.hashKeys().arrayListValues().build();
		for (final BenchRecord record : records.values()) {
			final String paramKey = record.getParametersKey();
			parameters.put(paramKey, record);
		}
		return parameters;
	}

}
