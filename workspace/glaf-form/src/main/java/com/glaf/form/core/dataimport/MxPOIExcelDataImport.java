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
package com.glaf.form.core.dataimport;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.glaf.core.util.FieldType;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;

import com.glaf.form.core.model.FormDefinitionType;
import com.glaf.form.core.model.NodeType;
import com.glaf.form.core.model.ObjectFactory;

public class MxPOIExcelDataImport implements FormDataImport {
	protected final static Log logger = LogFactory
			.getLog(MxPOIExcelDataImport.class);

	public static final int COLOR_MASK = Integer.parseInt("FFFFFF", 16);

	public static void main(String[] args) throws Exception {
		MxPOIExcelDataImport imp = new MxPOIExcelDataImport();
		FormDefinitionType formDefinition = imp
				.read(new java.io.FileInputStream("./data/TripApply.xls"));
		ObjectFactory of = new ObjectFactory();
		JAXBContext jc = JAXBContext.newInstance("com.glaf.form.core.model");

		// System.out.println(jc.createBinder().getXMLNode());
		Marshaller marshaller = jc.createMarshaller();
		JAXBElement<?> jaxbElement = of.createFormDefinition(formDefinition);
		marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		File f = new File("./test.fdl.xml");
		if (!f.exists()) {
			if (!f.createNewFile()) {
				throw new RuntimeException();
			}
		}
		marshaller.marshal(jaxbElement, new FileOutputStream(f));
	}

	public Color getBackgroundColor(HSSFPalette palette, HSSFCell cell) {
		short index = cell.getCellStyle().getFillForegroundColor();
		short[] rgb = palette.getColor(index).getTriplet();
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public String getColor(HSSFPalette palette, short index) {
		if (palette.getColor(index) == null) {
			return null;
		}
		short[] rgb = palette.getColor(index).getTriplet();
		Color color = new Color(rgb[0], rgb[1], rgb[2]);
		return this.getColorHexa(color);
	}

	public String getColorHexa(Color color) {
		String hexa = Integer.toHexString(color.getRGB() & COLOR_MASK)
				.toUpperCase();
		return "#" + ("000000" + hexa).substring(hexa.length());
	}

	public Color getFrontColor(HSSFWorkbook wb, HSSFCell cell) {
		HSSFPalette palette = wb.getCustomPalette();
		HSSFFont font = wb.getFontAt(cell.getCellStyle().getFontIndex());
		short index = font.getColor();
		if (palette.getColor(index) == null) {
			return Color.BLACK;
		}
		short[] rgb = palette.getColor(index).getTriplet();
		return new Color(rgb[0], rgb[1], rgb[2]);
	}

	public Integer getHeightPix(int x) {
		return (int) Math.round(x / 14.125);
	}

	public Integer getWidthPix(int x) {
		return (int) Math.round(x / 28.44);
	}

	public FormDefinitionType read(InputStream inputStream) {
		FormDefinitionType formDefinition = new FormDefinitionType();
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		String sheetName = wb.getSheetName(0);
		String formName = sheetName;
		if (sheetName.indexOf("{") != -1 && sheetName.indexOf("}") != -1
				&& sheetName.indexOf("{") < sheetName.indexOf("}")) {
			String json = sheetName.substring(sheetName.lastIndexOf("{"),
					sheetName.lastIndexOf("}") + 1);
			json = StringTools.replaceIgnoreCase(json, "=", ":");
			formName = sheetName.substring(0, sheetName.lastIndexOf("{"));
			Map<String, Object> dataMap = JsonUtils.decode(json);
			String title = (String) dataMap.get("T");
			if (StringUtils.isNotEmpty(title)) {
				if (dataMap.get("title") == null) {
					dataMap.put("title", title);
				}
			}
			Tools.populate(formDefinition, dataMap);
		}

		formDefinition.setName(formName);

		Integer y = 0;
		int rows = sheet.getPhysicalNumberOfRows();
		formDefinition.setRows(rows);

		for (int rowIndex = 0; rowIndex < rows; rowIndex++) {
			HSSFRow row = sheet.getRow(rowIndex);
			if (row == null) {
				continue;
			}
			Integer x = 0;

			int cells = row.getLastCellNum();

			formDefinition.setColumns(cells);

			for (int colIndex = 0; colIndex < cells; colIndex++) {
				HSSFCell cell = row.getCell(colIndex);

				if (cell != null) {

					NodeType node = null;

					Map<String, Object> propertyMap = new java.util.HashMap<String, Object>();

					String cellValue = null;

					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:
						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						break;
					case HSSFCell.CELL_TYPE_STRING:
						cellValue = cell.getRichStringCellValue().getString();
						break;
					default:
						break;
					}

					if (cellValue == null) {
						cellValue = "";
					}

					node = new NodeType();

					if (cellValue.startsWith("$B{") && cellValue.endsWith("}")) {
						node.setNodeType("button");
					} else if (cellValue.startsWith("$C{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("checkbox");
					} else if (cellValue.startsWith("$R{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("radio");
					} else if (cellValue.startsWith("$S{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("select");
					} else if (cellValue.startsWith("$SX{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("select");
					} else if (cellValue.startsWith("$D{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("datefield");
					} else if (cellValue.startsWith("$N{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("numberfield");
					} else if (cellValue.startsWith("$A{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("textarea");
					} else if (cellValue.startsWith("$T{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("textfield");
					} else if (cellValue.startsWith("$H{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("hidden");
					} else if (cellValue.startsWith("$L{")
							&& cellValue.endsWith("}")) {
						node.setNodeType("label");
					} else {
						node.setNodeType("label");
						node.setTitle(cellValue);
					}

					if (cellValue.startsWith("$SX") && cellValue.endsWith("}")) {
						Map<String, Object> dataMap = JsonUtils
								.decode(cellValue.substring(3,
										cellValue.length()));
						if (dataMap != null) {
							propertyMap.putAll(dataMap);
						}
					} else if (cellValue.startsWith("$")
							&& cellValue.endsWith("}")) {
						try {
							Map<String, Object> dataMap = JsonUtils
									.decode(cellValue.substring(2,
											cellValue.length()));
							if (dataMap != null) {
								propertyMap.putAll(dataMap);
							}
						} catch (Exception ex) {
							logger.error(cellValue);
						}
					}

					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_FORMULA:
						node.setFormula(cell.getCellFormula());
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN:

						break;
					case HSSFCell.CELL_TYPE_NUMERIC:
						if (HSSFDateUtil.isCellDateFormatted(cell)) {

						} else {

						}
						break;
					case HSSFCell.CELL_TYPE_STRING:

						break;
					default:
						break;
					}

					this.setStyle(wb, cell, node);

					String expr = (String) propertyMap.get("expression");
					if (StringUtils.isEmpty(expr)) {
						if (propertyMap.get("EXPR") != null) {
							propertyMap.put("expression",
									propertyMap.get("EXPR"));
						}
					}

					String name = (String) propertyMap.get("name");
					if (StringUtils.isEmpty(name)) {
						if (propertyMap.get("N") != null) {
							propertyMap.put("name", propertyMap.get("N"));
						}
					}

					String title = (String) propertyMap.get("title");
					if (StringUtils.isEmpty(title)) {
						if (propertyMap.get("T") != null) {
							propertyMap.put("title", propertyMap.get("T"));
						}
					}

					String englishTitle = (String) propertyMap
							.get("englishTitle");
					if (StringUtils.isEmpty(englishTitle)) {
						if (propertyMap.get("EN") != null) {
							propertyMap.put("englishTitle",
									propertyMap.get("EN"));
						}
					}

					String toolTip = (String) propertyMap.get("toolTip");
					if (StringUtils.isEmpty(toolTip)) {
						if (propertyMap.get("TIP") != null) {
							propertyMap.put("toolTip", propertyMap.get("TIP"));
						}
					}

					String textAlignment = (String) propertyMap
							.get("textAlignment");
					if (StringUtils.isEmpty(textAlignment)) {
						if (propertyMap.get("HA") != null) {
							propertyMap.put("textAlignment",
									propertyMap.get("HA"));
						}
					}

					String verticalAlignment = (String) propertyMap
							.get("verticalAlignment");
					if (StringUtils.isEmpty(verticalAlignment)) {
						if (propertyMap.get("VA") != null) {
							propertyMap.put("verticalAlignment",
									propertyMap.get("VA"));
						}
					}

					String dataCode = (String) propertyMap.get("dataCode");
					if (StringUtils.isEmpty(dataCode)) {
						if (propertyMap.get("DD") != null) {
							propertyMap.put("dataCode", propertyMap.get("DD"));
							propertyMap.put("objectValue",
									propertyMap.get("DD"));
						}
					}

					String objectId = (String) propertyMap.get("objectId");
					if (StringUtils.isEmpty(objectId)) {
						if (propertyMap.get("XK") != null) {
							propertyMap.put("objectId", propertyMap.get("XK"));
						}
					}

					String objectValue = (String) propertyMap
							.get("objectValue");
					if (StringUtils.isEmpty(objectValue)) {
						if (propertyMap.get("XV") != null) {
							propertyMap.put("objectValue",
									propertyMap.get("XV"));
						}
					}

					String initialValue = (String) propertyMap
							.get("initialValue");
					if (StringUtils.isEmpty(initialValue)) {
						if (propertyMap.get("VALUE") != null) {
							propertyMap.put("initialValue",
									propertyMap.get("VALUE"));
						} else if (propertyMap.get("V") != null) {
							propertyMap.put("initialValue",
									propertyMap.get("V"));
						}
					}

					String styleClass = (String) propertyMap.get("styleClass");
					if (StringUtils.isEmpty(styleClass)) {
						if (propertyMap.get("CSS") != null) {
							propertyMap.put("styleClass",
									propertyMap.get("CSS"));
						}
					}

					String display = (String) propertyMap.get("display");
					if (StringUtils.isEmpty(display)) {
						if (propertyMap.get("DISP") != null) {
							propertyMap.put("display", propertyMap.get("DISP"));
						}
					}

					String displayType = (String) propertyMap
							.get("displayType");
					if (StringUtils.isEmpty(displayType)) {
						if (propertyMap.get("DPT") != null) {
							propertyMap.put("displayType",
									propertyMap.get("DPT"));
						}
					}

					if (StringUtils.isNumeric(displayType)) {
						propertyMap.put("displayType",
								Integer.valueOf(displayType));
					}

					String queryId = (String) propertyMap.get("queryId");
					if (StringUtils.isEmpty(queryId)) {
						if (propertyMap.get("Q") != null) {
							propertyMap.put("queryId", propertyMap.get("Q"));
						}
					}

					String renderer = (String) propertyMap.get("renderer");
					if (StringUtils.isEmpty(renderer)) {
						if (propertyMap.get("RD") != null) {
							propertyMap.put("renderer", propertyMap.get("RD"));
						}
					}

					String renderType = (String) propertyMap.get("renderType");
					if (StringUtils.isEmpty(renderType)) {
						if (propertyMap.get("RDT") != null) {
							propertyMap.put("renderType",
									propertyMap.get("RDT"));
						}
					}

					String dataField = (String) propertyMap.get("dataField");
					if (StringUtils.isEmpty(dataField)) {
						if (propertyMap.get("CTR") != null) {
							propertyMap
									.put("dataField", propertyMap.get("CTR"));
						}
					}

					String accessLevel = (String) propertyMap
							.get("accessLevel");
					if (StringUtils.isEmpty(accessLevel)) {
						if (propertyMap.get("AL") != null) {
							propertyMap.put("accessLevel",
									propertyMap.get("AL"));
						}
					}

					String dataType = (String) propertyMap.get("dataType");
					if (StringUtils.isEmpty(dataType)) {
						if (propertyMap.get("DT") != null) {
							propertyMap.put("dataType", propertyMap.get("DT"));
							dataType = (String) propertyMap.get("DT");
						}
					}

					if (StringUtils.isNotEmpty(dataType)) {
						dataType = dataType.toUpperCase();
						Integer fieldType = Integer.valueOf(FieldType
								.getFieldType(dataType));
						propertyMap.put("dataType", fieldType);
					}

					Tools.populate(node, propertyMap);

					node.setX(x);
					node.setY(y);

					node.setHeight(Integer.valueOf(getHeightPix(row.getHeight())));

					node.setWidth(Integer.valueOf(getWidthPix(sheet
							.getColumnWidth((colIndex)))));

					node.setRowIndex(row.getRowNum());
					node.setColIndex(cell.getColumnIndex());

					for (int i = 0; i < sheet.getNumMergedRegions(); i++) {
						CellRangeAddress range = sheet.getMergedRegion(i);

						if (range.getFirstRow() <= row.getRowNum()
								&& row.getRowNum() <= range.getLastRow()
								&& range.getFirstColumn() <= cell
										.getColumnIndex()
								&& cell.getColumnIndex() <= range
										.getLastColumn()) {
							if (StringUtils.isEmpty(node.getTitle())
									&& StringUtils.isEmpty(node.getName())) {

							} else {
								Integer height = 0;
								Integer width = 0;

								for (int kk = range.getFirstColumn(); kk <= range
										.getLastColumn(); kk++) {
									width = width
											+ getWidthPix(sheet
													.getColumnWidth(kk));
								}

								for (int kk = range.getFirstRow(); kk <= range
										.getLastRow(); kk++) {
									HSSFRow rowx = sheet.getRow(kk);
									height = height
											+ getHeightPix(rowx.getHeight());
								}

								node.setWidth(width);
								node.setHeight(height);
								node.setRowSpan(range.getLastRow()
										- range.getFirstRow() + 1);
								node.setColSpan(range.getLastColumn()
										- range.getFirstColumn() + 1);
							}
						}
					}
					formDefinition.getNode().add(node);
				}

				x = x + getWidthPix(sheet.getColumnWidth(colIndex));

			}

			y = y + getHeightPix(row.getHeight());

			formDefinition.setWidth(Integer.valueOf(x));

			formDefinition.setHeight(Integer.valueOf(y));

		}

		return formDefinition;
	}

	protected void setStyle(HSSFWorkbook wb, HSSFCell cell, NodeType node) {
		HSSFCellStyle style = cell.getCellStyle();
		if (style != null) {
			short alignment = style.getAlignment();
			switch (alignment) {
			case HSSFCellStyle.ALIGN_CENTER:

				break;
			case HSSFCellStyle.ALIGN_JUSTIFY:

				break;
			case HSSFCellStyle.ALIGN_LEFT:

				break;
			case HSSFCellStyle.ALIGN_RIGHT:

				break;
			default:
				break;
			}

			short verticalAlignment = style.getVerticalAlignment();
			switch (verticalAlignment) {
			case HSSFCellStyle.VERTICAL_BOTTOM:

				break;
			case HSSFCellStyle.VERTICAL_CENTER:

				break;
			case HSSFCellStyle.VERTICAL_TOP:

				break;
			default:
				break;
			}

			Color x2 = this.getFrontColor(wb, cell);
			node.setForeground(this.getColorHexa(x2));

			HSSFPalette palette = wb.getCustomPalette();
			Color x4 = this.getBackgroundColor(palette, cell);
			node.setBackground(this.getColorHexa(x4));
			HSSFFont font = wb.getFontAt(style.getFontIndex());
			if (font != null) {

			}
		}
	}

}