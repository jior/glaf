package baseSrc.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import baseSrc.common.AutoArrayList;
import baseSrc.common.DbAccess;
import baseSrc.common.LogHelper;

public class BaseDAO {

	protected static LogHelper logger = new LogHelper(BaseDAO.class);

	private String defaultMethod;

	protected DbAccess dbAccess;

	public String getDefaultMethod() {
		return defaultMethod;
	}

	public void setDefaultMethod(String defaultMethod) {
		this.defaultMethod = defaultMethod;
	}

	public void setDbAccess(DbAccess dbAccess) {
		this.dbAccess = dbAccess;
	}

	// 用hashmap结构的数据，按照Key对应关系自动设置actionform类的属性
	public void FillFormByHashMapWithKey(Object form, HashMap<String, Object> hm) {

		if (null != hm && 0 != hm.size()) {

			Iterator<Entry<String, Object>> dtos = hm.entrySet().iterator();

			// 遍历hashmap中的所有数据
			while (dtos.hasNext()) {

				Entry<String, Object> entry = dtos.next();
				// 将每个数据设置到对应form字段中
				fillFormField(form, entry);
			}
		}

	}

	private void fillFormField(Object form, Entry<String, Object> entry) {

		String methodName = entry.getKey();
		// 将Key的第一个字符变为大写
		methodName = methodName.replaceFirst(methodName.substring(0, 1), methodName.substring(0, 1).toUpperCase());

		// 还有明细？
		if (entry.getValue() instanceof List) {

			List<?> mapDetails = (List<?>) entry.getValue();
			// 调用form的get方法获取明细数据
			AutoArrayList formDetails = (AutoArrayList) callGetField(form,
					methodName);

			for (int i = 0; i < mapDetails.size(); i++) {
				@SuppressWarnings("unchecked")
				HashMap<String, Object> mapDetail = (HashMap<String, Object>) mapDetails
						.get(i);
				Object formDetail = formDetails.get(i);

				// 递归调用hashmap数据映射form内属性方法
				FillFormByHashMapWithKey(formDetail, mapDetail);
			}
		} else {

			// 调用form的set方法设置内容
			callSetField(form, methodName, entry.getValue());
		}

	}

	private Object callGetField(Object form, String methodName) {
		Object ret = null;
		Method m = null;
		methodName = "get" + methodName;

		// 执行方法
		try {

			// 取得方法
			m = form.getClass().getMethod(methodName);
			ret = m.invoke(form);
		}
		// 找不到方法异常
		catch (NoSuchMethodException ex) {
			throw new BaseException("[" + methodName
					+ "] is not be found in the " + form.getClass().getName(),
					ex);
		}
		// 访问权限异常
		catch (IllegalAccessException ex) {
			throw new BaseException(ex);
		}
		// 参数异常
		catch (IllegalArgumentException ex) {
			throw new BaseException(ex);
		}
		// 调用对象错误
		catch (InvocationTargetException ex) {
			throw new BaseException(ex);
		}

		return ret;

	}

	private void callSetField(Object form, String methodName, Object value) {

		Method m = null;
		//zhongmin add 20120820
		Method[] ms = null;
		
		methodName = "set" + methodName;

		// instanceOf AutoArrayList
		// 定义接口需要的类型
		Class<?>[] clazz = new Class[1];
		if (null == value) {
			clazz[0] = Object.class;
		} else {
			clazz[0] = value.getClass();
		}

		// 执行方法
		try {

			// 取得方法
			//zhongmin zhushi 20120820
			//m = form.getClass().getMethod(methodName, clazz);
			//zhongmin end			
			//zhongmin add 20120820
			ms =	form.getClass().getMethods();
			for(Method meth:ms){
				if(methodName.equals(meth.getName())){
					m = meth;
					break;
				}
			}
			//zhongmin end
			// 构造参数
			Object[] object = new Object[1];
			object[0] = value;
			if(m !=null){
				m.invoke(form, object);
			}
		}
		// 找不到方法异常
		//zhongmin zhushi 20120820
		//catch (NoSuchMethodException ex) {
		//	throw new BaseException("[" + methodName
		//			+ "] is not be found in the " + form.getClass().getName(),
		//			ex);
		//}
		//zhongmin end
		// 访问权限异常
		catch (IllegalAccessException ex) {
			throw new BaseException(ex);
		}
		// 参数异常
		catch (IllegalArgumentException ex) {
			throw new BaseException(ex);
		}
		// 调用对象错误
		catch (InvocationTargetException ex) {
			throw new BaseException(ex);
		}

	}

}
