请在本目录放execution配置文件,属性文件的key为实现ExecutionHandler接口的类，value是每个类的配置信息。
package com.glaf.core.execution;

public interface ExecutionHandler {

	/**
	 * 执行业务逻辑
	 */
	void execute(String content);
}
