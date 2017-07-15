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

import org.eclipse.eavp.service.swtchart.demos.Activator;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public class LineSeriesPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public LineSeriesPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Set the basic chart settings.");
	}

	public void createFieldEditors() {

		addField(new SpacerFieldEditor(getFieldEditorParent()));
		addField(new LabelFieldEditor("Chart Settings", getFieldEditorParent()));
		addField(new SpacerFieldEditor(getFieldEditorParent()));
		//
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_ENABLE_RANGE_UI, "Enable Range UI", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_VERTICAL_SLIDER_VISIBLE, "Vertical Slider Visible (see Bug #511257)", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_HORIZONTAL_SLIDER_VISIBLE, "Horizontal Slider Visible", getFieldEditorParent()));
		addField(new StringFieldEditor(LineSeriesPreferenceConstants.P_TITLE, "Title:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_TITLE_VISIBLE, "Title Visible", getFieldEditorParent()));
		addField(new ColorFieldEditor(LineSeriesPreferenceConstants.P_TITLE_COLOR, "Title Color:", getFieldEditorParent()));
		addField(new ComboFieldEditor(LineSeriesPreferenceConstants.P_LEGEND_POSITION, "Legend Position:", PreferenceSupport.LEGEND_POSITIONS, getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_LEGEND_VISIBLE, "Legend Visible", getFieldEditorParent()));
		addField(new ComboFieldEditor(LineSeriesPreferenceConstants.P_ORIENTATION, "Orientation:", PreferenceSupport.ORIENTATIONS, getFieldEditorParent()));
		addField(new ColorFieldEditor(LineSeriesPreferenceConstants.P_BACKGROUND, "Background:", getFieldEditorParent()));
		addField(new ColorFieldEditor(LineSeriesPreferenceConstants.P_BACKGROUND_CHART, "Background Chart:", getFieldEditorParent()));
		addField(new ColorFieldEditor(LineSeriesPreferenceConstants.P_BACKGROUND_PLOT_AREA, "Background Plot Area:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_ENABLE_COMPRESS, "Enable Compress", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_USE_ZERO_X, "Use Zero X", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_USE_ZERO_Y, "Use Zero Y", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_USE_RANGE_RESTRICTION, "Use Range Restriction", getFieldEditorParent()));
		addField(new DoubleFieldEditor(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MIN_X, "Factor Extend Min X:", getFieldEditorParent()));
		addField(new DoubleFieldEditor(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MAX_X, "Factor Extend Max X:", getFieldEditorParent()));
		addField(new DoubleFieldEditor(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MIN_Y, "Factor Extend Min Y:", getFieldEditorParent()));
		addField(new DoubleFieldEditor(LineSeriesPreferenceConstants.P_FACTOR_EXTEND_MAX_X, "Factor Extend Max X:", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_SHOW_POSITION_MARKER, "Show Position Marker", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_SHOW_CENTER_MARKER, "Show Center Marker", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_SHOW_POSITION_LEGEND, "Show Position Legend", getFieldEditorParent()));
		addField(new BooleanFieldEditor(LineSeriesPreferenceConstants.P_CREATE_MENU, "Create Menu", getFieldEditorParent()));
	}

	public void init(IWorkbench workbench) {

	}
}