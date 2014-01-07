package com.glaf.cluster.catalina.session;

import java.io.File;
import java.io.IOException;

import javax.management.JMException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.zookeeper.jmx.ManagedUtil;
import org.apache.zookeeper.server.ServerCnxnFactory;
import org.apache.zookeeper.server.ZKDatabase;
import org.apache.zookeeper.server.DatadirCleanupManager;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.persistence.FileTxnSnapLog;
import org.apache.zookeeper.server.quorum.*;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig.ConfigException;

/**
 * 
 * <h2>Configuration file</h2>
 * 
 * When the main() method of this class is used to start the program, the first
 * argument is used as a path to the config file, which will be used to obtain
 * configuration information. This file is a Properties file, so keys and values
 * are separated by equals (=) and the key/value pairs are separated by new
 * lines. The following is a general summary of keys used in the configuration
 * file. For full details on this see the documentation in docs/index.html
 * <ol>
 * <li>dataDir - The directory where the ZooKeeper data is stored.</li>
 * <li>dataLogDir - The directory where the ZooKeeper transaction log is stored.
 * </li>
 * <li>clientPort - The port used to communicate with clients.</li>
 * <li>tickTime - The duration of a tick in milliseconds. This is the basic unit
 * of time in ZooKeeper.</li>
 * <li>initLimit - The maximum number of ticks that a follower will wait to
 * initially synchronize with a leader.</li>
 * <li>syncLimit - The maximum number of ticks that a follower will wait for a
 * message (including heartbeats) from the leader.</li>
 * <li>server.<i>id</i> - This is the host:port[:port] that the server with the
 * given id will use for the quorum protocol.</li>
 * </ol>
 * In addition to the config file. There is a file in the data directory called
 * "myid" that contains the server id as an ASCII decimal value.
 * 
 */
public class QuorumPeerMain {
	private static final Logger LOG = LoggerFactory
			.getLogger(QuorumPeerMain.class);

	private static final String USAGE = "Usage: QuorumPeerMain configfile";

	protected QuorumPeer quorumPeer;

	/**
	 * To start the replicated server specify the configuration file name on the
	 * command line.
	 * 
	 * @param args
	 *            path to the configfile
	 */
	public static void main(String[] args) {
		QuorumPeerMain main = new QuorumPeerMain();
		try {
			if (args.length == 0) {
				args = new String[1];
				if (System.getProperty("zk.cfg.path") != null) {
					args[0] = System.getProperty("zk.cfg.path");
				} else {
					args[0] = "../conf/zoo.cfg";
				}
			}
			LOG.error("use config:" + args[0]);
			main.initializeAndRun(args);
		} catch (IllegalArgumentException e) {
			LOG.error("Invalid arguments, exiting abnormally", e);
			LOG.info(USAGE);
			System.err.println(USAGE);
			System.exit(2);
		} catch (ConfigException e) {
			LOG.error("Invalid config, exiting abnormally", e);
			System.err.println("Invalid config, exiting abnormally");
			System.exit(2);
		} catch (Exception e) {
			LOG.error("Unexpected exception, exiting abnormally", e);
			System.exit(1);
		}
		LOG.info("Exiting normally");
		System.exit(0);
	}

	protected void initializeAndRun(String[] args) throws ConfigException,
			IOException {
		QuorumPeerConfig config = new QuorumPeerConfig();
		if (args.length == 1) {
			config.parse(args[0]);
		}

		// Start and schedule the the purge task
		DatadirCleanupManager purgeMgr = new DatadirCleanupManager(
				config.getDataDir(), config.getDataLogDir(),
				config.getSnapRetainCount(), config.getPurgeInterval());
		purgeMgr.start();

		if (args.length == 1 && config.getServers().size() > 0) {
			runFromConfig(config);
		} else {
			LOG.warn("Either no config or no quorum defined in config, running "
					+ " in standalone mode");
			// there is only server in the quorum -- run as standalone
			ZooKeeperServerMain.main(args);
		}
	}

	public void runFromConfig(QuorumPeerConfig config) throws IOException {
		try {
			ManagedUtil.registerLog4jMBeans();
		} catch (JMException e) {
			LOG.warn("Unable to register log4j JMX control", e);
		}

		LOG.info("Starting quorum peer");
		try {
			ServerCnxnFactory cnxnFactory = ServerCnxnFactory.createFactory();
			cnxnFactory.configure(config.getClientPortAddress(),
					config.getMaxClientCnxns());

			quorumPeer = new QuorumPeer();
			quorumPeer.setClientPortAddress(config.getClientPortAddress());
			quorumPeer.setTxnFactory(new FileTxnSnapLog(new File(config
					.getDataLogDir()), new File(config.getDataDir())));
			quorumPeer.setQuorumPeers(config.getServers());
			quorumPeer.setElectionType(config.getElectionAlg());
			quorumPeer.setMyid(config.getServerId());
			quorumPeer.setTickTime(config.getTickTime());
			quorumPeer.setMinSessionTimeout(config.getMinSessionTimeout());
			quorumPeer.setMaxSessionTimeout(config.getMaxSessionTimeout());
			quorumPeer.setInitLimit(config.getInitLimit());
			quorumPeer.setSyncLimit(config.getSyncLimit());
			quorumPeer.setQuorumVerifier(config.getQuorumVerifier());
			quorumPeer.setCnxnFactory(cnxnFactory);
			quorumPeer
					.setZKDatabase(new ZKDatabase(quorumPeer.getTxnFactory()));
			quorumPeer.setLearnerType(config.getPeerType());

			quorumPeer.start();
			quorumPeer.join();
		} catch (InterruptedException e) {
			// warn, but generally this is ok
			LOG.warn("Quorum Peer interrupted", e);
		}
	}

	public static void close(String[] args) {
		System.out.println("stop");
		LOG.warn("stop");
		System.exit(3);
	}
}
