package com.glaf.dts.test;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.parse.ParserFacede;
import com.glaf.core.util.DateUtils;

public class ParseTest {

	@Test
	public void testImportXls() {
		String mappingFile = "./report/mapping/Plans.mapping.xml";
		String dataFile = "./report/data/importparts-3.xls";
		ContextFactory.hasBean("dataSource");
		ParserFacede parser = new ParserFacede();
		List<TableModel> rows = parser.parse(mappingFile, dataFile, "root_"
				+ DateUtils.getDateTime("yyyyMMddHHmmss", new Date()), true);
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

}
