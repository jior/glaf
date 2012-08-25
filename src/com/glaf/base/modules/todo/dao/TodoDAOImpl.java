package com.glaf.base.modules.todo.dao;

import java.sql.*;
import java.util.*;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.glaf.base.modules.todo.model.UserEntity;

public class TodoDAOImpl extends HibernateDaoSupport implements TodoDAO {

	public TodoDAOImpl() {

	}

	public static void fillStatement(PreparedStatement stmt, List values)
			throws SQLException {
		if (values == null || values.size() == 0) {
			return;
		}
		for (int i = 0; i < values.size(); i++) {
			Object obj = values.get(i);
			if (obj != null) {
				if (obj instanceof java.sql.Date) {
					java.sql.Date sqlDate = (java.sql.Date) obj;
					stmt.setDate(i + 1, sqlDate);
				} else if (obj instanceof java.sql.Time) {
					java.sql.Time sqlTime = (java.sql.Time) obj;
					stmt.setTime(i + 1, sqlTime);
				} else if (obj instanceof java.sql.Timestamp) {
					Timestamp datetime = (Timestamp) obj;
					stmt.setTimestamp(i + 1, datetime);
				} else {
					stmt.setObject(i + 1, obj);
				}
			} else {
				stmt.setString(i + 1, null);
			}
		}
	}

	public void executeSQL(final String sql, final List values) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(org.hibernate.Session session)
					throws org.hibernate.HibernateException, SQLException {
				Connection con = session.connection();
				PreparedStatement psmt = null;
				try {
					psmt = con.prepareStatement(sql);
					fillStatement(psmt, values);
					psmt.executeUpdate();
					/**
					 * 若不加close()，会导致一些非JVM分配的内存 永远无法回收（因为不归Java的垃圾回收机制管）
					 */
					psmt.close();// 【修改后添加的】
					psmt = null;
					return null;
				} catch (SQLException ex) {
					throw ex;
				} finally {
					if (psmt != null) {
						try {
							psmt.close();
							psmt = null;
						} catch (SQLException ex) {
						}
					}
				}
			}
		});
	}

	public void saveAll(final List rows) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				if (rows != null && rows.size() > 0) {
					Iterator iter = rows.iterator();
					while (iter.hasNext()) {
						Object obj = iter.next();
						session.save(obj);
					}
				}
				return null;
			}
		});
	}

	public List getUserEntityList(final String actorId) {
		return (List) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List rows = new ArrayList();
				StringBuffer sb = new StringBuffer();
				sb.append(
						" SELECT d.account actorId, b.deptId deptId, b.sysRoleId roleId ")
						.append(" FROM sys_user_role a INNER JOIN ")
						.append(" sys_dept_role b ON a.roleId = b.id INNER JOIN ")
						.append(" sys_role c ON b.sysRoleId = c.id INNER JOIN ")
						.append(" sys_user d ON a.userId = d.id ")
						.append(" WHERE (d.account = ? ) ");
				Connection con = null;
				PreparedStatement psmt = null;
				ResultSet rs = null;
				try {
					con = session.connection();
					psmt = con.prepareStatement(sb.toString());
					psmt.setString(1, actorId);
					rs = psmt.executeQuery();
					while (rs.next()) {
						UserEntity entity = new UserEntity();
						entity.setActorId(rs.getString(1));
						entity.setDeptId(rs.getLong(2));
						entity.setRoleId(rs.getLong(3));
						rows.add(entity);
					}
					psmt.close();
					rs.close();
					rs = null;
					psmt = null;
				} catch (SQLException ex) {
					throw ex;
				} finally {
					if (psmt != null) {
						try {
							psmt.close();
							psmt = null;
						} catch (SQLException ex) {
						}
					}
					if (rs != null) {
						try {
							rs.close();
							rs = null;
						} catch (SQLException ex) {
						}
					}
				}

				return rows;
			}
		});
	}

}
