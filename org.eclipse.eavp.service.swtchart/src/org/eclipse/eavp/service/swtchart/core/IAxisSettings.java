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

import java.text.DecimalFormat;

import org.eclipse.swt.graphics.Color;
import org.swtchart.IAxis.Position;

public interface IAxisSettings {

	String getTitle();

	void setTitle(String title);

	String getDescription();

	void setDescription(String description);

	DecimalFormat getDecimalFormat();

	void setDecimalFormat(DecimalFormat decimalFormat);

	Color getColor();

	void setColor(Color color);

	boolean isVisible();

	void setVisible(boolean visible);

	Position getPosition();

	void setPosition(Position position);
}
