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
import java.io.*;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import com.glaf.core.util.*;

import com.glaf.form.core.context.FormContext;
import com.glaf.form.core.domain.FormDefinition;

import com.glaf.form.core.export.FormTemplateExporter;
import com.glaf.form.core.graph.def.*;
import com.glaf.form.core.graph.node.*;

public class FormJxlsExporter implements FormTemplateExporter {
	protected final static Log logger = LogFactory
			.getLog(FormJxlsExporter.class);

	public byte[] export(FormContext formContext) {
		byte[] bytes = null;
		Workbook wb = genTemplate(formContext);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			wb.write(baos);
			bytes = baos.toByteArray();
		} catch (IOException ex) {
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
				}
			}
		}
		return bytes;
	}

	public Workbook genTemplate(FormContext formContext) {
		Workbook wb = new HSSFWorkbook();
		FormDefinition formDefinition = formContext.getFormDefinition();
		if (formDefinition != null) {
			Sheet sheet = wb.createSheet();
			sheet.setDefaultColumnWidth(2);
			// sheet.setZoom(3,4);
			String modelName = StringTools.lower(formDefinition.getName());
			String subject = formDefinition.getName() + "{T="
					+ formDefinition.getTitle() + "}";
			wb.setSheetName(0, subject);
			DataFormat format = wb.createDataFormat();
			int rowNums = formDefinition.getRows();
			int colNums = formDefinition.getColumns();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < rowNums; i++) {
				Row row = sheet.createRow((short) i);
				for (int j = 0; j < colNums; j++) {
					List<FormNode> nodes = formDefinition.getNodes();
					Collections.sort(nodes);
					for (FormNode node : nodes) {
						if (node.getRowIndex() == i && node.getColIndex() == j) {
							sb.delete(0, sb.length());
							this.addMergedRegion(sheet, node, i, j);
							row.setHeight((short) Math.ceil(node.getHeight() * 14.125));
							Font font = wb.createFont();
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

							CellStyle style = wb.createCellStyle();
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

							Cell cell = row.createCell(j);

							if (StringUtils.equals(node.getNodeType(),
									LabelNode.NODE_TYPE)) {
								if (StringUtils
										.isNotEmpty(node.getExpression())) {
									if (node.getExpression().startsWith("${")
											&& node.getExpression().endsWith(
													"}")) {
										sb.append(node.getExpression());
									} else if (node.getExpression().startsWith(
											"#{")
											&& node.getExpression().endsWith(
													"}")) {
										String expr = node.getExpression()
												.substring(
														2,
														node.getExpression()
																.length() - 1);
										logger.debug(expr);
										sb.append("${").append(expr)
												.append('}');
									}
									font.setColor(HSSFColor.BLUE.index);
								}
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							} else if (StringUtils.equals(node.getNodeType(),
									DateFieldNode.NODE_TYPE)) {
								sb.append("${").append(modelName).append('.')
										.append(node.getName()).append('}');
								font.setColor(HSSFColor.BLUE.index);
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
								style.setDataFormat(format
										.getFormat("yyyy-MM-dd"));
							} else if (StringUtils.equals(node.getNodeType(),
									NumberFieldNode.NODE_TYPE)) {
								sb.append("${").append(modelName).append('.')
										.append(node.getName()).append('}');
								font.setColor(HSSFColor.BLUE.index);
								cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
							} else if (StringUtils.equals(node.getNodeType(),
									TextAreaNode.NODE_TYPE)) {
								sb.append("${").append(modelName).append('.')
										.append(node.getName()).append('}');
								font.setColor(HSSFColor.BLUE.index);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							} else if (StringUtils.equals(node.getNodeType(),
									TextFieldNode.NODE_TYPE)) {
								sb.append("${").append(modelName).append('.')
										.append(node.getName()).append('}');
								font.setColor(HSSFColor.BLUE.index);
							} else if (StringUtils.equals(node.getNodeType(),
									SelectNode.NODE_TYPE)) {
								sb.append("${").append(modelName).append('.')
										.append(node.getName()).append('}');
								font.setColor(HSSFColor.BLUE.index);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							} else if (StringUtils.equals(node.getNodeType(),
									CheckboxNode.NODE_TYPE)) {
								sb.append("=IF(\"").append("${")
										.append(modelName).append('.')
										.append(node.getName()).append("}\"")
										.append('=').append('"')
										.append(node.getInitialValue())
										.append('"').append(",\"")
										.append(node.getDisplay())
										.append("\", \"\")");
								font.setColor(HSSFColor.BLUE.index);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							} else if (StringUtils.equals(node.getNodeType(),
									RadioNode.NODE_TYPE)) {
								font.setColor(HSSFColor.BLUE.index);
								sb.append("=IF(\"").append("${")
										.append(modelName).append('.')
										.append(node.getName()).append("}\"")
										.append('=').append('"')
										.append(node.getInitialValue())
										.append('"').append(",\"")
										.append(node.getDisplay())
										.append("\", \"\")");
								font.setColor(HSSFColor.BLUE.index);
								cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							}

							if (sb.length() > 0) {
								text = sb.toString();
							}

							cell.setCellStyle(style);

							HSSFRichTextString richString = new HSSFRichTextString(
									text);
							richString.applyFont(font);
							cell.setCellValue(richString);

						}
					}
				}
			}

			int endCol = colNums + 2;
			int endRow = rowNums + 2;

			// System.out.println(endRow + ":"+endCol);

			wb.setPrintArea(0, // sheet index
					0, // start column
					endCol, // end column
					0, // start row
					endRow // end row
			);
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

	protected void addMergedRegion(Sheet sheet, FormNode node, int i, int j) {
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

}