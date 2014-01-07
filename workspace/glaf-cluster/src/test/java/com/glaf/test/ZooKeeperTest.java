package com.glaf.test;

import com.glaf.cluster.catalina.session.SessionMetaData;
import com.glaf.cluster.catalina.session.UUID32;
import com.glaf.cluster.catalina.session.ZooKeeperUtils;

public class ZooKeeperTest {

	public static void main(String[] args) {
		SessionMetaData metadata = new SessionMetaData();
		metadata.setId(UUID32.getUUID());
		ZooKeeperUtils.initialize("localhost:2181");
		ZooKeeperUtils.createRootNode();
		ZooKeeperUtils.createSessionNode(metadata);
	}

}
