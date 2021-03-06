/*******************************************************************************
 * Copyright (c) 2010 itemis AG (http://www.itemis.eu)
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * Contributors:
 *   Jan Koehnlein - Initial API and implementation
 *******************************************************************************/
package com.github.jknack.antlr4ide.ui.railroad.figures;

import java.util.List;

import org.eclipse.draw2d.LayoutManager;
import org.eclipse.emf.ecore.EObject;

import com.github.jknack.antlr4ide.ui.railroad.figures.layout.SequenceLayout;
import com.github.jknack.antlr4ide.ui.railroad.figures.primitives.CrossPoint;
import com.github.jknack.antlr4ide.ui.railroad.figures.primitives.PrimitiveFigureFactory;

/**
 * A sequence of segments.
 *
 * @author Jan Koehnlein - Initial contribution and API
 */
public class SequenceSegment extends AbstractSegmentFigure {

  public SequenceSegment(final EObject eObject, final List<ISegmentFigure> body,
      final PrimitiveFigureFactory primitiveFactory) {
    super(eObject);
    if (body.isEmpty()) {
      setEntry(primitiveFactory.createCrossPoint(this));
      setExit(getEntry());
    } else {
      boolean isFirst = true;
      CrossPoint currentEnd = null;
      for (ISegmentFigure child : body) {
        if (isFirst) {
          setEntry(child.getEntry());
          isFirst = false;
        } else {
          primitiveFactory.createConnection(currentEnd, child.getEntry(), this);
        }
        add(child);
        currentEnd = child.getExit();
      }
      setExit(currentEnd);
    }
  }

  @Override
  protected LayoutManager createLayoutManager() {
    return new SequenceLayout();
  }

}
