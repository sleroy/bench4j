package com.tocea.frameworks.bench4j.reports.jfreechart;

import java.awt.Color;
import java.awt.Font;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Multimap;
import com.tocea.frameworks.bench4j.impl.BenchRecord;

public class AggregateBenchComparisonChart {

	private final File	                        reportFolder;
	private final Multimap<String, BenchRecord>	collection;

	private static final Logger	                LOGGER	= LoggerFactory.getLogger(AggregateBenchComparisonChart.class);
	private final int	                        width;
	private final int	                        height;

	/**
	 * @param _collection
	 * @param _reportFolder
	 * @param _height
	 * @param _width
	 */
	public AggregateBenchComparisonChart(Multimap<String, BenchRecord> _groupByParameters, File _reportFolder,
	        int _width, int _height) {
		collection = _groupByParameters;

		reportFolder = _reportFolder;
		width = _width;
		height = _height;

	}

	public void generate() throws IOException {

		final JFreeChart medianGraph = createChart(createMedianDataset());
		final File medianGraphFileName = new File(reportFolder, "Benchmark_median.png");
		LOGGER.info("Generating graph in {}", medianGraphFileName);
		ChartUtilities.saveChartAsPNG(medianGraphFileName, medianGraph, width, height);

		final JFreeChart meanChart = createChart(createMeanDataset());
		final File meanGraphFileName = new File(reportFolder, "Benchmark_mean.png");
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
		final JFreeChart chart = ChartFactory.createBarChart("Benchmark",
				// title
				"Parameter/Testcase", // domain axis label
				"Time in ms", // range axis label
				dataset, // data
				PlotOrientation.HORIZONTAL, // orientation
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
		rangeAxis.setAutoRange(true);
		rangeAxis.setLabelFont(new Font("Dialog", Font.PLAIN, 8));

		// disable bar outlines...
		final CategoryItemRenderer renderer = plot.getRenderer();
		renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);

		final CategoryAxis domainAxis = plot.getDomainAxis();
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
		domainAxis.setLabelFont(new Font("Dialog", Font.PLAIN, 8));
		domainAxis.setMaximumCategoryLabelWidthRatio(3f);
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

		for (final BenchRecord res : collection.values()) {
			dataset.addValue(res.getMeanRunTime(), res.getMethodName(), res.getParametersKey());
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

		for (final BenchRecord res : collection.values()) {
			dataset.addValue(res.getMeanRunTime(), res.getMethodName(), res.getParametersKey());
		}

		return dataset;

	}
}
