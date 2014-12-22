/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.core.job;

import java.lang.reflect.Method;

import org.quartz.Job;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class JobCglibProxy implements MethodInterceptor {

	protected Enhancer enhancer = new Enhancer();

	public Object getProxy(Class<?> clazz) {
		// 设置需要创建的子类
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		// 通过字节码技术动态创建子类实例
		return enhancer.create();
	}

	@Override
	public Object intercept(Object object, Method method, Object[] args,
			MethodProxy proxy) throws Throwable {
		// 目标方法调用
		Object result = proxy.invokeSuper(object, args);
		// 目标方法后执行
		return result;
	}

	public static void main(String[] args) throws Exception {
		JobCglibProxy proxy = new JobCglibProxy();
		// 通过动态生成子类的方式创建代理类
		Job target = (Job) proxy.getProxy(SimpleJob.class);
		target.execute(null);
	}

}