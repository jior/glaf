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

package com.glaf.core.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.AccessToken;
import com.glaf.core.cache.CacheFactory;

/**
 * 公众平台通用接口工具类
 * 
 */
public class TokenUtils {
	private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

	/**
	 * 获取access_token
	 * 
	 * @param accountId
	 *            凭证
	 * @param appSecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String access_token_url,
			String accountId, String appSecret) {
		AccessToken accessToken = null;
		String cacheKey = accountId + "_" + appSecret;
		if (CacheFactory.getString(cacheKey) != null) {
			String str = (String) CacheFactory.getString(cacheKey);
			JSONObject jsonObject = JSON.parseObject(str);
			accessToken = new AccessToken();
			accessToken.setToken(jsonObject.getString("access_token"));
			accessToken.setExpiresIn(jsonObject.getInteger("expires_in"));
		} else {
			String requestUrl = access_token_url.replace("APPID", accountId)
					.replace("APPSECRET", appSecret);
			JSONObject jsonObject = HttpUtils.executeRequest(requestUrl, "GET",
					null);
			// 如果请求成功
			if (null != jsonObject) {
				log.debug(jsonObject.toJSONString());
				try {
					accessToken = new AccessToken();
					accessToken.setToken(jsonObject.getString("access_token"));
					accessToken.setExpiresIn(jsonObject
							.getInteger("expires_in"));
					CacheFactory.put(cacheKey, jsonObject.toJSONString());
				} catch (JSONException e) {
					accessToken = null;
					// 获取token失败
					log.error("获取token失败 errcode:{} errmsg:{}",
							jsonObject.getInteger("errcode"),
							jsonObject.getString("errmsg"));
				}
			}
		}
		return accessToken;
	}

	public static String getServiceUrl(HttpServletRequest request) {
		String serviceUrl = "http://" + request.getServerName();
		if (request.getServerPort() != 80) {
			serviceUrl += ":" + request.getServerPort();
		}
		if (!"/".equals(request.getContextPath())) {
			serviceUrl += request.getContextPath();
		}
		return serviceUrl;
	}

}