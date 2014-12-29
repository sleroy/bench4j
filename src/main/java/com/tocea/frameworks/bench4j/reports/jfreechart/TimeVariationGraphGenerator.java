package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

import com.tocea.frameworks.bench4j.BenchRecord;

public class TimeVariationGraphGenerator {

	private final Statement	      statement;
	private final FrameworkMethod	description;
	private final BenchRecord	  record;

	private final File	          reportFolder;
	private final int	          width;
	private final int	          height;

	public TimeVariationGraphGenerator(Statement _statement, FrameworkMethod _description, BenchRecord _benchRecord,
	        File _reportFolder, int _width, int _height) {
		statement = _statement;
		description = _description;
		record = _benchRecord;
		reportFolder = _reportFolder;
		width = _width;
		height = _height;

	}

	public void generate() throws IOException {
		final CategoryDataset dataset = createDataset();
		final JFreeChart createChart = createChart(dataset);
		ChartUtilities.saveChartAsPNG(new File(reportFolder, "time_" + record.getDisplayName() + ".png"), createChart,
		        width, height);

	}

	/**
	 * Creates a sample chart.
	 *
	 * @param dataset
	 *            the dataset.
	 *
	 * @return The chart.
	 */
	private final JFreeChart createChart(final CategoryDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createBarChart("Time variation for " + record.getDisplayName(), // chart
		        // title
		        "Bench Rounds", // domain axis label
		        "Time in ms", // range axis label
		        dataset, // data
		        PlotOrientation.VERTICAL, // orientation
		        true, // include legend
		        true, // tooltips?
		        false // URLs?
		        );

		// set the background color for the chart...
		chart.setBackgroundPaint(Color.white);

		// get a reference to the plot for further customisation...
		final CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// set the range axis to display integers only...
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		rangeAxis.setUpperMargin(0.15);

		// disable bar outlines...
		final CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

		return chart;

	}

	/**
	 * Returns a sample dataset.
	 *
	 * @return The dataset.
	 */
	private final CategoryDataset createDataset() {

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		int i = 0;
		for (final Double res : record.getWarmupTimes()) {
			dataset.addValue(res, "warmup_time", "#" + i);
			++i;
		}

		int j = 0;
		for (final Double res : record.getBenchTimes()) {
			dataset.addValue(res, "bench_time", "#" + j);
			++j;
		}

		return dataset;

	}
}
