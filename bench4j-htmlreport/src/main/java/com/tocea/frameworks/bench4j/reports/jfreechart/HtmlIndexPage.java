/**
 *
 */
package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.io.File;
import java.util.Map;

import com.tocea.frameworks.bench4j.impl.BenchRecord;

/**
 * This class generates the html page based on the provided records.
 *
 * @author sleroy
 *
 */
public class HtmlIndexPage {

	private final File	reportFolder;

	/**
	 * Generates the index page
	 *
	 * @param _reportFolder
	 */
	public HtmlIndexPage(File _reportFolder) {
		reportFolder = _reportFolder;

	}

	/**
	 * Generates the index page.
	 *
	 * @param _records
	 *            the list of records.
	 */
	public void generate(Map<RecordKey, BenchRecord> _records) {

	}

}
