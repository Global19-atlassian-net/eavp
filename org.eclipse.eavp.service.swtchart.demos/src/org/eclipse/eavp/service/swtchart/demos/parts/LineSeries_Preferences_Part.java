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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.eclipse.eavp.service.swtchart.axisconverter.MillisecondsToMinuteConverter;
import org.eclipse.eavp.service.swtchart.axisconverter.RelativeIntensityConverter;
import org.eclipse.eavp.service.swtchart.core.IChartSettings;
import org.eclipse.eavp.service.swtchart.core.IPrimaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.ISecondaryAxisSettings;
import org.eclipse.eavp.service.swtchart.core.ISeriesData;
import org.eclipse.eavp.service.swtchart.core.RangeRestriction;
import org.eclipse.eavp.service.swtchart.core.SecondaryAxisSettings;
import org.eclipse.eavp.service.swtchart.demos.Activator;
import org.eclipse.eavp.service.swtchart.demos.preferences.LineSeriesDataPreferencePage;
import org.eclipse.eavp.service.swtchart.demos.preferences.LineSeriesPreferenceConstants;
import org.eclipse.eavp.service.swtchart.demos.preferences.LineSeriesPreferencePage;
import org.eclipse.eavp.service.swtchart.demos.preferences.LineSeriesPrimaryAxesPreferencePage;
import org.eclipse.eavp.service.swtchart.demos.preferences.LineSeriesSecondaryAxesPreferencePage;
import org.eclipse.eavp.service.swtchart.demos.support.SeriesConverter;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesData;
import org.eclipse.eavp.service.swtchart.linecharts.ILineSeriesSettings;
import org.eclipse.eavp.service.swtchart.linecharts.LineChart;
import org.eclipse.eavp.service.swtchart.linecharts.LineSeriesData;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.swtchart.IAxis.Position;
import org.swtchart.ILineSeries.PlotSymbolType;
import org.swtchart.LineStyle;

public class LineSeries_Preferences_Part extends Composite {

	private LineChart lineChart;
	private Map<RGB, Color> colors;

	@Inject
	public LineSeries_Preferences_Part(Composite parent) {
		super(parent, SWT.NONE);
		colors = new HashMap<>();
		try {
			initialize();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void dispose() {

		for(Color color : colors.values()) {
			color.dispose();
		}
		super.dispose();
	}

	private void initialize() throws Exception {

		this.setLayout(new GridLayout(1, true));
		/*
		 * Buttons
		 */
		Composite compositeButtons = new Composite(this, SWT.NONE);
		GridData gridDataComposite = new GridData(GridData.FILL_HORIZONTAL);
		gridDataComposite.horizontalAlignment = SWT.END;
		compositeButtons.setLayoutData(gridDataComposite);
		compositeButtons.setLayout(new GridLayout(1, false));
		//
		Button buttonOpenSettings = new Button(compositeButtons, SWT.PUSH);
		modifySettingsButton(buttonOpenSettings);
		buttonOpenSettings.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {

				IPreferencePage preferencePage = new LineSeriesPreferencePage();
				preferencePage.setTitle("Chart Settings");
				IPreferencePage preferencePrimaryAxesPage = new LineSeriesPrimaryAxesPreferencePage();
				preferencePrimaryAxesPage.setTitle("Primary Axes");
				IPreferencePage preferenceSecondaryAxesPage = new LineSeriesSecondaryAxesPreferencePage();
				preferenceSecondaryAxesPage.setTitle("Secondary Axes");
				IPreferencePage preferenceDataPage = new LineSeriesDataPreferencePage();
				preferenceDataPage.setTitle("Series Data");
				//
				PreferenceManager preferenceManager = new PreferenceManager();
				preferenceManager.addToRoot(new PreferenceNode("1", preferencePage));
				preferenceManager.addToRoot(new PreferenceNode("2", preferencePrimaryAxesPage));
				preferenceManager.addToRoot(new PreferenceNode("3", preferenceSecondaryAxesPage));
				preferenceManager.addToRoot(new PreferenceNode("4", preferenceDataPage));
				//
				PreferenceDialog preferenceDialog = new PreferenceDialog(Display.getDefault().getActiveShell(), preferenceManager);
				preferenceDialog.create();
				preferenceDialog.setMessage("Settings");
				if(preferenceDialog.open() == PreferenceDialog.OK) {
					try {
						applyChartSettings();
						applySeriesSettings();
					} catch(Exception e1) {
						System.out.println(e1);
					}
				}
			}
		});
		//
		lineChart = new LineChart(this, SWT.NONE);
		lineChart.setLayoutData(new GridData(GridData.FILL_BOTH));
		//
		applyChartSettings();
		applySeriesSettings();
	}

	private void modifySettingsButton(Button button) {

		button.setToolTipText("Open the Settings");
		button.setText(Activator.getDefault() != null ? "" : "Settings");
		button.setImage(Activator.getDefault() != null ? Activator.getDefault().getImage(Activator.ICON_OPEN_SETTINGS) : null);
	}

	private void applyChartSettings() throws Exception {

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		//
		Color colorHintRangeSelector = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_COLOR_HINT_RANGE_SELECTOR));
		Color colorTitle = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_TITLE_COLOR));
		Color colorBackground = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_BACKGROUND));
		Color colorBackgroundChart = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_BACKGROUND_CHART));
		Color colorBackgroundPlotArea = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_BACKGROUND_PLOT_AREA));
		Color colorPrimaryXAxis = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_COLOR));
		Color colorPrimaryYAxis = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_COLOR));
		Locale localePrimaryXAxis = new Locale(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_DECIMAL_FORMAT_LOCALE));
		Locale localePrimaryYAxis = new Locale(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_DECIMAL_FORMAT_LOCALE));
		Color colorSecondaryXAxis = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_COLOR));
		Color colorSecondaryYAxis = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_COLOR));
		Locale localeSecondaryXAxis = new Locale(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_DECIMAL_FORMAT_LOCALE));
		Locale localeSecondaryYAxis = new Locale(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_DECIMAL_FORMAT_LOCALE));
		Color colorPositionMarker = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_COLOR_POSITION_MARKER));
		Color colorPlotCenterMarker = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_COLOR_PLOT_CENTER_MARKER));
		Color colorLegendMarker = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_COLOR_LEGEND_MARKER));
		Color colorAxisZeroMarker = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_COLOR_AXIS_ZERO_MARKER));
		Color colorSeriesLabelMarker = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_COLOR_SERIES_LABEL_MARKER));
		//
		IChartSettings chartSettings = lineChart.getChartSettings();
		chartSettings.setEnableRangeSelector(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_RANGE_SELECTOR));
		chartSettings.setColorHintRangeSelector(colorHintRangeSelector);
		chartSettings.setRangeSelectorDefaultAxisX(preferenceStore.getInt(LineSeriesPreferenceConstants.P_RANGE_SELECTOR_DEFAULT_AXIS_X));
		chartSettings.setRangeSelectorDefaultAxisY(preferenceStore.getInt(LineSeriesPreferenceConstants.P_RANGE_SELECTOR_DEFAULT_AXIS_Y));
		chartSettings.setVerticalSliderVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_VERTICAL_SLIDER_VISIBLE));
		chartSettings.setHorizontalSliderVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_HORIZONTAL_SLIDER_VISIBLE));
		chartSettings.setTitle(preferenceStore.getString(LineSeriesPreferenceConstants.P_TITLE));
		chartSettings.setTitleVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_TITLE_VISIBLE));
		chartSettings.setTitleColor(colorTitle);
		chartSettings.setLegendPosition(preferenceStore.getInt(LineSeriesPreferenceConstants.P_LEGEND_POSITION));
		chartSettings.setLegendVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_LEGEND_VISIBLE));
		chartSettings.setOrientation(preferenceStore.getInt(LineSeriesPreferenceConstants.P_ORIENTATION));
		chartSettings.setBackground(colorBackground);
		chartSettings.setBackgroundChart(colorBackgroundChart);
		chartSettings.setBackgroundPlotArea(colorBackgroundPlotArea);
		chartSettings.setEnableCompress(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_COMPRESS));
		RangeRestriction rangeRestriction = chartSettings.getRangeRestriction();
		rangeRestriction.setZeroX(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ZERO_X));
		rangeRestriction.setZeroY(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ZERO_Y));
		rangeRestriction.setRestrictZoom(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_RESTRICT_ZOOM));
		rangeRestriction.setXZoomOnly(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_X_ZOOM_ONLY));
		rangeRestriction.setYZoomOnly(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_Y_ZOOM_ONLY));
		rangeRestriction.setForceZeroMinY(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_FORCE_ZERO_MIN_Y));
		rangeRestriction.setFactorExtendMinX(preferenceStore.getDouble(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MIN_X));
		rangeRestriction.setFactorExtendMaxX(preferenceStore.getDouble(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MAX_X));
		rangeRestriction.setFactorExtendMinY(preferenceStore.getDouble(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MIN_Y));
		rangeRestriction.setFactorExtendMaxY(preferenceStore.getDouble(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MAX_Y));
		//
		chartSettings.setShowPositionMarker(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SHOW_POSITION_MARKER));
		chartSettings.setColorPositionMarker(colorPositionMarker);
		chartSettings.setShowPlotCenterMarker(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SHOW_PLOT_CENTER_MARKER));
		chartSettings.setColorPlotCenterMarker(colorPlotCenterMarker);
		chartSettings.setShowLegendMarker(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SHOW_LEGEND_MARKER));
		chartSettings.setColorLegendMarker(colorLegendMarker);
		chartSettings.setShowAxisZeroMarker(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SHOW_AXIS_ZERO_MARKER));
		chartSettings.setColorLegendMarker(colorAxisZeroMarker);
		chartSettings.setShowSeriesLabelMarker(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SHOW_SERIES_LABEL_MARKER));
		chartSettings.setColorSeriesLabelMarker(colorSeriesLabelMarker);
		//
		chartSettings.setCreateMenu(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_CREATE_MENU));
		/*
		 * Primary X-Axis
		 */
		IPrimaryAxisSettings primaryAxisSettingsX = chartSettings.getPrimaryAxisSettingsX();
		primaryAxisSettingsX.setTitle(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_TITLE));
		primaryAxisSettingsX.setDescription(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_DESCRIPTION));
		primaryAxisSettingsX.setDecimalFormat(new DecimalFormat((preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_DECIMAL_FORMAT_PATTERN)), new DecimalFormatSymbols(localePrimaryXAxis)));
		primaryAxisSettingsX.setColor(colorPrimaryXAxis);
		primaryAxisSettingsX.setPosition(Position.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_POSITION)));
		primaryAxisSettingsX.setVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_VISIBLE));
		primaryAxisSettingsX.setGridLineStyle(LineStyle.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_GRID_LINE_STYLE)));
		primaryAxisSettingsX.setEnableLogScale(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_ENABLE_LOG_SCALE));
		primaryAxisSettingsX.setExtraSpaceTitle(preferenceStore.getInt(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_EXTRA_SPACE_TITLE));
		/*
		 * Primary Y-Axis
		 */
		IPrimaryAxisSettings primaryAxisSettingsY = chartSettings.getPrimaryAxisSettingsY();
		primaryAxisSettingsY.setTitle(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_TITLE));
		primaryAxisSettingsY.setDescription(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_DESCRIPTION));
		primaryAxisSettingsY.setDecimalFormat(new DecimalFormat((preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_DECIMAL_FORMAT_PATTERN)), new DecimalFormatSymbols(localePrimaryYAxis)));
		primaryAxisSettingsY.setColor(colorPrimaryYAxis);
		primaryAxisSettingsY.setPosition(Position.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_POSITION)));
		primaryAxisSettingsY.setVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_VISIBLE));
		primaryAxisSettingsY.setGridLineStyle(LineStyle.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_GRID_LINE_STYLE)));
		primaryAxisSettingsY.setEnableLogScale(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_ENABLE_LOG_SCALE));
		primaryAxisSettingsY.setExtraSpaceTitle(preferenceStore.getInt(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_EXTRA_SPACE_TITLE));
		/*
		 * Secondary X-Axes
		 */
		chartSettings.getSecondaryAxisSettingsListX().clear();
		ISecondaryAxisSettings secondaryAxisSettingsX = new SecondaryAxisSettings(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_TITLE), new MillisecondsToMinuteConverter());
		secondaryAxisSettingsX.setDescription(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_DESCRIPTION));
		secondaryAxisSettingsX.setDecimalFormat(new DecimalFormat((preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_DECIMAL_FORMAT_PATTERN)), new DecimalFormatSymbols(localeSecondaryXAxis)));
		secondaryAxisSettingsX.setColor(colorSecondaryXAxis);
		secondaryAxisSettingsX.setPosition(Position.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_POSITION)));
		secondaryAxisSettingsX.setVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_VISIBLE));
		secondaryAxisSettingsX.setGridLineStyle(LineStyle.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_GRID_LINE_STYLE)));
		secondaryAxisSettingsX.setEnableLogScale(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_ENABLE_LOG_SCALE));
		secondaryAxisSettingsX.setExtraSpaceTitle(preferenceStore.getInt(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_EXTRA_SPACE_TITLE));
		chartSettings.getSecondaryAxisSettingsListX().add(secondaryAxisSettingsX);
		/*
		 * Secondary Y-Axes
		 */
		chartSettings.getSecondaryAxisSettingsListY().clear();
		ISecondaryAxisSettings secondaryAxisSettingsY = new SecondaryAxisSettings(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_TITLE), new RelativeIntensityConverter(SWT.VERTICAL, true));
		secondaryAxisSettingsY.setDescription(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_DESCRIPTION));
		secondaryAxisSettingsY.setDecimalFormat(new DecimalFormat((preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_DECIMAL_FORMAT_PATTERN)), new DecimalFormatSymbols(localeSecondaryYAxis)));
		secondaryAxisSettingsY.setColor(colorSecondaryYAxis);
		secondaryAxisSettingsY.setPosition(Position.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_POSITION)));
		secondaryAxisSettingsY.setVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_VISIBLE));
		secondaryAxisSettingsY.setGridLineStyle(LineStyle.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_GRID_LINE_STYLE)));
		secondaryAxisSettingsY.setEnableLogScale(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_ENABLE_LOG_SCALE));
		secondaryAxisSettingsY.setExtraSpaceTitle(preferenceStore.getInt(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_EXTRA_SPACE_TITLE));
		chartSettings.getSecondaryAxisSettingsListY().add(secondaryAxisSettingsY);
		//
		lineChart.applySettings(chartSettings);
	}

	private void applySeriesSettings() {

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		Color lineColorSeries1 = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_LINE_COLOR_SERIES_1));
		Color symbolColorSeries1 = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_SYMBOL_COLOR_SERIES_1));
		Color lineColorSeries2 = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_LINE_COLOR_SERIES_2));
		Color symbolColorSeries2 = getColor(PreferenceConverter.getColor(preferenceStore, LineSeriesPreferenceConstants.P_SYMBOL_COLOR_SERIES_2));
		//
		lineChart.deleteSeries();
		List<ILineSeriesData> lineSeriesDataList = new ArrayList<ILineSeriesData>();
		ISeriesData seriesData;
		ILineSeriesData lineSeriesData;
		ILineSeriesSettings lineSerieSettings;
		/*
		 * Series 1
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_4_1);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setAntialias(preferenceStore.getInt(LineSeriesPreferenceConstants.P_ANTIALIAS_SERIES_1));
		lineSerieSettings.setDescription(preferenceStore.getString(LineSeriesPreferenceConstants.P_DESCRIPTION_SERIES_1));
		lineSerieSettings.setEnableArea(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_AREA_SERIES_1));
		lineSerieSettings.setEnableStack(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_STACK_SERIES_1));
		lineSerieSettings.setEnableStep(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_STEP_SERIES_1));
		lineSerieSettings.setLineColor(lineColorSeries1);
		lineSerieSettings.setLineStyle(LineStyle.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_LINE_STYLE_SERIES_1)));
		lineSerieSettings.setLineWidth(preferenceStore.getInt(LineSeriesPreferenceConstants.P_LINE_WIDTH_SERIES_1));
		lineSerieSettings.setSymbolColor(symbolColorSeries1);
		lineSerieSettings.setSymbolSize(preferenceStore.getInt(LineSeriesPreferenceConstants.P_SYMBOL_SIZE_SERIES_1));
		lineSerieSettings.setSymbolType(PlotSymbolType.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_SYMBOL_TYPE_SERIES_1)));
		lineSerieSettings.setVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_VISIBLE_SERIES_1));
		lineSerieSettings.setVisibleInLegend(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_VISIBLE_IN_LEGEND_SERIES_1));
		lineSeriesDataList.add(lineSeriesData);
		/*
		 * Series 2
		 */
		seriesData = SeriesConverter.getSeriesXY(SeriesConverter.LINE_SERIES_4_2);
		lineSeriesData = new LineSeriesData(seriesData);
		lineSerieSettings = lineSeriesData.getLineSeriesSettings();
		lineSerieSettings.setAntialias(preferenceStore.getInt(LineSeriesPreferenceConstants.P_ANTIALIAS_SERIES_2));
		lineSerieSettings.setDescription(preferenceStore.getString(LineSeriesPreferenceConstants.P_DESCRIPTION_SERIES_2));
		lineSerieSettings.setEnableArea(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_AREA_SERIES_2));
		lineSerieSettings.setEnableStack(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_STACK_SERIES_2));
		lineSerieSettings.setEnableStep(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_ENABLE_STEP_SERIES_2));
		lineSerieSettings.setLineColor(lineColorSeries2);
		lineSerieSettings.setLineStyle(LineStyle.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_LINE_STYLE_SERIES_2)));
		lineSerieSettings.setLineWidth(preferenceStore.getInt(LineSeriesPreferenceConstants.P_LINE_WIDTH_SERIES_2));
		lineSerieSettings.setSymbolColor(symbolColorSeries2);
		lineSerieSettings.setSymbolSize(preferenceStore.getInt(LineSeriesPreferenceConstants.P_SYMBOL_SIZE_SERIES_2));
		lineSerieSettings.setSymbolType(PlotSymbolType.valueOf(preferenceStore.getString(LineSeriesPreferenceConstants.P_SYMBOL_TYPE_SERIES_2)));
		lineSerieSettings.setVisible(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_VISIBLE_SERIES_2));
		lineSerieSettings.setVisibleInLegend(preferenceStore.getBoolean(LineSeriesPreferenceConstants.P_VISIBLE_IN_LEGEND_SERIES_2));
		lineSeriesDataList.add(lineSeriesData);
		//
		lineChart.addSeriesData(lineSeriesDataList, LineChart.HIGH_COMPRESSION);
	}

	private Color getColor(RGB rgb) {

		Color color = colors.get(rgb);
		if(color == null) {
			color = new Color(Display.getDefault(), rgb);
			colors.put(rgb, color);
		}
		return color;
	}
}
