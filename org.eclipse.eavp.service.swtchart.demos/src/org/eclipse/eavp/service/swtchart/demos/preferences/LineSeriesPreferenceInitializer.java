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
package org.eclipse.eavp.service.swtchart.demos.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.eavp.service.swtchart.demos.Activator;
import org.eclipse.jface.preference.IPreferenceStore;

public class LineSeriesPreferenceInitializer extends AbstractPreferenceInitializer {

	public void initializeDefaultPreferences() {

		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		/*
		 * Line Series
		 */
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ENABLE_RANGE_UI, LineSeriesPreferenceConstants.DEF_ENABLE_RANGE_UI);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_VERTICAL_SLIDER_VISIBLE, LineSeriesPreferenceConstants.DEF_VERTICAL_SLIDER_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_HORIZONTAL_SLIDER_VISIBLE, LineSeriesPreferenceConstants.DEF_HORIZONTALSLIDER_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_TITLE, LineSeriesPreferenceConstants.DEF_TITLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_TITLE_VISIBLE, LineSeriesPreferenceConstants.DEF_TITLE_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_TITLE_COLOR, LineSeriesPreferenceConstants.DEF_TITLE_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_LEGEND_POSITION, LineSeriesPreferenceConstants.DEF_LEGEND_POSITION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_LEGEND_VISIBLE, LineSeriesPreferenceConstants.DEF_LEGEND_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ORIENTATION, LineSeriesPreferenceConstants.DEF_ORIENTATION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_BACKGROUND, LineSeriesPreferenceConstants.DEF_BACKGROUND);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_BACKGROUND_IN_PLOT_AREA, LineSeriesPreferenceConstants.DEF_BACKGROUND_IN_PLOT_AREA);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ENABLE_COMPRESS, LineSeriesPreferenceConstants.DEF_ENABLE_COMPRESS);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_USE_ZERO_X, LineSeriesPreferenceConstants.DEF_USE_ZERO_X);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_USE_ZERO_Y, LineSeriesPreferenceConstants.DEF_USE_ZERO_Y);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_USE_RANGE_RESTRICTION, LineSeriesPreferenceConstants.DEF_USE_RANGE_RESTRICTION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MIN_X, LineSeriesPreferenceConstants.DEF_FACTOR_EXTEND_MIN_X);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MAX_X, LineSeriesPreferenceConstants.DEF_FACTOR_EXTEND_MAX_X);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MIN_Y, LineSeriesPreferenceConstants.DEF_FACTOR_EXTEND_MIN_Y);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MAX_Y, LineSeriesPreferenceConstants.DEF_FACTOR_EXTEND_MAX_Y);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SHOW_POSITION_MARKER, LineSeriesPreferenceConstants.DEF_SHOW_POSITION_MARKER);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SHOW_CENTER_MARKER, LineSeriesPreferenceConstants.DEF_SHOW_CENTER_MARKER);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SHOW_POSITION_LEGEND, LineSeriesPreferenceConstants.DEF_SHOW_POSITION_LEGEND);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_CREATE_MENU, LineSeriesPreferenceConstants.DEF_CREATE_MENU);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_TITLE, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_TITLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_DESCRIPTION, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_DESCRIPTION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_DECIMAL_FORMAT_PATTERN, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_DECIMAL_FORMAT_PATTERN);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_DECIMAL_FORMAT_LOCALE, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_DECIMAL_FORMAT_LOCALE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_COLOR, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_POSITION, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_POSITION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_VISIBLE, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_X_AXIS_GRID_LINE_STYLE, LineSeriesPreferenceConstants.DEF_PRIMARY_X_AXIS_GRID_LINE_STYLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_TITLE, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_TITLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_DESCRIPTION, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_DESCRIPTION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_DECIMAL_FORMAT_PATTERN, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_DECIMAL_FORMAT_PATTERN);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_DECIMAL_FORMAT_LOCALE, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_DECIMAL_FORMAT_LOCALE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_COLOR, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_POSITION, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_POSITION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_VISIBLE, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_PRIMARY_Y_AXIS_GRID_LINE_STYLE, LineSeriesPreferenceConstants.DEF_PRIMARY_Y_AXIS_GRID_LINE_STYLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_TITLE, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_TITLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_DESCRIPTION, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_DESCRIPTION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_DECIMAL_FORMAT_PATTERN, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_DECIMAL_FORMAT_PATTERN);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_DECIMAL_FORMAT_LOCALE, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_DECIMAL_FORMAT_LOCALE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_COLOR, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_POSITION, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_POSITION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_VISIBLE, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_X_AXIS_GRID_LINE_STYLE, LineSeriesPreferenceConstants.DEF_SECONDARY_X_AXIS_GRID_LINE_STYLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_TITLE, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_TITLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_DESCRIPTION, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_DESCRIPTION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_DECIMAL_FORMAT_PATTERN, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_DECIMAL_FORMAT_PATTERN);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_DECIMAL_FORMAT_LOCALE, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_DECIMAL_FORMAT_LOCALE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_COLOR, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_POSITION, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_POSITION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_VISIBLE, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SECONDARY_Y_AXIS_GRID_LINE_STYLE, LineSeriesPreferenceConstants.DEF_SECONDARY_Y_AXIS_GRID_LINE_STYLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ANTIALIAS, LineSeriesPreferenceConstants.DEF_ANTIALIAS);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_DESCRIPTION, LineSeriesPreferenceConstants.DEF_DESCRIPTION);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ENABLE_AREA, LineSeriesPreferenceConstants.DEF_ENABLE_AREA);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ENABLE_STACK, LineSeriesPreferenceConstants.DEF_ENABLE_STACK);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_ENABLE_STEP, LineSeriesPreferenceConstants.DEF_ENABLE_STEP);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_LINE_COLOR, LineSeriesPreferenceConstants.DEF_LINE_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_LINE_COLOR, LineSeriesPreferenceConstants.DEF_LINE_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_LINE_STYLE, LineSeriesPreferenceConstants.DEF_LINE_STYLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_LINE_WIDTH, LineSeriesPreferenceConstants.DEF_LINE_WIDTH);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SYMBOL_COLOR, LineSeriesPreferenceConstants.DEF_SYMBOL_COLOR);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SYMBOL_SIZE, LineSeriesPreferenceConstants.DEF_SYMBOL_SIZE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_SYMBOL_TYPE, LineSeriesPreferenceConstants.DEF_SYMBOL_TYPE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_VISIBLE, LineSeriesPreferenceConstants.DEF_VISIBLE);
		preferenceStore.setDefault(LineSeriesPreferenceConstants.P_VISIBLE_IN_LEGEND, LineSeriesPreferenceConstants.DEF_VISIBLE_IN_LEGEND);
	}
}
