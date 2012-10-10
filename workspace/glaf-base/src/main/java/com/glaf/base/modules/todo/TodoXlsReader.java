package com.glaf.base.modules.todo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jpage.util.Tools;

import com.glaf.base.modules.todo.model.ToDo;
import com.glaf.base.utils.ParamUtil;

public class TodoXlsReader {

	public List<ToDo> readXls(java.io.InputStream inputStream) {
		List<ToDo> todos = new ArrayList<ToDo>();
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(inputStream);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow row = sheet.getRow(1);
		Map<Integer, String> keyMap = new HashMap<Integer, String>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		int cells = row.getPhysicalNumberOfCells();
		for (int colIndex = 0; colIndex < cells; colIndex++) {
			HSSFCell cell = row.getCell(colIndex);
			keyMap.put(colIndex, cell.getStringCellValue());
		}
		Set<String> keys = new HashSet<String>();
		for (int rowIndex = 2; rowIndex < sheet.getPhysicalNumberOfRows(); rowIndex++) {
			HSSFRow rowx = sheet.getRow(rowIndex);
			if (rowx == null) {
				continue;
			}
			// System.out.println();
			dataMap.clear();
			for (int colIndex = 0; colIndex < cells; colIndex++) {
				String fieldName = keyMap.get(colIndex);
				HSSFCell cell = rowx.getCell(colIndex);
				if (cell == null) {
					continue;
				}
				Object cellValue = null;
				switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_FORMULA:
					break;
				case HSSFCell.CELL_TYPE_BOOLEAN:
					cellValue = cell.getBooleanCellValue();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					cellValue = cell.getNumericCellValue();
					break;
				case HSSFCell.CELL_TYPE_STRING:
					if (StringUtils.isNotEmpty(cell.getRichStringCellValue()
							.getString())) {
						cellValue = cell.getRichStringCellValue().getString();
					}
					break;
				default:
					if (StringUtils.isNotEmpty(cell.getStringCellValue())) {
						cellValue = cell.getStringCellValue();
					}
					break;
				}
				if (cellValue != null) {
					dataMap.put(fieldName, cellValue);
					if("id".equals(fieldName)){
						cellValue = cellValue.toString().substring(0, cellValue.toString().indexOf("."));
						dataMap.put(fieldName, cellValue);
					}
					//System.out.print("\t" + fieldName + "=" + cellValue);
				}
			}
			//System.out.println();
			//System.out.println(rowIndex+":::::"+dataMap.get("code"));
			if (dataMap.get("code") != null) {
				//System.out.println(rowIndex+"->"+dataMap);
				String id = ParamUtil.getString(dataMap, "id");
				if (!keys.contains(ParamUtil.getString(dataMap, "code"))) {
					if (id != null && StringUtils.isNotEmpty(id)) {
						ToDo model = new ToDo();
						Tools.populate(model, dataMap);
						if (ParamUtil.getInt(dataMap, "limitDay") > 0) {
							model.setLimitDay(ParamUtil.getInt(dataMap,
									"limitDay"));
						}
						todos.add(model);
						keys.add(model.getCode());
					}
				}
			}
		}

		return todos;
	}
}
