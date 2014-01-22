package com.glaf.cluster.catalina.session;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ConnectionWatcher implements Watcher {
	private static final int SESSION_TIMEOUT = 3600;
	private CountDownLatch signal = new CountDownLatch(1);
	private Log log = LogFactory.getLog(getClass());

	public ZooKeeper connection(String servers) {
		ZooKeeper zk;
		try {
			zk = new ZooKeeper(servers, SESSION_TIMEOUT, this);
			signal.await();
			return zk;
		} catch (IOException ex) {
			log.error(ex);
		} catch (InterruptedException ex) {
			log.error(ex);
		}
		return null;
	}

	public void process(WatchedEvent event) {
		KeeperState state = event.getState();
		if (state == KeeperState.SyncConnected) {
			signal.countDown();
		}
	}
}
