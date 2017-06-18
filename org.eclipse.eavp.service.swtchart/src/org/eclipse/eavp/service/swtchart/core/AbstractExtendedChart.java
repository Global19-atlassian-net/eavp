/*******************************************************************************
 * Copyright (c) 2017 Lablicate GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * Dr. Philip Wenig - initial API and implementation
 *******************************************************************************/
package org.eclipse.eavp.service.swtchart.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.eavp.service.swtchart.exceptions.SeriesException;
import org.eclipse.swt.widgets.Composite;
import org.swtchart.IAxis;
import org.swtchart.IAxis.Direction;
import org.swtchart.IAxisSet;
import org.swtchart.ISeries;
import org.swtchart.ISeries.SeriesType;
import org.swtchart.ISeriesSet;
import org.swtchart.Range;

public abstract class AbstractExtendedChart extends AbstractHandledChart implements IChartDataCoordinates, IRangeSupport, IExtendedChart {

	private boolean useZeroY;
	private boolean useZeroX;
	private double length;
	private double minX;
	private double maxX;
	private double minY;
	private double maxY;
	private boolean useRangeRestriction;
	/*
	 * The settings are used to get the description
	 * or to get the IAxisScaleConverter of the
	 * secondary axes.
	 */
	private Map<Integer, IAxisSettings> xAxisSettingsMap;
	private Map<Integer, IAxisSettings> yAxisSettingsMap;

	public AbstractExtendedChart(Composite parent, int style) {
		super(parent, style);
		xAxisSettingsMap = new HashMap<Integer, IAxisSettings>();
		yAxisSettingsMap = new HashMap<Integer, IAxisSettings>();
		resetCoordinates();
	}

	@Override
	public boolean isUseZeroY() {

		return useZeroY;
	}

	@Override
	public void setUseZeroY(boolean useZeroY) {

		this.useZeroY = useZeroY;
	}

	@Override
	public boolean isUseZeroX() {

		return useZeroX;
	}

	@Override
	public void setUseZeroX(boolean useZeroX) {

		this.useZeroX = useZeroX;
	}

	@Override
	public double getLength() {

		return length;
	}

	@Override
	public double getMinX() {

		return minX;
	}

	@Override
	public double getMaxX() {

		return maxX;
	}

	@Override
	public double getMinY() {

		return minY;
	}

	@Override
	public double getMaxY() {

		return maxY;
	}

	@Override
	public boolean isUseRangeRestriction() {

		return useRangeRestriction;
	}

	@Override
	public void setUseRangeRestriction(boolean useRangeRestriction) {

		this.useRangeRestriction = useRangeRestriction;
	}

	public Map<Integer, IAxisSettings> getXAxisSettingsMap() {

		return xAxisSettingsMap;
	}

	public Map<Integer, IAxisSettings> getYAxisSettingsMap() {

		return yAxisSettingsMap;
	}

	@Override
	public void setRange(IAxis axis, int xStart, int xStop, boolean adjustMinMax) {

		if(axis != null && Math.abs(xStop - xStart) > 0 && !isUpdateSuspended()) {
			double start = axis.getDataCoordinate(Math.min(xStart, xStop));
			double stop = axis.getDataCoordinate(Math.max(xStart, xStop));
			setRange(axis, start, stop, adjustMinMax);
		}
	}

	@Override
	public void setRange(IAxis axis, double start, double stop, boolean adjustMinMax) {

		if(axis != null && Math.abs(stop - start) > 0 && !isUpdateSuspended()) {
			double min = Math.min(start, stop);
			double max = Math.max(start, stop);
			axis.setRange(new Range(min, max));
			if(adjustMinMax) {
				adjustMinMaxRange(axis);
			}
			//
			if(axis.getDirection() == Direction.X) {
				adjustSecondaryXAxes();
			} else if(axis.getDirection() == Direction.Y) {
				adjustSecondaryYAxes();
			}
		}
	}

	@Override
	public void adjustMinMaxRange(IAxis axis) {

		if(axis != null && !isUpdateSuspended()) {
			Range range = axis.getRange();
			if(axis.getDirection().equals(Direction.X)) {
				/*
				 * X-AXIS
				 */
				if(useZeroX) {
					range.lower = (range.lower < 0) ? 0 : range.lower;
				} else {
					range.lower = (range.lower < minX) ? minX : range.lower;
				}
				range.upper = (range.upper > maxX) ? maxX : range.upper;
			} else {
				/*
				 * Y-AXIS
				 */
				if(useZeroY) {
					range.lower = (range.lower < 0) ? 0 : range.lower;
				} else {
					range.lower = (range.lower < minY) ? minY : range.lower;
				}
				range.upper = (range.upper > maxY) ? maxY : range.upper;
			}
			/*
			 * Adjust the range.
			 */
			axis.setRange(range);
		}
	}

	@Override
	public ISeries createSeries(SeriesType seriesType, double[] xSeries, double[] ySeries, String id) throws SeriesException {

		if(xSeries.length == ySeries.length) {
			ISeriesSet seriesSet = getSeriesSet();
			ISeries series = seriesSet.createSeries(seriesType, id);
			series.setXSeries(xSeries);
			series.setYSeries(ySeries);
			calculateCoordinates(series);
			return series;
		} else {
			throw new SeriesException("The length of x and y series differs.");
		}
	}

	@Override
	public void deleteSeries(String id) {

		ISeriesSet seriesSet = getSeriesSet();
		if(seriesSet.getSeries(id) != null) {
			resetCoordinates();
			seriesSet.deleteSeries(id);
			for(ISeries series : seriesSet.getSeries()) {
				calculateCoordinates(series);
			}
		}
	}

	@Override
	public void setRange(String axis, double start, double stop) {

		IAxisSet axisSet = getAxisSet();
		IAxis selectedAxis = (axis.equals(IExtendedChart.X_AXIS)) ? axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS) : axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		setRange(selectedAxis, start, stop, true);
	}

	@Override
	public void adjustRange(boolean adjustMinMax) {

		if(!isUpdateSuspended()) {
			getAxisSet().adjustRange();
			if(adjustMinMax) {
				adjustMinMaxRange(getAxisSet().getXAxis(BaseChart.ID_PRIMARY_X_AXIS));
				adjustMinMaxRange(getAxisSet().getYAxis(BaseChart.ID_PRIMARY_Y_AXIS));
			}
			/*
			 * Adjust the secondary axes.
			 */
			adjustSecondaryXAxes();
			adjustSecondaryYAxes();
		}
	}

	public void adjustSecondaryAxes() {

		adjustSecondaryXAxes();
		adjustSecondaryYAxes();
	}

	@Override
	public void adjustSecondaryXAxes() {

		IAxisSet axisSet = getAxisSet();
		IAxis xAxis = axisSet.getXAxis(BaseChart.ID_PRIMARY_X_AXIS);
		Range range = xAxis.getRange();
		for(int id : axisSet.getXAxisIds()) {
			if(id != BaseChart.ID_PRIMARY_X_AXIS) {
				IAxis axis = axisSet.getXAxis(id);
				IAxisSettings axisSettings = xAxisSettingsMap.get(id);
				if(axis != null && axisSettings instanceof ISecondaryAxisSettings) {
					IAxisScaleConverter axisScaleConverter = ((ISecondaryAxisSettings)axisSettings).getAxisScaleConverter();
					axisScaleConverter.setChartDataCoordinates(this);
					double start = axisScaleConverter.convertToSecondaryUnit(range.lower);
					double end = axisScaleConverter.convertToSecondaryUnit(range.upper);
					Range adjustedRange = new Range(start, end);
					axis.setRange(adjustedRange);
				}
			}
		}
	}

	@Override
	public void adjustSecondaryYAxes() {

		IAxisSet axisSet = getAxisSet();
		IAxis yAxis = axisSet.getYAxis(BaseChart.ID_PRIMARY_Y_AXIS);
		Range range = yAxis.getRange();
		for(int id : axisSet.getYAxisIds()) {
			if(id != BaseChart.ID_PRIMARY_Y_AXIS) {
				IAxis axis = axisSet.getYAxis(id);
				IAxisSettings axisSettings = yAxisSettingsMap.get(id);
				if(axis != null && axisSettings instanceof ISecondaryAxisSettings) {
					IAxisScaleConverter axisScaleConverter = ((ISecondaryAxisSettings)axisSettings).getAxisScaleConverter();
					axisScaleConverter.setChartDataCoordinates(this);
					double start = axisScaleConverter.convertToSecondaryUnit(range.lower);
					double end = axisScaleConverter.convertToSecondaryUnit(range.upper);
					Range adjustedRange = new Range(start, end);
					axis.setRange(adjustedRange);
				}
			}
		}
	}

	/*
	 * Min/max values will be set dynamically via Math.min and Math.max.
	 * Using the default double value 0 could lead to errors when using
	 * Math.min(...), hence initialize the values with the lowest/highest value.
	 */
	private void resetCoordinates() {

		length = 0;
		minX = Double.MAX_VALUE;
		maxX = Double.MIN_VALUE;
		minY = Double.MAX_VALUE;
		maxY = Double.MIN_VALUE;
	}

	private void calculateCoordinates(ISeries series) {

		double[] xSeries = series.getXSeries();
		double[] ySeries = series.getYSeries();
		//
		double seriesMinX = Arrays.stream(xSeries).min().getAsDouble();
		double seriesMaxX = Arrays.stream(xSeries).max().getAsDouble();
		double seriesMinY = Arrays.stream(ySeries).min().getAsDouble();
		double seriesMaxY = Arrays.stream(ySeries).max().getAsDouble();
		//
		length = Math.max(length, xSeries.length);
		minX = Math.min(minX, seriesMinX);
		maxX = Math.max(maxX, seriesMaxX);
		minY = Math.min(minY, seriesMinY);
		maxY = Math.max(maxY, seriesMaxY);
	}
}
