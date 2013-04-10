package com.glaf.base.modules.sys.business;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.modules.sys.model.RealmInfo;
import com.glaf.base.modules.sys.service.SysApplicationService;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.PropertiesUtils;

public class RealmBuilder {
	private static final Log logger = LogFactory
			.getLog(RealmBuilder.class);

	protected SysApplicationService sysApplicationService;

	public SysApplicationService getSysApplicationService() {
		if (sysApplicationService == null) {
			sysApplicationService = ContextFactory
					.getBean("sysApplicationService");
		}
		return sysApplicationService;
	}

	public void setSysApplicationService(
			SysApplicationService sysApplicationService) {
		this.sysApplicationService = sysApplicationService;
	}

	public java.util.Properties buildRealms() {
		java.util.Properties props = new java.util.Properties();
		List<RealmInfo> realms = getSysApplicationService().getRealmInfos();
		if (realms != null && !realms.isEmpty()) {
			Map<String, StringBuffer> map = new HashMap<String, StringBuffer>();
			for (RealmInfo realm : realms) {
				StringBuffer sb = (StringBuffer) map.get(realm.getUrl());
				if (sb == null) {
					sb = new StringBuffer();
				}
				sb.append(realm.getItem());
				sb.append(",");
				map.put(realm.getUrl(), sb);
			}
			Iterator<String> iterator = map.keySet().iterator();
			while (iterator.hasNext()) {
				String url = (String) iterator.next();
				StringBuffer sb = (StringBuffer) map.get(url);
				String perms = sb.toString();
				if (perms.endsWith(",")) {
					perms = perms.substring(0, perms.lastIndexOf(","));
				}
				if(url.indexOf("?") != -1){
					url = url.substring(0, url.indexOf("?"));
				}
				props.put(url, "authc, perms[\"" + perms + "\"]");
			}
		}
		return props;
	}
	
	public static void main(String[] args) throws IOException{
		RealmBuilder builder = new RealmBuilder();
		java.util.Properties props = builder.buildRealms();
		logger.info(props);
		PropertiesUtils.save("glaf-base-security.properties", props);
	}

}
