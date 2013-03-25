package com.glaf.dts.parse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.service.ITableDataService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.xml.MetadataXmlReader;

public class POIExcelParser implements TextParser {

	public static void main(String[] args) throws Exception {
		String mappingFile = "./report/mapping/Todo.mapping.xml";
		String dataFile = "./report/data/Todo.xls";
		MetadataXmlReader reader = new MetadataXmlReader();
		TableDefinition tableDefinition = reader
				.read(new java.io.FileInputStream(mappingFile));
		if (tableDefinition != null) {
			ColumnDefinition column4 = new ColumnDefinition();
			column4.setTitle("聚合主键");
			column4.setName("aggregationKey");
			column4.setColumnName("AGGREGATIONKEY");
			column4.setJavaType("String");
			column4.setLength(500);
			tableDefinition.addColumn(column4);
			if (DBUtils.tableExists(tableDefinition.getTableName())) {
				com.glaf.core.util.DBUtils.alterTable(tableDefinition);
			} else {
				com.glaf.core.util.DBUtils.createTable(tableDefinition);
			}
		}
		XmlMappingReader xmlReader = new XmlMappingReader();
		TableModel tableModel = xmlReader.read(new java.io.FileInputStream(
				mappingFile));
		System.out.println("start row no:" + tableModel.getStartRow());
		TextParser parser = new POIExcelParser();

		List<TableModel> rows = parser.parse(tableModel,
				new java.io.FileInputStream(dataFile));

		ITableDefinitionService tableDefinitionService = ContextFactory
				.getBean("tableDefinitionService");
		tableDefinitionService.save(tableDefinition);

		ITableDataService tableDataService = ContextFactory
				.getBean("tableDataService");
		tableDataService.saveAll(tableModel.getTableName(), rows);
	}

	public List<TableModel> parse(TableModel tableModel,
			java.io.InputStream data) {
		List<TableModel> rows = new ArrayList<TableModel>();
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
				int startRow = tableModel.getStartRow();// 从1开始
				if (startRow > 0 && i < startRow - 1) {
					continue;// 跳过开始行
				}
				HSSFRow row = sheet.getRow(i);
				TableModel model = new TableModel();

				model.setIdColumn(tableModel.getIdColumn());
				model.setTableName(tableModel.getTableName());
				model.setAggregationKeys(tableModel.getAggregationKeys());

				int colCount = row.getPhysicalNumberOfCells();
				for (ColumnModel cell : tableModel.getColumns()) {
					if (cell.getPosition() > 0 && cell.getPosition() < colCount) {
						HSSFCell hssfCell = row.getCell(cell.getPosition() - 1);
						ColumnModel col = new ColumnModel();
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
								formatter = new SimpleDateFormat(
										cell.getFormat());
								try {
									Date date = formatter.parse(value);
									col.setDateValue(date);
									col.setValue(date);
								} catch (ParseException ex) {
									ex.printStackTrace();
								}
							}
						}
						if (tableModel.getIdColumn() != null) {
							if (StringUtils.equals(col.getColumnName(),
									tableModel.getIdColumn().getColumnName())) {
								ColumnModel idColumn = new ColumnModel();
								idColumn.setColumnName(col.getColumnName());
								idColumn.setValue(col.getValue());
								model.setIdColumn(idColumn);
							}
						}
						model.addColumn(col);
					}
				}
				if (tableModel.getIdColumn() != null
						&& tableModel.getIdColumn().isRequired()) {
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
