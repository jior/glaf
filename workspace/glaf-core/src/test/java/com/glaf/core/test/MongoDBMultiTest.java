package com.glaf.core.test;

import java.util.List;

import com.glaf.core.container.MongodbContainer;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.UUID32;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDBMultiTest {

	public static void main(String[] args) throws Exception {
		List<Integer> days = DateUtils.getYearDays(2015);
		for (Integer day : days) {
			long start = System.currentTimeMillis();
			String dbname = "gridfs_" + day;
			DB db = MongodbContainer.getInstance().getDB(dbname);
			GridFS gridFS = new GridFS(db, "FS");
			for (int j = 0; j < 10; j++) {
				GridFSInputFile inputFile = null;
				String uuid = UUID32.getUUID();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < 32; i++) {
					sb.append(UUID32.getUUID());
				}
				inputFile = gridFS.createFile(sb.toString().getBytes());
				DBObject metadata = new BasicDBObject();
				metadata.put("path", uuid);
				metadata.put("filename", uuid);
				metadata.put("size", uuid.length());
				metadata.put("lastModified", System.currentTimeMillis());
				inputFile.setMetaData(metadata);
				inputFile.setId(uuid);
				inputFile.setFilename(uuid);// 指定唯一文件名称
				inputFile.save();// 保存
			}
			long time = System.currentTimeMillis() - start;
			System.out.println("times:" + time);
		}
	}

}
