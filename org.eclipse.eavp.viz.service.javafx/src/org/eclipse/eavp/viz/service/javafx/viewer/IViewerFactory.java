/*******************************************************************************
 * Copyright (c) 2015 UT-Battelle, LLC.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Tony McCrary (tmccrary@l33tlabs.com)
 *******************************************************************************/
package org.eclipse.eavp.viz.service.javafx.viewer;

import org.eclipse.eavp.viz.service.javafx.canvas.FXViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * <p>
 * Defines an abstract way to create GeometryViewer instances.
 * </p>
 * 
 * @author Tony McCrary (tmccrary@l33tlabs.com)
 *
 */
public interface IViewerFactory {

    /**
     * <p>
     * Creates a geometry viewer instance on the supplied parent composite.
     * </p>
     */
    public FXViewer createViewer(Composite parent);

}
