package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.tocea.frameworks.bench4j.BenchRecord;

public class BenchComparisonChart {

	private final File	            reportFolder;
	private final List<BenchRecord>	collection;
	private final String	        parameterKey;

	private static final Logger	    LOGGER	= LoggerFactory.getLogger(BenchComparisonChart.class);
	private final int	            width;
	private final int	            height;

	/**
	 * @param _collection
	 * @param _reportFolder
	 * @param _height
	 * @param _width
	 */
	public BenchComparisonChart(Collection<BenchRecord> _collection, File _reportFolder, String _parameterKey,
			int _width, int _height) {
		collection = Lists.newArrayList(_collection);
		Collections.sort(collection, new Comparator<BenchRecord>() {

			@Override
			public int compare(BenchRecord _arg0, BenchRecord _arg1) {

				return _arg0.getMethodName().compareTo(_arg1.getMethodName());
			}

		});
		reportFolder = _reportFolder;
		parameterKey = _parameterKey;
		width = _width;
		height = _height;

	}

	public void generate() throws IOException {

		final JFreeChart medianGraph = createChart(createMedianDataset());
		final File medianGraphFileName = new File(reportFolder, "Comparison_median_" + parameterKey + ".png");
		LOGGER.info("Generating graph in {}", medianGraphFileName);
		ChartUtilities.saveChartAsPNG(medianGraphFileName, medianGraph, width, height);

		final JFreeChart meanChart = createChart(createMeanDataset());
		final File meanGraphFileName = new File(reportFolder, "Comparison_mean_" + parameterKey + ".png");
		LOGGER.info("Generating graph in {}", meanGraphFileName);
		ChartUtilities.saveChartAsPNG(meanGraphFileName, meanChart, width, height);

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
		final JFreeChart chart = ChartFactory.createBarChart("Bench for configuration " + parameterKey, // chart
				// title
				"Implementation", // domain axis label
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
	private final CategoryDataset createMeanDataset() {

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (final BenchRecord res : collection) {
			dataset.addValue(res.getMeanWarmupTime(), res.getMethodName(), "warmup_time");
		}

		for (final BenchRecord res : collection) {
			dataset.addValue(res.getMeanWarmupTime(), res.getMethodName(), "bench_time");
		}

		return dataset;

	}

	/**
	 * Returns a sample dataset.
	 *
	 * @return The dataset.
	 */
	private final CategoryDataset createMedianDataset() {

		// create the dataset...
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (final BenchRecord res : collection) {
			dataset.addValue(res.getMedianWarmupTime(), res.getMethodName(), "warmup_time");
		}

		for (final BenchRecord res : collection) {
			dataset.addValue(res.getMedianWarmupTime(), res.getMethodName(), "bench_time");
		}

		return dataset;

	}
}
