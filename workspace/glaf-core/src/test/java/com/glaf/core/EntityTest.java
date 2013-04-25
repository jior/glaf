package com.glaf.core;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.glaf.core.base.TableModel;
import com.glaf.core.domain.EntityDefinition;
import com.glaf.core.service.EntityDefinitionService;
import com.glaf.core.util.FileUtils;
import com.glaf.core.xml.XmlMappingReader;
import com.glaf.test.AbstractTest;

public class EntityTest extends AbstractTest {

	protected EntityDefinitionService entityDefinitionService;

	@Test
	public void testImportMapping() throws IOException {
		entityDefinitionService = super.getBean("entityDefinitionService");
		File dir = new File("../glaf-generator/mapping");
		File contents[] = dir.listFiles();
		if (contents != null) {
			XmlMappingReader reader = new XmlMappingReader();
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()
						&& contents[i].getAbsolutePath().endsWith(
								".mapping.xml")) {
					byte[] bytes = FileUtils
							.getBytes(new java.io.FileInputStream(contents[i]));
					TableModel tableModel = reader
							.read(new java.io.ByteArrayInputStream(bytes));
					EntityDefinition entity = new EntityDefinition();
					entity.setAggregationKeys(tableModel.getAggregationKey());
					entity.setCreateBy("system");
					entity.setFileContent(new String(bytes));
					entity.setTablename(tableModel.getTableName());
					entity.setName(tableModel.getEntityName());
					entity.setParseType(tableModel.getParseType());
					entity.setStartRow(tableModel.getStartRow());
					entity.setStopWord(tableModel.getStopWord());
					entity.setTitle(tableModel.getTitle());
					entity.setType("mapping");
					entity.setId(entity.getTablename());
					if (entity.getType() != null) {
						entity.setId(entity.getTablename() + "_"
								+ entity.getType());
					}
					entity.setData(bytes);
					entityDefinitionService.save(entity);
				}
			}
		}
	}

	@Test
	public void testParseMapping() throws IOException {
		entityDefinitionService = super.getBean("entityDefinitionService");
		File dir = new File("../glaf-bi/report/mapping");
		File contents[] = dir.listFiles();
		if (contents != null) {
			XmlMappingReader reader = new XmlMappingReader();
			for (int i = 0; i < contents.length; i++) {
				if (contents[i].isFile()
						&& contents[i].getAbsolutePath().endsWith(
								".mapping.xml")) {
					byte[] bytes = FileUtils
							.getBytes(new java.io.FileInputStream(contents[i]));
					TableModel tableModel = reader
							.read(new java.io.ByteArrayInputStream(bytes));
					EntityDefinition entity = new EntityDefinition();
					entity.setAggregationKeys(tableModel.getAggregationKey());
					entity.setCreateBy("system");
					entity.setFileContent(new String(bytes));
					entity.setTablename(tableModel.getTableName());
					entity.setName(tableModel.getEntityName());
					entity.setParseType(tableModel.getParseType());
					entity.setStartRow(tableModel.getStartRow());
					entity.setStopWord(tableModel.getStopWord());
					entity.setTitle(tableModel.getTitle());
					entity.setType("parse");
					entity.setId(entity.getTablename());
					if (entity.getType() != null) {
						entity.setId(entity.getTablename() + "_"
								+ entity.getType());
					}
					entity.setData(bytes);
					entityDefinitionService.save(entity);
				}
			}
		}
	}

}
