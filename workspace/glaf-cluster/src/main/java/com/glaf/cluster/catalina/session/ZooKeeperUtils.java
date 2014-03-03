package com.glaf.cluster.catalina.session;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

public class ZooKeeperUtils {

	/** ��־ */
	private static Log log = LogFactory.getLog(ZooKeeperUtils.class);

	private static ExecutorService pool = Executors.newCachedThreadPool();

	private static final String GROUP_NAME = "/SESSIONS";

	private static String hosts;

	/**
	 * 
	 * ����ָ��Session ID�Ľڵ�(�첽����)
	 * 
	 * @param sid
	 * 
	 * @param waitFor�Ƿ�ȴ�ִ�н��
	 * 
	 * @return
	 */
	public static String asynCreateSessionNode(final SessionMetaData metadata,
			boolean waitFor) {
		Callable<String> task = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return createSessionNode(metadata);
			}

		};

		try {
			Future<String> result = pool.submit(task);
			// �����Ҫ�ȴ�ִ�н��
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return result.get();
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}

		return null;
	}

	/**
	 * 
	 * ɾ��ָ��Session ID�Ľڵ�(�첽����)
	 * 
	 * @param sid
	 * 
	 * @param waitFor�Ƿ�ȴ�ִ�н��
	 * 
	 * @return
	 */
	public static boolean asynDeleteSessionNode(final String sid,
			boolean waitFor) {

		Callable<Boolean> task = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				return deleteSessionNode(sid);
			}

		};

		try {
			Future<Boolean> result = pool.submit(task);

			// �����Ҫ�ȴ�ִ�н��
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return result.get();
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}

		return false;
	}

	/**
	 * 
	 * ɾ��ָ��Session ID�Ľڵ�(�첽����)
	 * 
	 * @param sid
	 * 
	 * @param waitFor�Ƿ�ȴ�ִ�н��
	 * 
	 * @return
	 */

	public static boolean asynSetSessionData(final String sid,
			final String name, final Object value, boolean waitFor) {

		Callable<Boolean> task = new Callable<Boolean>() {

			@Override
			public Boolean call() throws Exception {
				return setSessionData(sid, name, value);
			}

		};

		try {
			Future<Boolean> result = pool.submit(task);

			// �����Ҫ�ȴ�ִ�н��
			if (waitFor) {
				while (true) {
					if (result.isDone()) {
						return result.get();
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}

		return false;
	}

	/**
	 * 
	 * �ر�һ���Ự
	 */
	public static void close(ZooKeeper zk) {
		if (zk != null) {
			try {
				zk.close();
			} catch (InterruptedException e) {
				log.error(e);
			}
		}
	}

	/**
	 * 
	 * ���ӷ�����
	 * 
	 * @return
	 */
	public static ZooKeeper connect() {
		ConnectionWatcher watcher = new ConnectionWatcher();
		ZooKeeper zk = watcher.connection(hosts);
		return zk;
	}

	/**
	 * 
	 * ����һ����ڵ�
	 */
	public static void createGroupNode() {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				// ���ڵ��Ƿ����
				Stat stat = zk.exists(GROUP_NAME, false);
				// statΪnull��ʾ�޴˽ڵ㣬��Ҫ����
				if (stat == null) {
					// ���������
					String path = zk.create(GROUP_NAME, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("�����ڵ����:[" + path + "]");
				} else {
					log.debug("��ڵ��Ѵ��ڣ����贴��[" + GROUP_NAME + "]");
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
	}

	/**
	 * 
	 * ����ָ��Session ID�Ľڵ�
	 * 
	 * @return
	 */
	public static String createRootNode() {
		ZooKeeper zk = connect(); // ���ӷ�����
		if (zk != null) {
			String path = GROUP_NAME;
			try {
				// ���ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				// statΪnull��ʾ�޴˽ڵ㣬��Ҫ����
				if (stat == null) {
					// ���������
					String createPath = zk.create(path, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("����Session�ڵ����:[" + createPath + "]");
					// д��ڵ�����
					zk.setData(path, "/".getBytes(), -1);
					return createPath;
				}
			} catch (KeeperException e) {
				e.printStackTrace();
				log.error(e);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return null;
	}

	/**
	 * 
	 * ����ָ��Session ID�Ľڵ�
	 * 
	 * @param metadata
	 *            Session ����
	 * 
	 * @return
	 */
	public static String createSessionNode(SessionMetaData metadata) {
		if (metadata == null) {
			return null;
		}
		ZooKeeper zk = connect(); // ���ӷ�����
		if (zk != null) {
			String path = GROUP_NAME + "/" + metadata.getId();
			try {
				// ���ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				// statΪnull��ʾ�޴˽ڵ㣬��Ҫ����
				if (stat == null) {
					// ���������
					String createPath = zk.create(path, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("����Session�ڵ����:[" + createPath + "]");
					// д��ڵ�����
					zk.setData(path, SerializationUtils.serialize(metadata), -1);
					return createPath;
				}
			} catch (KeeperException e) {
				e.printStackTrace();
				log.error(e);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return null;
	}

	/**
	 * 
	 * ɾ��ָ��Session ID�Ľڵ�
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @return
	 */
	public static boolean deleteSessionNode(String sid) {
		ZooKeeper zk = connect(); // ���ӷ�����
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// ���ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				// ����ڵ������ɾ��֮
				if (stat != null) {
					// ��ɾ���ӽڵ�
					List<String> nodes = zk.getChildren(path, false);
					if (nodes != null) {
						for (String node : nodes) {
							zk.delete(path + "/" + node, -1);
						}
					}

					// ɾ�����ڵ�
					zk.delete(path, -1);
					log.debug("ɾ��Session�ڵ����:[" + path + "]");
					return true;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return false;
	}

	/**
	 * 
	 * ����
	 */
	public static void destroy() {
		if (pool != null) {
			// �ر�
			pool.shutdown();
		}
	}

	/**
	 * 
	 * ����ָ��Session ID�Ľڵ�������
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            ���ݽڵ������
	 * 
	 * @return
	 */
	public static byte[] getBytesData(String sid, String name) {
		ZooKeeper zk = connect(); // ���ӷ�����

		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// ���ָ����Session�ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// �������ݽڵ��Ƿ����
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);

					if (stat != null) {
						// ��ȡ�ڵ�����
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							log.debug("get data from zookeeper...");
							return data;
						}
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return null;
	}

	/**
	 * 
	 * ����ָ��Session ID�Ľڵ�������
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            ���ݽڵ������
	 * 
	 * @return
	 */
	public static Object getSessionData(String sid, String name) {

		ZooKeeper zk = connect(); // ���ӷ�����

		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// ���ָ����Session�ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// �������ݽڵ��Ƿ����
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					Object value = null;
					if (stat != null) {
						// ��ȡ�ڵ�����
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							// �����л�
							value = SerializationUtils.deserialize(data);
						}
					}

					return value;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return null;
	}

	/**
	 * 
	 * ����ZooKeeper�������ϵ�Session�ڵ���������ݣ���װ��ΪMap
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public static Map<String, Object> getSessionMap(String id) {
		ZooKeeper zk = connect();
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// ��ȡԪ����
				SessionMetaData metadata = getSessionMetaData(path, zk);
				// ��������ڻ�����Ч����ֱ�ӷ���null
				if (metadata == null || !metadata.getValidate()) {
					return null;
				}

				// ��ȡ�����ӽڵ�
				List<String> nodes = zk.getChildren(path, false);
				// �������
				Map<String, Object> sessionMap = new java.util.HashMap<String, Object>();
				for (String node : nodes) {
					String dataPath = path + "/" + node;
					Stat stat = zk.exists(dataPath, false);
					// �ڵ����
					if (stat != null) {
						// ��ȡ����
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							sessionMap.put(node,
									SerializationUtils.deserialize(data));
						} else {
							sessionMap.put(node, null);
						}
					}
				}
				return sessionMap;
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return null;
	}

	/**
	 * 
	 * ����ָ��ID��SessionԪ����
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public static SessionMetaData getSessionMetaData(String id, ZooKeeper zk) {
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// ���ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				// statΪnull��ʾ�޴˽ڵ�
				if (stat == null) {
					return null;
				}

				// ��ȡ�ڵ��ϵ�����
				byte[] data = zk.getData(path, false, null);
				if (data != null) {
					// �����л�
					Object value = SerializationUtils.deserialize(data);
					// ת������
					if (value instanceof SessionMetaData) {
						SessionMetaData metadata = (SessionMetaData) value;
						// ���õ�ǰ�汾��
						metadata.setVersion(stat.getVersion());
						return metadata;
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			}
		}

		return null;
	}

	/**
	 * 
	 * ��ʼ��
	 */
	public static void initialize(String hosts) {
		ZooKeeperUtils.hosts = hosts;
		try {
			ZooKeeperUtils.createRootNode();
		} catch (Exception ex) {
			log.error(ex);
			throw new RuntimeException("Can't create root node.");
		}
	}

	/**
	 * 
	 * ��ָ֤��ID�Ľڵ��Ƿ���Ч
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public static boolean isValid(String id) {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				return isValid(id, zk);
			} finally {
				close(zk);
			}
		}
		return false;
	}

	/**
	 * 
	 * ��ָ֤��ID�Ľڵ��Ƿ���Ч
	 * 
	 * @param id
	 * 
	 * @param zk
	 * 
	 * @return
	 */
	public static boolean isValid(String id, ZooKeeper zk) {
		if (zk != null) {
			// ��ȡԪ����
			SessionMetaData metadata = getSessionMetaData(id, zk);
			// ��������ڻ�����Ч����ֱ�ӷ���null
			if (metadata == null) {
				return false;
			}
			return metadata.getValidate();
		}
		return false;
	}

	/**
	 * 
	 * ɾ��ָ��Session ID�Ľڵ�������
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            ���ݽڵ������
	 * 
	 * @return
	 */
	public static void removeSessionData(String sid, String name) {
		ZooKeeper zk = connect(); // ���ӷ�����
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// ���ָ����Session�ڵ��Ƿ����
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// �������ݽڵ��Ƿ����
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat != null) {
						// ɾ���ڵ�
						zk.delete(dataPath, -1);
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}
	}

	/**
	 * 
	 * ��ָ��Session ID�Ľڵ���������ݽڵ�
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            ���ݽڵ������
	 * 
	 * @param data
	 *            ����
	 * 
	 * @return
	 */
	public static boolean setSessionData(String sid, String name, byte[] data) {
		boolean result = false;
		ZooKeeper zk = connect(); // ���ӷ�����
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// ���ָ����Session�ڵ��Ƿ����
				Stat stat = zk.exists(path, false);

				// ����ڵ������ɾ��֮
				if (stat != null) {
					// �������ݽڵ��Ƿ���ڣ������ھʹ���һ��
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat == null) {
						// �������ݽڵ�
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
						log.debug("�������ݽڵ����[" + dataPath + "]");
					}

					// �ڽڵ����������ݣ��������ݱ�������л�
					int dataNodeVer = -1;

					if (stat != null) {
						// ��¼���ݽڵ�İ汾
						dataNodeVer = stat.getVersion();
					}
					stat = zk.setData(dataPath, data, dataNodeVer);
					log.debug("�������ݽڵ��������[" + dataPath + "]");
					result = true;
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return result;
	}

	/**
	 * 
	 * ��ָ��Session ID�Ľڵ���������ݽڵ�
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            ���ݽڵ������
	 * 
	 * @param value
	 *            ����
	 * 
	 * @return
	 */
	public static boolean setSessionData(String sid, String name, Object value) {
		boolean result = false;

		ZooKeeper zk = connect(); // ���ӷ�����

		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// ���ָ����Session�ڵ��Ƿ����
				Stat stat = zk.exists(path, false);

				// ����ڵ������ɾ��֮
				if (stat != null) {
					// �������ݽڵ��Ƿ���ڣ������ھʹ���һ��
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat == null) {
						// �������ݽڵ�
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
						log.debug("�������ݽڵ����[" + dataPath + "]");
					}

					// �ڽڵ����������ݣ��������ݱ�������л�
					if (value instanceof Serializable) {

						int dataNodeVer = -1;

						if (stat != null) {
							// ��¼���ݽڵ�İ汾
							dataNodeVer = stat.getVersion();
						}

						byte[] data = SerializationUtils
								.serialize((Serializable) value);
						stat = zk.setData(dataPath, data, dataNodeVer);
						log.debug("�������ݽڵ��������[" + dataPath + "][" + value + "]");
						result = true;
					}
				}
			} catch (KeeperException e) {
				log.error(e);
			} catch (InterruptedException e) {
				log.error(e);
			} finally {
				close(zk);
			}
		}

		return result;
	}

	/**
	 * 
	 * ����Session�ڵ��Ԫ����
	 * 
	 * @param metadata
	 *            Session ����
	 * 
	 * @param zk
	 */
	public static void updateSessionMetaData(SessionMetaData metadata,
			ZooKeeper zk) {
		try {
			if (metadata != null) {
				String id = metadata.getId();
				Long now = System.currentTimeMillis(); // ��ǰʱ��
				// ����Ƿ����
				Long timeout = metadata.getLastAccessTime()
						+ metadata.getMaxIdle(); // ����ʱ��
				// �������ʱ��С�ڵ�ǰʱ�䣬���ʾSession��ʱ
				if (timeout < now) {
					metadata.setValidate(false);
					log.debug("Session�ڵ��ѳ�ʱ[" + id + "]");
				}

				// �������һ�η���ʱ��
				metadata.setLastAccessTime(now);

				// ���½ڵ�����
				String path = GROUP_NAME + "/" + id;
				byte[] data = SerializationUtils.serialize(metadata);
				zk.setData(path, data, metadata.getVersion());
				log.debug("����Session�ڵ��Ԫ�������[" + path + "]");
			}

		} catch (KeeperException e) {
			log.error(e);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	/**
	 * 
	 * ����Session�ڵ��Ԫ����
	 * 
	 * @param id
	 *            Session ID
	 * 
	 */
	public static void updateSessionMetaData(String id) {
		ZooKeeper zk = connect();
		try {
			// ��ȡԪ����
			SessionMetaData metadata = getSessionMetaData(id, zk);
			if (metadata != null) {
				updateSessionMetaData(metadata, zk);
			}
		} finally {
			close(zk);
		}
	}

}
