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
package com.glaf.form.core.export.xls;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import com.glaf.core.base.DataModel;
import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.dataimport.MxFormDataImport;
import com.glaf.form.core.dataimport.MxFormDataImportFactory;
import com.glaf.form.core.domain.FormDefinition;
import com.glaf.form.core.graph.def.*;
import com.glaf.form.core.graph.node.*;
import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.util.FdlConverter;

public class FormExcelExporter {
	protected final static Log logger = LogFactory
			.getLog(FormExcelExporter.class);

	public HSSFWorkbook export(DataModel dataModel,
			FormContext formContext) {
		HSSFWorkbook wb = new HSSFWorkbook();
		FormDefinition formDefinition = formContext.getFormDefinition();
		if (formDefinition != null) {
			HSSFSheet sheet = wb.createSheet();
			sheet.setDefaultColumnWidth(2);
			String subject = formDefinition.getName() + "{T="
					+ formDefinition.getTitle() + "}";
			wb.setSheetName(0, subject);

			int rowNs = formDefinition.getRows();
			int colNs = formDefinition.getColumns();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < rowNs; i++) {
				HSSFRow row = sheet.createRow((short) i);
				for (int j = 0; j < colNs; j++) {
					List<FormNode> nodes = formDefinition.getNodes();
					Collections.sort(nodes);
					for (FormNode node : nodes) {
						if (node.getRowIndex() == i && node.getColIndex() == j) {
							sb.delete(0, sb.length());
							this.addMergedRegion(sheet, node, i, j);
							row.setHeight((short) Math.ceil(node.getHeight() * 14.125));
							HSSFFont font = wb.createFont();
							if (node.getFontSize() > 0) {
								font.setFontHeightInPoints((short) (node
										.getFontSize() - 5));
							} else {
								font.setFontHeightInPoints((short) 12);
							}

							if (StringUtils.isNotEmpty(node.getForeground())) {
								String c = node.getForeground();
								if (c.length() > 0 && c.charAt(0) == '#') {
									c = c.substring(1);
								}
								if (c.length() == 6) {
									String red = c.substring(0, 2);
									String green = c.substring(2, 4);
									String blue = c.substring(4, 6);

									int r = Integer.parseInt(red, 16);
									int g = Integer.parseInt(green, 16);
									int b = Integer.parseInt(blue, 16);

									Color color = new Color(r, g, b);

									logger.debug(r + ":" + g + ":" + b + "->"
											+ (color.getRGB()));

								}
							}

							if (node.isBold()) {
								font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
							}

							if (node.isItalic()) {
								font.setItalic(true);
							}

							if (StringUtils.isNotEmpty(node.getFontName())) {
								font.setFontName(node.getFontName());
							}

							HSSFCellStyle style = wb.createCellStyle();
							style.setFont(font);

							if (StringUtils.equalsIgnoreCase(
									node.getTextAlignment(), "center")) {
								style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
							} else if (StringUtils.equalsIgnoreCase(
									node.getTextAlignment(), "right")) {
								style.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
							} else {
								style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
							}

							if (StringUtils.equalsIgnoreCase(
									node.getVerticalAlignment(), "middle")) {
								style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
							} else if (StringUtils.equalsIgnoreCase(
									node.getTextAlignment(), "top")) {
								style.setVerticalAlignment(HSSFCellStyle.VERTICAL_TOP);
							} else if (StringUtils.equalsIgnoreCase(
									node.getTextAlignment(), "bottom")) {
								style.setVerticalAlignment(HSSFCellStyle.VERTICAL_BOTTOM);
							} else {
								style.setVerticalAlignment(HSSFCellStyle.VERTICAL_JUSTIFY);
							}

							if (node.getWidth() > 0) {

							}

							String text = node.getTitle();

							if (StringUtils.equals(node.getNodeType(),
									LabelNode.NODE_TYPE)) {
								if (StringUtils
										.isNotEmpty(node.getExpression())) {
									sb.append("$L{N:\"").append(node.getName())
											.append("\", T:\"")
											.append(node.getTitle())
											.append("\", EXPR:\"")
											.append(node.getExpression())
											.append("\"}");
									font.setColor(HSSFColor.BLUE.index);
								}
							} else if (StringUtils.equals(node.getNodeType(),
									DateFieldNode.NODE_TYPE)) {
								sb.append("$D{N:\"").append(node.getName())
										.append("\", T:\"")
										.append(node.getTitle()).append("\"}");
								font.setColor(HSSFColor.BLUE.index);
							} else if (StringUtils.equals(node.getNodeType(),
									NumberFieldNode.NODE_TYPE)) {
								sb.append("$N{N:\"").append(node.getName())
										.append("\", T:\"")
										.append(node.getTitle()).append("\"}");
								font.setColor(HSSFColor.BLUE.index);
							} else if (StringUtils.equals(node.getNodeType(),
									TextAreaNode.NODE_TYPE)) {
								sb.append("$A{N:\"").append(node.getName())
										.append("\", T:\"")
										.append(node.getTitle()).append("\"}");
								font.setColor(HSSFColor.BLUE.index);
							} else if (StringUtils.equals(node.getNodeType(),
									TextFieldNode.NODE_TYPE)) {
								sb.append("$T{N:\"").append(node.getName())
										.append("\", T:\"")
										.append(node.getTitle()).append("\"}");
								font.setColor(HSSFColor.BLUE.index);
							} else if (StringUtils.equals(node.getNodeType(),
									CheckboxNode.NODE_TYPE)) {
								sb.append("$C{N:\"").append(node.getName())
										.append("\", T:\"")
										.append(node.getTitle())
										.append("\", DISP:\"")
										.append(node.getDisplay())
										.append("\", VALUE:\"")
										.append(node.getInitialValue())
										.append("\"}");
								font.setColor(HSSFColor.BLUE.index);
							} else if (StringUtils.equals(node.getNodeType(),
									RadioNode.NODE_TYPE)) {
								sb.append("$R{N:\"").append(node.getName())
										.append("\", T:\"")
										.append(node.getTitle())
										.append("\", DISP:\"")
										.append(node.getDisplay())
										.append("\", VALUE:\"")
										.append(node.getInitialValue())
										.append("\"}");
								font.setColor(HSSFColor.BLUE.index);
							}

							if (sb.length() > 0) {
								text = sb.toString();
							}

							HSSFRichTextString richString = new HSSFRichTextString(
									text);
							richString.applyFont(font);
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(style);
							cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							cell.setCellValue(richString);

						}
					}
				}
			}
		}
		return wb;
	}

	protected Color parseToColor(String c) {
		Color convertedColor = Color.BLACK;
		try {
			if (StringUtils.isNotEmpty(c)) {
				if (c.length() > 0 && c.charAt(0) == '#') {
					c = c.substring(1);
				}
				convertedColor = new Color(Integer.parseInt(c, 16));
			}
		} catch (NumberFormatException ex) {
		}
		return convertedColor;
	}

	protected void addMergedRegion(HSSFSheet sheet, FormNode node, int i, int j) {
		if (node.getRowSpan() > 1 && node.getRowSpan() > 1) {
			CellRangeAddress region = new CellRangeAddress(i, i
					+ node.getRowSpan() - 1, j, j + node.getColSpan() - 1);
			sheet.addMergedRegion(region);
			logger.debug("XY->" + node.getName() + " " + node.getTitle() + ":["
					+ i + "," + (i + node.getRowSpan() - 1) + "," + j + ","
					+ (j + node.getColSpan()) + "]");
		} else if (node.getRowSpan() > 1) {
			CellRangeAddress region = new CellRangeAddress(i, i
					+ node.getRowSpan() - 1, j, j);
			sheet.addMergedRegion(region);
			logger.debug("Y->" + node.getName() + " " + node.getTitle() + ":["
					+ (i + node.getRowSpan() - 1) + "," + i + "," + j + "," + j
					+ "]");
		} else if (node.getColSpan() > 1) {
			CellRangeAddress region = new CellRangeAddress(i, i, j, j
					+ node.getColSpan() - 1);
			sheet.addMergedRegion(region);
			logger.debug("Y->" + node.getName() + " " + node.getTitle() + ":["
					+ i + "," + i + "," + j + "," + (j + node.getColSpan() - 1)
					+ "]");
		}
	}

	public static void main(String[] args) throws Exception {
		String str = args[0];
		String type = str.substring(str.lastIndexOf(".") + 1, str.length());
		System.out.println(type);
		MxFormDataImport di = MxFormDataImportFactory.getDataImport(type);
		FormDefinitionType formDefinitionType = di
				.read(new java.io.FileInputStream(args[0]));

		FormDefinition formDefinition = FdlConverter
				.toFormDefinition(formDefinitionType);
		FormContext formContext = new FormContext();
		formContext.setContextPath("./server/webapps/jbpm");
		formContext.setFormDefinition(formDefinition);

		FormExcelExporter exporter = new FormExcelExporter();
		HSSFWorkbook wb = exporter.export(null, formContext);
		java.io.OutputStream os = new FileOutputStream(formDefinition.getName()
				+ "_1.xls");
		wb.write(os);
		os.close();
	}

}