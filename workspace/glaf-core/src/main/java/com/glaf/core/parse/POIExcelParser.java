package com.glaf.core.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;

public class POIExcelParser implements Parser {

	protected final static Log logger = LogFactory.getLog(POIExcelParser.class);

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/BaseData.mapping.xml";
		String dataFile = "./report/data/BaseData.xls";
		ContextFactory.hasBean("dataSource");
		ParserFacede parser = new ParserFacede();
		List<TableModel> rows = parser.parse(mappingFile, dataFile, null, true);
		if (rows != null && !rows.isEmpty()) {
			JSONArray array = new JSONArray();
			for (TableModel model : rows) {
				JSONObject jsonObject = new JSONObject();
				List<ColumnModel> columns = model.getColumns();
				if (columns != null && !columns.isEmpty()) {
					for (ColumnModel col : columns) {
						if (col.getName() != null) {
							jsonObject.put(col.getName(), col.getValue());
						}
						if (col.getColumnName() != null) {
							jsonObject.put(col.getColumnName().toLowerCase(),
									col.getValue());
						}
					}
				}
				if (model.getIdColumn() != null) {
					ColumnModel col = model.getIdColumn();
					if (col.getName() != null) {
						jsonObject.put(col.getName(), col.getValue());
					}
					if (col.getColumnName() != null) {
						jsonObject.put(col.getColumnName().toLowerCase(),
								col.getValue());
					}
				}
				array.put(jsonObject);
			}
			System.out.println(array.toString('\n'));
		}
	}

	public List<TableModel> parse(TableModel metadata, java.io.InputStream data) {
		List<TableModel> rows = new java.util.ArrayList<TableModel>();
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(data);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		int sheetCount = wb.getNumberOfSheets();
		SimpleDateFormat formatter = null;
		for (int r = 0; r < sheetCount; r++) {
			HSSFSheet sheet = wb.getSheetAt(r);
			int rowCount = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rowCount; i++) {
				int startRow = metadata.getStartRow();// 从1开始
				if (startRow > 0 && i < startRow - 1) {
					continue;// 跳过开始行
				}
				HSSFRow row = sheet.getRow(i);
				TableModel model = new TableModel();

				model.setIdColumn(metadata.getIdColumn());
				model.setTableName(metadata.getTableName());
				model.setAggregationKeys(metadata.getAggregationKeys());

				int colCount = row.getPhysicalNumberOfCells();
				// logger.info("column count="+colCount);
				int colIndex = 0;
				for (ColumnModel cell : metadata.getColumns()) {
					if (cell.getPosition() > 0
							&& cell.getPosition() <= colCount) {
						colIndex++;
						HSSFCell hssfCell = row.getCell(cell.getPosition() - 1);
						if (hssfCell == null) {
							continue;
						}
						ColumnModel col = new ColumnModel();
						col.setName(cell.getName());
						col.setColumnName(cell.getColumnName());
						col.setValueExpression(cell.getValueExpression());
						String javaType = cell.getType();

						String value = null;

						switch (hssfCell.getCellType()) {
						case HSSFCell.CELL_TYPE_FORMULA:
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = String.valueOf(hssfCell
									.getBooleanCellValue());
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if ("Date".equals(javaType)) {
								Date date = hssfCell.getDateCellValue();
								if (date != null) {
									col.setDateValue(date);
									col.setValue(date);
								}
							} else {
								value = String.valueOf(hssfCell
										.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_STRING:
							if (hssfCell.getRichStringCellValue() != null) {
								value = hssfCell.getRichStringCellValue()
										.getString();
							} else {
								value = hssfCell.getStringCellValue();
							}
							break;
						default:
							if (StringUtils.isNotEmpty(hssfCell
									.getStringCellValue())) {
								value = hssfCell.getStringCellValue();
							}
							break;
						}

						if (StringUtils.isNotEmpty(value)) {
							value = value.trim();
							if (value.endsWith(".0")) {
								value = value.substring(0, value.length() - 2);
							}
							col.setStringValue(value);
							col.setValue(value);

							if ("Boolean".equals(javaType)) {
								col.setBooleanValue(Boolean.valueOf(value));
								col.setValue(Boolean.valueOf(value));
							} else if ("Integer".equals(javaType)) {
								if (value.indexOf(".") != -1) {
									value = value.substring(0,
											value.indexOf("."));
								}
								col.setIntValue(Integer.parseInt(value));
								col.setValue(Integer.parseInt(value));
							} else if ("Long".equals(javaType)) {
								if (value.indexOf(".") != -1) {
									value = value.substring(0,
											value.indexOf("."));
								}
								col.setLongValue(Long.parseLong(value));
								col.setValue(Long.parseLong(value));
							} else if ("Double".equals(javaType)) {
								col.setDoubleValue(Double.parseDouble(value));
								col.setValue(Double.parseDouble(value));
							} else if ("Date".equals(javaType)) {
								if (cell.getFormat() != null) {
									try {
										formatter = new SimpleDateFormat(
												cell.getFormat());
										Date date = formatter.parse(value);
										col.setDateValue(date);
										col.setValue(date);
									} catch (ParseException ex) {
										logger.debug("error date format: row("
												+ startRow + ") col("
												+ colIndex + ")");
										ex.printStackTrace();
									}
								} else {
									try {
										value = StringTools.replace(value, "/",
												"-");
										Date date = DateUtils.toDate(value);
										col.setDateValue(date);
										col.setValue(date);
									} catch (Exception ex) {
										logger.debug("error date format: row("
												+ startRow + ") col("
												+ colIndex + ")");
										ex.printStackTrace();
									}
								}
							}
						}
						if (metadata.getIdColumn() != null) {
							if (StringUtils.equals(col.getColumnName(),
									metadata.getIdColumn().getColumnName())) {
								ColumnModel idColumn = new ColumnModel();
								idColumn.setName(metadata.getIdColumn()
										.getName());
								idColumn.setType(metadata.getIdColumn()
										.getType());
								idColumn.setColumnName(metadata.getIdColumn()
										.getColumnName());
								idColumn.setValue(col.getValue());
								model.setIdColumn(idColumn);
							}
						}
						model.addColumn(col);
					}
				}
				if (metadata.getIdColumn() != null
						&& metadata.getIdColumn().isRequired()) {
					if (model.getIdColumn().getValue() != null) {
						rows.add(model);
					}
				} else {
					rows.add(model);
				}
			}
		}

		return rows;
	}

}
