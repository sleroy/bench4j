/**
 *
 */
package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

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
	 * @throws IOException
	 */
	public void generate(Map<RecordKey, BenchRecord> _records) throws IOException {
		final TemplateEngine templateEngine = new TemplateEngine();
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

		// XHTML is the default mode, but we set it anyway for better
		// understanding of code
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(true);
		templateResolver.setPrefix("templates/");
		templateResolver.setSuffix(".html");
		templateEngine.setTemplateResolver(templateResolver);
		try (final FileWriter fw = new FileWriter(new File(reportFolder, "index.html"))) {
			templateEngine.process("index", new BenchContext(_records), fw);
		}
	}
}
