//================================================================================================
//项目名称 ：    基盘
//功    能 ：   访问数据库
//文件名称 ：    DbAccess.java                                   
//描    述 ：    封装访问数据库的基本方法，基盘所有访问数据库的方法都从这里获得
//================================================================================================
//修改履历                                                                
//年 月 日		区分		所 属/担 当           		内 容									标识        
//----------   	----   	------------------- ---------------                          ------        
//2009/04/28   	编写   	Intasect/邹怡文    	 新規作成                                                                            
//================================================================================================

package baseSrc.common;

import baseSrc.framework.BaseActionForm;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.hibernate.transform.Transformers;

public class DbAccess {
	
	private BaseDaoSupport hds;
	
	private static LogHelper logger = new LogHelper(DbAccess.class);

	public void setHds(BaseDaoSupport hds) {
		this.hds = hds;
	}
	
	/**
	 * 取出表中和PK关联的一条数据
	 * 
	 * @param entityClass 表的映射类名
	 * @param id 表的映射类中定义的ID（PK） 
	 * 
	 * @return 表的映射类对象
	 */
	public Object load(Class<?> entityClass,Serializable id)throws DataAccessException{
//		setLoadLog(entityClass.getName(),id,null);
		return this.hds.getHibernateTemplate().load(entityClass, id);
	}
	
	/**
	 * 锁定并取出表中和PK关联的一条数据
	 * 
	 * @param entityClass 表的映射类名
	 * @param id 表的映射类中定义的ID（PK） 
	 * @param lockMode 锁定的类型
	 * 
	 * @return 表的映射类对象
	 */
	public Object load(Class<?> entityClass,Serializable id,LockMode lockMode)throws DataAccessException{
//		setLoadLog(entityClass.getName(), id, lockMode);
		return this.hds.getHibernateTemplate().load(entityClass, id, lockMode);
	}

	/**
	 * 更新或者插入一条数据
	 * 
	 * @param entityClass 表的映射类名
	 * 
	 * @return void
	 */
	public void saveOrUpdate(Object entity)throws DataAccessException{
		
//		try {
//			logger.info("Update : " + entity.getClass().getName() + " Parameters("
//					+ logger.getPropertyString(entity) + ")");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		this.hds.getHibernateTemplate().saveOrUpdate(entity);
		this.hds.getHibernateTemplate().flush();
	}
	
	/**
	 * 根据条件查询表中的数据
	 * 
	 * @param tabName 表的映射类对象名
	 * @param queryString 查询条件
	 * @param params 查询条件集
	 * 
	 * @return 表的映射类对象的List
	 */
	public List<?> find(String tabName,String queryString,Map<String, Object> params)throws DataAccessException{
		
		// 检索结果列表
		List<?> list = new ArrayList<Object>();
		
		// 获得当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// 设置查询条件
		queryString = "FROM " + tabName + " " + queryString ;
		Query query = session.createQuery(queryString);
		query = setParams(query,params);
		
		// 执行检索
		list = query.list();
		
		// 返回检索结果
		return list;
	}

	/**
	 * 删除一条或者多条数据
	 * 
	 * @param tabName 表的映射类对象名
	 * @param queryString 查询条件
	 * @param params 查询条件集
	 * 
	 * @return 删除条数
	 */
	public int delete(String tabName,String queryString,Map<String, Object> params)throws DataAccessException{
		
		// 执行SQL所影响的行数
		int delNumber = 0;
		
		// 获取当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// 设置查询条件
		queryString = "DELETE FROM " + tabName + " " + queryString ;
		Query query = session.createQuery(queryString);
		query = setParams(query,params);
		
		// 执行SQL
		delNumber = query.executeUpdate();
		
		// 返回执行SQL所影响的行数
		return delNumber;
	}	
	
	/**
	 * 更新一条或者多条数据
	 * 
	 * @param tabName 表的映射类对象名
	 * @param datas 更新值
	 * @param queryString 更新条件
	 * @param params 更新条件参数
	 * 
	 * @return 更新条数
	 */
	public int update(String tabName,Map<String, Object> datas,String queryString,Map<String, Object> params)throws DataAccessException{
		
		// 更新的数据行数
		int updNumber = 0;
		
		// 获取当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// 更新SQL作成
		StringBuffer updateString = new StringBuffer("UPDATE ");
		updateString.append(tabName);
		updateString.append(" SET ");
		
		// 循环设置更新字段和对应值
		if(null!=datas && 0!=datas.size()){
			Iterator<Entry<String,Object>> dtos = datas.entrySet().iterator();
			Entry<String,Object> entryFirst = dtos.next();
			updateString.append(entryFirst.getKey());
			updateString.append("=:");
			updateString.append(entryFirst.getKey());
			while(dtos.hasNext()){
				Entry<String,Object> entry = dtos.next();
				updateString.append(",");			
				updateString.append(entry.getKey());
				updateString.append("=:");
				updateString.append(entry.getKey());
			}
		}
		updateString.append(" ");
		
		// 设置更新条件
		updateString.append(queryString);
		
		// 建立查询对象
		Query query = session.createQuery(updateString.toString());
		
		// 设置参数
		params.putAll(datas);
		query = setParams(query,params);
		
		// 执行更新
		updNumber = query.executeUpdate();
		return updNumber;
	}

	/**
	 * 根据条件和数据条数的指定来查询表中的数据
	 * 
	 * @param tabName 表的映射类对象名
	 * @param queryString 查询条件
	 * @param params 查询条件集
	 * @param form BaseActionForm
	 * @param count 数据总条数
	 * @param pageSize 每一页显示的数据条数
	 * 
	 * @return 表的映射类对象的List
	 */
	public List<?> getResutlsForPage(String tabName,String queryString,Map<String, Object> params,BaseActionForm form,long count,int pageSize)throws DataAccessException{
		
		//定义当前画面页数为当前页
		int pageNo = form.getPageNo();
		
		//如果当前页数指定为0的话，设置为第一页
		if(0==pageNo){
			pageNo=1;
		}
		
		//如果指示显示前一页
		if(  0 != form.getPageNoBefore()){
			//画面页数设置为前一页
			pageNo = form.getPageNoBefore();
		}
		//如果指示显示后一页
		if(  0 != form.getPageNoNext()){
			//画面页数设置为后一页
			pageNo = form.getPageNoNext();
		}
		//如果指示显示最后一页
		if(  0 != form.getPageNoEnd()){
			//画面页数设置为最后一页
			pageNo = form.getPageNoEnd();
		}
		
		//取得当前数据可以显示的最大页数
		int pageNoMax = (int)Math.ceil(count/(double)pageSize);
		
		//如果最大页数不等于当前页数，同时指定页数为最后一页
		if(pageNoMax != pageNo &&  (0 != form.getPageNoEnd())){
			//设置画面页数为最后一页
			pageNo = pageNoMax;
		}
		//如果指定页数为下一页，同时当前页数的起始数据的条数大于数据总条数
		if( 0 != form.getPageNoNext() && (pageNo-1)*pageSize>count){
			//设置画面页数为最后一页
			pageNo = pageNoMax;
		}
		//如果指示显示当前页
		if( 0 != form.getPageNo()){
			//设置画面页数为当前页
			pageNo = form.getPageNo();
		}
		//如果当前页已经大于数据最大页数
		if(pageNo>pageNoMax){
			//设置画面页数为最后一页
			pageNo = pageNoMax;
		}
		
		//创建数据检索的QUERY对象
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		queryString = "FROM " + tabName + " "+queryString ;
		Query query = session.createQuery(queryString);
		query = setParams(query,params);

		//指定取数据的开始位置
		int firstRecord = (pageNo-1)*pageSize;
		query.setFirstResult(firstRecord);
		
		//指定取得数据的条数
		int maxRecord = pageSize;
		query.setMaxResults(maxRecord);
		
		//取得每一页的数据对象
		List<?> list = new ArrayList<Object>();
		list = query.list();
		
		//如果数据条数不为零
		if(!BaseUtility.isListNull(list)){
			//设置当前页数
			form.setPageNo(pageNo);
			//设置数据总条数
			form.setRecordCount(count);
			//设置最大页数
			form.setPageNoMax(pageNoMax);
			//如果当前页为第一页
			if(pageNo==1){
				//设置前一页为第一页
				form.setPageNoBefore(pageNo);
			}else{
				//否则的话设置前一页为当前页减一页
				form.setPageNoBefore(pageNo-1);
			}
			//如果当前页为最后一页
			if(pageNo==pageNoMax){
				//设置下一页为最后一页
				form.setPageNoNext(pageNo);
			}else{
				//否则的话设置下一页为当前页加一页
				form.setPageNoNext(pageNo+1);
			}
			//设置最大页数
			form.setPageNoEnd(pageNoMax);
		}else{
			//如果数据条数为零
			//设置当前页，下一页，前一页，最后一页都为第一页
			form.setPageNo(1);
			form.setPageNoMax(1);
			form.setPageNoBefore(1);
			form.setPageNoEnd(1);
			form.setPageNoNext(1);
			//设置数据条数为零
			form.setRecordCount(0);
		}
		//返回每一页的数据对象
		return list;
	}
	
	/**
	 * 根据条件和数据条数的指定来查询表中的数据
	 * (排序条件必须要有主键)
	 * 
	 * @param tabName 表的映射类对象名
	 * @param queryString 查询条件
	 * @param params 查询条件集
	 * @param form BaseActionForm
	 * @param count 数据总条数
	 * @param pageSize 每一页显示的数据条数
	 * 
	 * @return 表的映射类对象的List
	 * @throws Exception 
	 */
	public List<?> getResutlsForPageByKey(String tabName,String queryString,BaseActionForm form,long count,int pageSize)throws Exception{
		
		//定义当前画面页数为当前页
		int pageNo = form.getPageNo();
		
		//如果当前页数指定为0的话，设置为第一页

		if(0==pageNo){
			pageNo=1;
		}
		
		//如果指示显示前一页

		if(  0 != form.getPageNoBefore()){
			//画面页数设置为前一页

			pageNo = form.getPageNoBefore();
		}
		//如果指示显示后一页

		if(  0 != form.getPageNoNext()){
			//画面页数设置为后一页

			pageNo = form.getPageNoNext();
		}
		//如果指示显示最后一页

		if(  0 != form.getPageNoEnd()){
			//画面页数设置为最后一页

			pageNo = form.getPageNoEnd();
		}
		
		//取得当前数据可以显示的最大页数

		int pageNoMax = (int)Math.ceil(count/(double)pageSize);
		
		//如果最大页数不等于当前页数，同时指定页数为最后一页

		if(pageNoMax != pageNo &&  (0 != form.getPageNoEnd())){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		//如果指定页数为下一页，同时当前页数的起始数据的条数大于数据总条数

		if( 0 != form.getPageNoNext() && (pageNo-1)*pageSize>count){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		//如果指示显示当前页

		if( 0 != form.getPageNo()){
			//设置画面页数为当前页
			pageNo = form.getPageNo();
		}
		//如果当前页已经大于数据最大页数

		if(pageNo>pageNoMax){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		
		//创建数据检索的QUERY对象
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		queryString = "SELECT * FROM " + tabName + " "+queryString ;

		long start = pageSize * (pageNo - 1);
		long end = pageSize * pageNo ;
		if(end > count) end = count;
		
		String sql = "SELECT MYTABLE.* FROM (" + queryString + ") MYTABLE WHERE ROWNUM <=" + end;   
		logger.info("getListBySQL2 sql:" + sql);
		
		List list = null;
		
		hds.getHibernateTemplate().setAllowCreate(true);
		session = hds.getHibernateTemplate().getSessionFactory().getCurrentSession();
		Statement statement = session.connection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

		ResultSet rst = statement.executeQuery(sql);

		list = resultSetToList(rst,end - start);

		//如果数据条数不为零

		if(!BaseUtility.isListNull(list)){
			//设置当前页数
			form.setPageNo(pageNo);
			//设置数据总条数

			form.setRecordCount(count);
			//设置最大页数

			form.setPageNoMax(pageNoMax);
			//如果当前页为第一页

			if(pageNo==1){
				//设置前一页为第一页

				form.setPageNoBefore(pageNo);
			}else{
				//否则的话设置前一页为当前页减一页

				form.setPageNoBefore(pageNo-1);
			}
			//如果当前页为最后一页

			if(pageNo==pageNoMax){
				//设置下一页为最后一页

				form.setPageNoNext(pageNo);
			}else{
				//否则的话设置下一页为当前页加一页

				form.setPageNoNext(pageNo+1);
			}
			//设置最大页数

			form.setPageNoEnd(pageNoMax);
		}else{
			//如果数据条数为零
			//设置当前页，下一页，前一页，最后一页都为第一页

			form.setPageNo(1);
			form.setPageNoMax(1);
			form.setPageNoBefore(1);
			form.setPageNoEnd(1);
			form.setPageNoNext(1);
			//设置数据条数为零
			form.setRecordCount(0);
		}
		//返回每一页的数据对象
		return list;
	}
	
	/**
	 * 结果集转换成LIST
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	public static List resultSetToList(ResultSet rs,long pageSize) throws Exception{
       ResultSetMetaData metaData = rs.getMetaData();// 取得结果集的元素
       int colCount = metaData.getColumnCount();// 取得所有列的个数

       List list = new ArrayList();// 存放返回结果的容器

       rs.afterLast();
       rs.previous();
       
       for(int i =0;i<pageSize;i++)
       {
    	   rs.previous();
       }
       while(rs.next()){
           Object[] objects = new Object[colCount];
           for(int i=1;i<=colCount;i++){// 对于该记录的每一列

               try{
            	   objects[i - 1] = rs.getObject(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
           list.add(objects);
        }
       return list;
    }

	/**
	 * 根据条件来查询表中的数据条数
	 * 
	 * @param tabName 表的映射类对象名
	 * @param queryString 查询条件
	 * @param params 查询条件集
     * 
	 * @return 数据条数
	 */
	public int getResutlsTotal(String tabName,String queryString,Map<String, Object> params)throws DataAccessException{   
		
		// 数据条数
		int total = 0;
		
		// 获取当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// SQL作成
		queryString = "SELECT count(*) FROM " + tabName + " "+queryString ;
		
		// 建立查询
		Query query = session.createQuery(queryString);
		
		// 设置参数
		query = setParams(query,params);
		
		// 执行查询
		total = ((java.lang.Number)query.uniqueResult()).intValue();
		
		// 返回数据条数
		return total; 
	} 
	
	/**
	 * 设置参数
	 * @param query 查询对象
	 * @param params 参数表
	 * @return 设置参数后的查询对象
	 */
	private Query setParams(Query query, Map<String, Object> params) {
		
		// 参数表不为空，设置参数
		if (params != null && params.size() > 0) {

			// 获取参数名
			Iterator<String> iterator = params.keySet().iterator();
			
			// 循环取得参数名对应的值
			while (iterator.hasNext()) {
				String name = iterator.next();
				Object value = params.get(name);
				
				// 如果值不为空，设置到查询对象
				if (value != null) {
					if (value instanceof Collection) {
						// 如果是集合类型，将整个集合设置到参数中
						query.setParameterList(name, (Collection<?>) value);
					} else {
						// 不是集合类型，只设置一个值到参数中
						query.setParameter(name, value);
					}
				}
			}
		}
		
		// 返回查询对象
		return query;
	}
	
	/**
	 * 原生sql检索方法
	 * @param queryString 原生sql语句
	 * @param params where条件的参数
	 * @return 检索结果list
	 * @throws DataAccessException
	 */
	public List<?> findSQL(String queryString,Map<String, Object> params)throws DataAccessException{
		
		// 检索结果列表

		List<?> list = new ArrayList<Object>();
		
		// 获得当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// 设置查询条件
		Query query = session.createSQLQuery(queryString);
		query = setParams(query,params);
		
		// 执行检索

		list = query.list();
		
		// 返回检索结果

		return list;
	}

	/**
	 * 执行原生SQL语句
	 * @param exeString 执行的sql
	 * @param params 参数
	 * @return 更新的数据条数
	 * @throws DataAccessException
	 */
	public int exeSQL(String exeString,Map<String, Object> params)throws DataAccessException{
		
		// 获得当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// 设置条件
		Query query = session.createSQLQuery(exeString);
		query = setParams(query,params);
		
		// 执行sql
		int i = query.executeUpdate();
		// 返回执行结果

		return i;
	}
	
	/**
	 * 执行原生sql并实现分页功能
	 * @param querySql 原生sql语句
	 * @param params   sql参数
	 * @param form     表单
	 * @param count    总条数
	 * @param pageSize 每页条数
	 * @return  检索结果集
	 * @throws DataAccessException
	 */
	public List<?> getResutlsForPageSQL(String querySql,Map<String, Object> params,BaseActionForm form,long count,int pageSize)throws DataAccessException{
		//根据条件和数据条数的指定来查询表中的数据(原生sql实现)
		//定义当前画面页数为当前页
		int pageNo = form.getPageNo();
		
		//如果当前页数指定为0的话，设置为第一页

		if(0==pageNo){
			pageNo=1;
		}
		
		//如果指示显示前一页

		if(  0 != form.getPageNoBefore()){
			//画面页数设置为前一页

			pageNo = form.getPageNoBefore();
		}
		//如果指示显示后一页

		if(  0 != form.getPageNoNext()){
			//画面页数设置为后一页

			pageNo = form.getPageNoNext();
		}
		//如果指示显示最后一页

		if(  0 != form.getPageNoEnd()){
			//画面页数设置为最后一页

			pageNo = form.getPageNoEnd();
		}
		
		//取得当前数据可以显示的最大页数

		int pageNoMax = (int)Math.ceil(count/(double)pageSize);
		
		//如果最大页数不等于当前页数，同时指定页数为最后一页

		if(pageNoMax != pageNo &&  (0 != form.getPageNoEnd())){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		//如果指定页数为下一页，同时当前页数的起始数据的条数大于数据总条数

		if( 0 != form.getPageNoNext() && (pageNo-1)*pageSize>count){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		//如果指示显示当前页

		if( 0 != form.getPageNo()){
			//设置画面页数为当前页
			pageNo = form.getPageNo();
		}
		//如果当前页已经大于数据最大页数

		if(pageNo>pageNoMax){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		
		//创建数据检索的QUERY对象
		Session session = null;
		
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		Query query = session.createSQLQuery(querySql);
		query = setParams(query,params);

		//指定取数据的开始位置

		int firstRecord = (pageNo-1)*pageSize;
		query.setFirstResult(firstRecord);
		
		//指定取得数据的条数

		int maxRecord = pageSize;
		query.setMaxResults(maxRecord);
		
		//取得每一页的数据对象
		List<?> list = new ArrayList<Object>();
		list = query.list();
		
		//如果数据条数不为零

		if(!BaseUtility.isListNull(list)){
			//设置当前页数
			form.setPageNo(pageNo);
			//设置数据总条数

			form.setRecordCount(count);
			//设置最大页数

			form.setPageNoMax(pageNoMax);
			//如果当前页为第一页

			if(pageNo==1){
				//设置前一页为第一页

				form.setPageNoBefore(pageNo);
			}else{
				//否则的话设置前一页为当前页减一页

				form.setPageNoBefore(pageNo-1);
			}
			//如果当前页为最后一页

			if(pageNo==pageNoMax){
				//设置下一页为最后一页

				form.setPageNoNext(pageNo);
			}else{
				//否则的话设置下一页为当前页加一页

				form.setPageNoNext(pageNo+1);
			}
			//设置最大页数

			form.setPageNoEnd(pageNoMax);
		}else{
			//如果数据条数为零
			//设置当前页，下一页，前一页，最后一页都为第一页

			form.setPageNo(1);
			form.setPageNoMax(1);
			form.setPageNoBefore(1);
			form.setPageNoEnd(1);
			form.setPageNoNext(1);
			//设置数据条数为零
			form.setRecordCount(0);
		}
		//返回每一页的数据对象
		return list;
	}

	/**
	 * 执行原生sql检索数据条数，和分页方法getResutlsForPageSQL配合使用
	 * @param querySql 原生检索sql
	 * @param params   检索参数
	 * @return 数据条数
	 * @throws DataAccessException
	 */
	public int getResutlsTotalSQL(String querySql,Map<String, Object> params)throws DataAccessException{   
		
		// 数据条数
		int total = 0;
		
		// 获取当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// SQL作成
		String queryString = "SELECT count(1) FROM (" + querySql + ") MYTABLE ";
		
		// 建立查询
		Query query = session.createSQLQuery(queryString);
		
		// 设置参数
		query = setParams(query,params);
		
		// 执行查询
		total = ((java.lang.Number)query.uniqueResult()).intValue();
		
		// 返回数据条数
		return total; 
	} 
	
	/**
	 * 根据SQL取得List<Map>形式数据
	 * @param queryString
	 * @param params
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> find2Map(String queryString,Map<String, Object> params)throws DataAccessException{
		// 检索结果列表

		List<?> list = new ArrayList<Object>();
		
		// 获得当前Session
		Session session = null;
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		
		// 设置查询条件
		Query query = session.createSQLQuery(queryString).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query = setParams(query,params);
		
		// 执行检索

		list = query.list();
		
		// 返回检索结果

		return list;
	}

	/**
	 * 执行原生sql并实现分页功能
	 * @param querySql 原生sql语句
	 * @param params   sql参数
	 * @param form     表单
	 * @param count    总条数
	 * @param pageSize 每页条数
	 * @return  检索结果集
	 * @throws DataAccessException
	 */
	public List<?> getMapsForPageSQL(String querySql,Map<String, Object> params,BaseActionForm form,long count,int pageSize)throws DataAccessException{
		//根据条件和数据条数的指定来查询表中的数据(原生sql实现)
		//定义当前画面页数为当前页
		int pageNo = form.getPageNo();
		
		//如果当前页数指定为0的话，设置为第一页

		if(0==pageNo){
			pageNo=1;
		}
		
		//如果指示显示前一页

		if(  0 != form.getPageNoBefore()){
			//画面页数设置为前一页

			pageNo = form.getPageNoBefore();
		}
		//如果指示显示后一页

		if(  0 != form.getPageNoNext()){
			//画面页数设置为后一页

			pageNo = form.getPageNoNext();
		}
		//如果指示显示最后一页

		if(  0 != form.getPageNoEnd()){
			//画面页数设置为最后一页

			pageNo = form.getPageNoEnd();
		}
		
		//取得当前数据可以显示的最大页数

		int pageNoMax = (int)Math.ceil(count/(double)pageSize);
		
		//如果最大页数不等于当前页数，同时指定页数为最后一页

		if(pageNoMax != pageNo &&  (0 != form.getPageNoEnd())){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		//如果指定页数为下一页，同时当前页数的起始数据的条数大于数据总条数

		if( 0 != form.getPageNoNext() && (pageNo-1)*pageSize>count){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		//如果指示显示当前页

		if( 0 != form.getPageNo()){
			//设置画面页数为当前页
			pageNo = form.getPageNo();
		}
		//如果当前页已经大于数据最大页数

		if(pageNo>pageNoMax){
			//设置画面页数为最后一页

			pageNo = pageNoMax;
		}
		
		//创建数据检索的QUERY对象
		Session session = null;
		
		this.hds.getHibernateTemplate().setAllowCreate(true);
		session = this.hds.getHdsSession();
		Query query = session.createSQLQuery(querySql);
		query = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query = setParams(query,params);

		//指定取数据的开始位置

		int firstRecord = (pageNo-1)*pageSize;
		query = query.setFirstResult(firstRecord);
		
		//指定取得数据的条数

		int maxRecord = pageSize;
		query = query.setMaxResults(maxRecord);
		
		//取得每一页的数据对象
		List<?> list = new ArrayList<Object>();
		list = query.list();
		
		//如果数据条数不为零

		if(!BaseUtility.isListNull(list)){
			//设置当前页数
			form.setPageNo(pageNo);
			//设置数据总条数

			form.setRecordCount(count);
			//设置最大页数

			form.setPageNoMax(pageNoMax);
			//如果当前页为第一页

			if(pageNo==1){
				//设置前一页为第一页

				form.setPageNoBefore(pageNo);
			}else{
				//否则的话设置前一页为当前页减一页

				form.setPageNoBefore(pageNo-1);
			}
			//如果当前页为最后一页

			if(pageNo==pageNoMax){
				//设置下一页为最后一页

				form.setPageNoNext(pageNo);
			}else{
				//否则的话设置下一页为当前页加一页

				form.setPageNoNext(pageNo+1);
			}
			//设置最大页数

			form.setPageNoEnd(pageNoMax);
		}else{
			//如果数据条数为零
			//设置当前页，下一页，前一页，最后一页都为第一页

			form.setPageNo(1);
			form.setPageNoMax(1);
			form.setPageNoBefore(1);
			form.setPageNoEnd(1);
			form.setPageNoNext(1);
			//设置数据条数为零
			form.setRecordCount(0);
		}
		//返回每一页的数据对象
		return list;
	}
}
