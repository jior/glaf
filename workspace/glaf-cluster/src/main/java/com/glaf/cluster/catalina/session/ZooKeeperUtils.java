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

	/** 日志 */
	private static Log log = LogFactory.getLog(ZooKeeperUtils.class);

	private static ExecutorService pool = Executors.newCachedThreadPool();

	private static final String GROUP_NAME = "/SESSIONS";

	private static String hosts;

	/**
	 * 
	 * 创建指定Session ID的节点(异步操作)
	 * 
	 * @param sid
	 * 
	 * @param waitFor是否等待执行结果
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
			// 如果需要等待执行结果
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
	 * 删除指定Session ID的节点(异步操作)
	 * 
	 * @param sid
	 * 
	 * @param waitFor是否等待执行结果
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

			// 如果需要等待执行结果
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
	 * 删除指定Session ID的节点(异步操作)
	 * 
	 * @param sid
	 * 
	 * @param waitFor是否等待执行结果
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

			// 如果需要等待执行结果
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
	 * 关闭一个会话
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
	 * 连接服务器
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
	 * 创建一个组节点
	 */
	public static void createGroupNode() {
		ZooKeeper zk = connect();
		if (zk != null) {
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(GROUP_NAME, false);
				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					String path = zk.create(GROUP_NAME, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建节点完成:[" + path + "]");
				} else {
					log.debug("组节点已存在，无需创建[" + GROUP_NAME + "]");
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
	 * 创建指定Session ID的节点
	 * 
	 * @return
	 */
	public static String createRootNode() {
		ZooKeeper zk = connect(); // 连接服务期
		if (zk != null) {
			String path = GROUP_NAME;
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					String createPath = zk.create(path, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建Session节点完成:[" + createPath + "]");
					// 写入节点数据
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
	 * 创建指定Session ID的节点
	 * 
	 * @param metadata
	 *            Session 数据
	 * 
	 * @return
	 */
	public static String createSessionNode(SessionMetaData metadata) {
		if (metadata == null) {
			return null;
		}
		ZooKeeper zk = connect(); // 连接服务期
		if (zk != null) {
			String path = GROUP_NAME + "/" + metadata.getId();
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// stat为null表示无此节点，需要创建
				if (stat == null) {
					// 创建组件点
					String createPath = zk.create(path, null,
							Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
					log.debug("创建Session节点完成:[" + createPath + "]");
					// 写入节点数据
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
	 * 删除指定Session ID的节点
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @return
	 */
	public static boolean deleteSessionNode(String sid) {
		ZooKeeper zk = connect(); // 连接服务期
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// 如果节点存在则删除之
				if (stat != null) {
					// 先删除子节点
					List<String> nodes = zk.getChildren(path, false);
					if (nodes != null) {
						for (String node : nodes) {
							zk.delete(path + "/" + node, -1);
						}
					}

					// 删除父节点
					zk.delete(path, -1);
					log.debug("删除Session节点完成:[" + path + "]");
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
	 * 销毁
	 */
	public static void destroy() {
		if (pool != null) {
			// 关闭
			pool.shutdown();
		}
	}

	/**
	 * 
	 * 返回指定Session ID的节点下数据
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            数据节点的名称
	 * 
	 * @return
	 */
	public static byte[] getBytesData(String sid, String name) {
		ZooKeeper zk = connect(); // 连接服务器

		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// 查找数据节点是否存在
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);

					if (stat != null) {
						// 获取节点数据
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
	 * 返回指定Session ID的节点下数据
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            数据节点的名称
	 * 
	 * @return
	 */
	public static Object getSessionData(String sid, String name) {

		ZooKeeper zk = connect(); // 连接服务器

		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// 查找数据节点是否存在
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					Object value = null;
					if (stat != null) {
						// 获取节点数据
						byte[] data = zk.getData(dataPath, false, null);
						if (data != null) {
							// 反序列化
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
	 * 返回ZooKeeper服务器上的Session节点的所有数据，并装载为Map
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
				// 获取元数据
				SessionMetaData metadata = getSessionMetaData(path, zk);
				// 如果不存在或是无效，则直接返回null
				if (metadata == null || !metadata.getValidate()) {
					return null;
				}

				// 获取所有子节点
				List<String> nodes = zk.getChildren(path, false);
				// 存放数据
				Map<String, Object> sessionMap = new java.util.HashMap<String, Object>();
				for (String node : nodes) {
					String dataPath = path + "/" + node;
					Stat stat = zk.exists(dataPath, false);
					// 节点存在
					if (stat != null) {
						// 提取数据
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
	 * 返回指定ID的Session元数据
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public static SessionMetaData getSessionMetaData(String id, ZooKeeper zk) {
		if (zk != null) {
			String path = GROUP_NAME + "/" + id;
			try {
				// 检查节点是否存在
				Stat stat = zk.exists(path, false);
				// stat为null表示无此节点
				if (stat == null) {
					return null;
				}

				// 获取节点上的数据
				byte[] data = zk.getData(path, false, null);
				if (data != null) {
					// 反序列化
					Object value = SerializationUtils.deserialize(data);
					// 转换类型
					if (value instanceof SessionMetaData) {
						SessionMetaData metadata = (SessionMetaData) value;
						// 设置当前版本号
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
	 * 初始化
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
	 * 验证指定ID的节点是否有效
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
	 * 验证指定ID的节点是否有效
	 * 
	 * @param id
	 * 
	 * @param zk
	 * 
	 * @return
	 */
	public static boolean isValid(String id, ZooKeeper zk) {
		if (zk != null) {
			// 获取元数据
			SessionMetaData metadata = getSessionMetaData(id, zk);
			// 如果不存在或是无效，则直接返回null
			if (metadata == null) {
				return false;
			}
			return metadata.getValidate();
		}
		return false;
	}

	/**
	 * 
	 * 删除指定Session ID的节点下数据
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            数据节点的名称
	 * 
	 * @return
	 */
	public static void removeSessionData(String sid, String name) {
		ZooKeeper zk = connect(); // 连接服务器
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);
				if (stat != null) {
					// 查找数据节点是否存在
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat != null) {
						// 删除节点
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
	 * 在指定Session ID的节点下添加数据节点
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            数据节点的名称
	 * 
	 * @param data
	 *            数据
	 * 
	 * @return
	 */
	public static boolean setSessionData(String sid, String name, byte[] data) {
		boolean result = false;
		ZooKeeper zk = connect(); // 连接服务器
		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);

				// 如果节点存在则删除之
				if (stat != null) {
					// 查找数据节点是否存在，不存在就创建一个
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat == null) {
						// 创建数据节点
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
						log.debug("创建数据节点完成[" + dataPath + "]");
					}

					// 在节点上设置数据，所有数据必须可序列化
					int dataNodeVer = -1;

					if (stat != null) {
						// 记录数据节点的版本
						dataNodeVer = stat.getVersion();
					}
					stat = zk.setData(dataPath, data, dataNodeVer);
					log.debug("更新数据节点数据完成[" + dataPath + "]");
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
	 * 在指定Session ID的节点下添加数据节点
	 * 
	 * @param sid
	 *            Session ID
	 * 
	 * @param name
	 *            数据节点的名称
	 * 
	 * @param value
	 *            数据
	 * 
	 * @return
	 */
	public static boolean setSessionData(String sid, String name, Object value) {
		boolean result = false;

		ZooKeeper zk = connect(); // 连接服务器

		if (zk != null) {
			String path = GROUP_NAME + "/" + sid;
			try {
				// 检查指定的Session节点是否存在
				Stat stat = zk.exists(path, false);

				// 如果节点存在则删除之
				if (stat != null) {
					// 查找数据节点是否存在，不存在就创建一个
					String dataPath = path + "/" + name;
					stat = zk.exists(dataPath, false);
					if (stat == null) {
						// 创建数据节点
						zk.create(dataPath, null, Ids.OPEN_ACL_UNSAFE,
								CreateMode.PERSISTENT);
						log.debug("创建数据节点完成[" + dataPath + "]");
					}

					// 在节点上设置数据，所有数据必须可序列化
					if (value instanceof Serializable) {

						int dataNodeVer = -1;

						if (stat != null) {
							// 记录数据节点的版本
							dataNodeVer = stat.getVersion();
						}

						byte[] data = SerializationUtils
								.serialize((Serializable) value);
						stat = zk.setData(dataPath, data, dataNodeVer);
						log.debug("更新数据节点数据完成[" + dataPath + "][" + value + "]");
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
	 * 更新Session节点的元数据
	 * 
	 * @param metadata
	 *            Session 数据
	 * 
	 * @param zk
	 */
	public static void updateSessionMetaData(SessionMetaData metadata,
			ZooKeeper zk) {
		try {
			if (metadata != null) {
				String id = metadata.getId();
				Long now = System.currentTimeMillis(); // 当前时间
				// 检查是否过期
				Long timeout = metadata.getLastAccessTime()
						+ metadata.getMaxIdle(); // 空闲时间
				// 如果空闲时间小于当前时间，则表示Session超时
				if (timeout < now) {
					metadata.setValidate(false);
					log.debug("Session节点已超时[" + id + "]");
				}

				// 设置最后一次访问时间
				metadata.setLastAccessTime(now);

				// 更新节点数据
				String path = GROUP_NAME + "/" + id;
				byte[] data = SerializationUtils.serialize(metadata);
				zk.setData(path, data, metadata.getVersion());
				log.debug("更新Session节点的元数据完成[" + path + "]");
			}

		} catch (KeeperException e) {
			log.error(e);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}

	/**
	 * 
	 * 更新Session节点的元数据
	 * 
	 * @param id
	 *            Session ID
	 * 
	 */
	public static void updateSessionMetaData(String id) {
		ZooKeeper zk = connect();
		try {
			// 获取元数据
			SessionMetaData metadata = getSessionMetaData(id, zk);
			if (metadata != null) {
				updateSessionMetaData(metadata, zk);
			}
		} finally {
			close(zk);
		}
	}

}
