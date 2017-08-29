/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.eavp.service.swtchart.demos.parts;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.customcharts.ChromatogramChart;
import org.eclipse.eavp.service.swtchart.demos.support.SeriesConverter;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.eavp.service.swtchart.marker.LabelMarker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.IPlotArea;
import org.swtchart.LineStyle;

public class LineSeries_1_Part extends ChromatogramChart {

	private int indexSeries;

	@Inject
	public LineSeries_1_Part(Composite parent) {
		super(parent, SWT.NONE);
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		//
		try {
			initialize();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	private void initialize() throws Exception {

		/*
		 * Chart Settings
		 */
		IChartSettings chartSettings = getChartSettings();
		chartSettings.setCreateMenu(true);
		applySettings(chartSettings);
		/*
		 * Create series.
		 */
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		//
		ISeriesData seriesData;
		ILineSeriesData lineSeriesData;
		ILineSeriesSettings lineSerieSettings;
		/*
		 * Chromatogram [0]
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Baseline [1]
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_BASELINE);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSerieSettings.setSymbolType(PlotSymbolType.NONE);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Selected Scans [2]
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_SELECTED_SCANS);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setLineStyle(LineStyle.NONE);
		lineSerieSettings.setSymbolType(PlotSymbolType.CROSS);
		lineSerieSettings.setSymbolSize(5);
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Active Peaks [3]
		 */
		indexSeries = 3;
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_ACTIVE_PEAKS);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(false);
		lineSerieSettings.setLineStyle(LineStyle.NONE);
		lineSerieSettings.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
		lineSerieSettings.setSymbolSize(5);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Inactive Peaks
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_INACTIVE_PEAKS);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(false);
		lineSerieSettings.setLineStyle(LineStyle.NONE);
		lineSerieSettings.setSymbolType(PlotSymbolType.INVERTED_TRIANGLE);
		lineSerieSettings.setSymbolSize(5);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Peak 1
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_SELECTED_PEAK_1);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSerieSettings.setSymbolType(PlotSymbolType.CIRCLE);
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSerieSettings.setSymbolSize(2);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Background 1
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_SELECTED_PEAK_1_BACKGROUND);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSerieSettings.setSymbolType(PlotSymbolType.NONE);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Peak 2
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_SELECTED_PEAK_2);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSerieSettings.setSymbolType(PlotSymbolType.CIRCLE);
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSerieSettings.setSymbolSize(2);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Background 2
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_SELECTED_PEAK_2_BACKGROUND);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setEnableArea(true);
		lineSerieSettings.setSymbolType(PlotSymbolType.NONE);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Identified Scans
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_IDENTIFIED_SCANS);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setLineStyle(LineStyle.NONE);
		lineSerieSettings.setSymbolType(PlotSymbolType.CIRCLE);
		lineSerieSettings.setSymbolSize(3);
		lineSerieSettings.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_GRAY));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Identified Scans Selected.
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_1_IDENTIFIED_SCANS_SELECTED);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setLineStyle(LineStyle.NONE);
		lineSerieSettings.setSymbolType(PlotSymbolType.CIRCLE);
		lineSerieSettings.setSymbolSize(5);
		lineSerieSettings.setSymbolColor(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Set series.
		 */
		addSeriesData(lineSeriesDataList);
		IPlotArea plotArea = (IPlotArea)getBaseChart().getPlotArea();
		LabelMarker labelMarker = new LabelMarker(getBaseChart());
		List<String> labels = new ArrayList<String>();
		labels.add("2-Methoxy-4-vinylphenol");
		labels.add("Ethanone, 1-(2-hydroxy-5-methylphenyl)-");
		labels.add("4-Hydroxy-2-methylacetophenone");
		labels.add("Ethanone, 1-(2-hydroxy-5-methylphenyl)-");
		labels.add("4-Hydroxy-3-methylacetophenone");
		labels.add("3-Methoxyacetophenone");
		labels.add("3-Methyl-4-isopropylphenol");
		labels.add("Phenol, 3,4-dimethoxy-");
		labels.add("2,4-Dimethoxyphenol");
		labels.add("3-Amino-2,6-dimethoxypyridine");
		labelMarker.setLabels(labels, indexSeries, SWT.VERTICAL);
		plotArea.addCustomPaintListener(labelMarker);
	}
}
