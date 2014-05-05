/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.chart.gen;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.CategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRendererState;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

public class ExtendedStackedBarRenderer extends StackedBarRenderer {

	private static final long serialVersionUID = 1L;
	private boolean showPositiveTotal = true;
	private boolean showNegativeTotal = true;
	private Font totalLabelFont = new Font("宋体", 1, 16);
	private NumberFormat totalFormatter = NumberFormat.getInstance();

	public NumberFormat getTotalFormatter() {
		return this.totalFormatter;
	}

	public void setTotalFormatter(NumberFormat paramNumberFormat) {
		if (paramNumberFormat == null)
			throw new IllegalArgumentException("Null format not permitted.");
		this.totalFormatter = paramNumberFormat;
	}

	public void drawItem(Graphics2D paramGraphics2D,
			CategoryItemRendererState paramCategoryItemRendererState,
			Rectangle2D paramRectangle2D, CategoryPlot paramCategoryPlot,
			CategoryAxis paramCategoryAxis, ValueAxis paramValueAxis,
			CategoryDataset paramCategoryDataset, int paramInt1, int paramInt2,
			int paramInt3) {
		Number localNumber1 = paramCategoryDataset.getValue(paramInt1,
				paramInt2);
		if (localNumber1 == null)
			return;
		double d1 = localNumber1.doubleValue();
		PlotOrientation localPlotOrientation = paramCategoryPlot
				.getOrientation();
		double d2 = paramCategoryAxis.getCategoryMiddle(paramInt2,
				getColumnCount(), paramRectangle2D,
				paramCategoryPlot.getDomainAxisEdge())
				- paramCategoryItemRendererState.getBarWidth() / 2.0D;
		double d3 = 0.0D;
		double d4 = 0.0D;
		double d6;
		for (int i = 0; i < paramInt1; i++) {
			Number localNumber2 = paramCategoryDataset.getValue(i, paramInt2);
			if (localNumber2 != null) {
				d6 = localNumber2.doubleValue();
				if (d6 > 0.0D)
					d3 += d6;
				else
					d4 += d6;
			}
		}
		RectangleEdge localRectangleEdge = paramCategoryPlot.getRangeAxisEdge();
		double d5;
		if (d1 > 0.0D) {
			d5 = paramValueAxis.valueToJava2D(d3, paramRectangle2D,
					localRectangleEdge);
			d6 = paramValueAxis.valueToJava2D(d3 + d1, paramRectangle2D,
					localRectangleEdge);
		} else {
			d5 = paramValueAxis.valueToJava2D(d4, paramRectangle2D,
					localRectangleEdge);
			d6 = paramValueAxis.valueToJava2D(d4 + d1, paramRectangle2D,
					localRectangleEdge);
		}
		double d7 = Math.min(d5, d6);
		double d8 = Math.max(Math.abs(d6 - d5), getMinimumBarLength());
		Rectangle2D.Double localDouble = null;
		if (localPlotOrientation == PlotOrientation.HORIZONTAL)
			localDouble = new Rectangle2D.Double(d7, d2, d8,
					paramCategoryItemRendererState.getBarWidth());
		else
			localDouble = new Rectangle2D.Double(d2, d7,
					paramCategoryItemRendererState.getBarWidth(), d8);
		Paint localPaint = getItemPaint(paramInt1, paramInt2);
		paramGraphics2D.setPaint(localPaint);
		paramGraphics2D.fill(localDouble);
		if ((isDrawBarOutline())
				&& (paramCategoryItemRendererState.getBarWidth() > 3.0D)) {
			paramGraphics2D.setStroke(getItemStroke(paramInt1, paramInt2));
			paramGraphics2D.setPaint(getItemOutlinePaint(paramInt1, paramInt2));
			paramGraphics2D.draw(localDouble);
		}
		CategoryItemLabelGenerator localCategoryItemLabelGenerator = getItemLabelGenerator(
				paramInt1, paramInt2);
		if ((localCategoryItemLabelGenerator != null)
				&& (isItemLabelVisible(paramInt1, paramInt2)))
			drawItemLabel(paramGraphics2D, paramCategoryDataset, paramInt1,
					paramInt2, paramCategoryPlot,
					localCategoryItemLabelGenerator, localDouble, d1 < 0.0D);
		double d9;
		float f1;
		float f2;
		Object localObject;
		if (d1 > 0.0D) {
			if ((this.showPositiveTotal)
					&& (isLastPositiveItem(paramCategoryDataset, paramInt1,
							paramInt2))) {
				paramGraphics2D.setPaint(Color.black);
				paramGraphics2D.setFont(this.totalLabelFont);
				d9 = calculateSumOfPositiveValuesForCategory(
						paramCategoryDataset, paramInt2);
				f1 = (float) localDouble.getCenterX();
				f2 = (float) localDouble.getMinY() - 4.0F;
				localObject = TextAnchor.BOTTOM_CENTER;
				if (localPlotOrientation == PlotOrientation.HORIZONTAL) {
					f1 = (float) localDouble.getMaxX() + 4.0F;
					f2 = (float) localDouble.getCenterY();
					localObject = TextAnchor.CENTER_LEFT;
				}
				TextUtilities.drawRotatedString(this.totalFormatter.format(d9),
						paramGraphics2D, f1, f2, (TextAnchor) localObject,
						0.0D, TextAnchor.CENTER);
			}
		} else if ((this.showNegativeTotal)
				&& (isLastNegativeItem(paramCategoryDataset, paramInt1,
						paramInt2))) {
			paramGraphics2D.setPaint(Color.black);
			paramGraphics2D.setFont(this.totalLabelFont);
			d9 = calculateSumOfNegativeValuesForCategory(paramCategoryDataset,
					paramInt2);
			f1 = (float) localDouble.getCenterX();
			f2 = (float) localDouble.getMaxY() + 4.0F;
			localObject = TextAnchor.TOP_CENTER;
			if (localPlotOrientation == PlotOrientation.HORIZONTAL) {
				f1 = (float) localDouble.getMinX() - 4.0F;
				f2 = (float) localDouble.getCenterY();
				localObject = TextAnchor.CENTER_RIGHT;
			}
			TextUtilities.drawRotatedString(this.totalFormatter.format(d9),
					paramGraphics2D, f1, f2, (TextAnchor) localObject, 0.0D,
					TextAnchor.CENTER);
		}
		if (paramCategoryItemRendererState.getInfo() != null) {
			EntityCollection localEntityCollection = paramCategoryItemRendererState
					.getEntityCollection();
			if (localEntityCollection != null) {
				String str1 = null;
				CategoryToolTipGenerator localCategoryToolTipGenerator = getToolTipGenerator(
						paramInt1, paramInt2);
				if (localCategoryToolTipGenerator != null)
					str1 = localCategoryToolTipGenerator.generateToolTip(
							paramCategoryDataset, paramInt1, paramInt2);
				String str2 = null;
				if (getItemURLGenerator(paramInt1, paramInt2) != null)
					str2 = getItemURLGenerator(paramInt1, paramInt2)
							.generateURL(paramCategoryDataset, paramInt1,
									paramInt2);
				localObject = new CategoryItemEntity(localDouble, str1, str2,
						paramCategoryDataset,
						paramCategoryDataset.getRowKey(paramInt1),
						paramCategoryDataset.getColumnKey(paramInt2));
				localEntityCollection.add((ChartEntity) localObject);
			}
		}
	}

	private boolean isLastPositiveItem(CategoryDataset paramCategoryDataset,
			int paramInt1, int paramInt2) {
		boolean bool = true;
		Number localNumber = paramCategoryDataset
				.getValue(paramInt1, paramInt2);
		if (localNumber == null)
			return false;
		for (int i = paramInt1 + 1; i < paramCategoryDataset.getRowCount(); i++) {
			localNumber = paramCategoryDataset.getValue(i, paramInt2);
			if (localNumber != null)
				bool = (bool) && (localNumber.doubleValue() <= 0.0D);
		}
		return bool;
	}

	private boolean isLastNegativeItem(CategoryDataset paramCategoryDataset,
			int paramInt1, int paramInt2) {
		boolean bool = true;
		Number localNumber = paramCategoryDataset
				.getValue(paramInt1, paramInt2);
		if (localNumber == null)
			return false;
		for (int i = paramInt1 + 1; i < paramCategoryDataset.getRowCount(); i++) {
			localNumber = paramCategoryDataset.getValue(i, paramInt2);
			if (localNumber != null)
				bool = (bool) && (localNumber.doubleValue() >= 0.0D);
		}
		return bool;
	}

	private double calculateSumOfPositiveValuesForCategory(
			CategoryDataset paramCategoryDataset, int paramInt) {
		double d1 = 0.0D;
		for (int i = 0; i < paramCategoryDataset.getRowCount(); i++) {
			Number localNumber = paramCategoryDataset.getValue(i, paramInt);
			if (localNumber != null) {
				double d2 = localNumber.doubleValue();
				if (d2 > 0.0D)
					d1 += d2;
			}
		}
		return d1;
	}

	private double calculateSumOfNegativeValuesForCategory(
			CategoryDataset paramCategoryDataset, int paramInt) {
		double d1 = 0.0D;
		for (int i = 0; i < paramCategoryDataset.getRowCount(); i++) {
			Number localNumber = paramCategoryDataset.getValue(i, paramInt);
			if (localNumber != null) {
				double d2 = localNumber.doubleValue();
				if (d2 < 0.0D)
					d1 += d2;
			}
		}
		return d1;
	}
}