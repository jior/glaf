package com.radiadesign.catalina.session;

import java.security.Principal;

import org.apache.catalina.Manager;
import org.apache.catalina.session.StandardSession;
 
import java.util.Map;

public class RedisSession extends StandardSession {

	private static final long serialVersionUID = 1L;
	protected static Boolean manualDirtyTrackingSupportEnabled = false;

	public static void setManualDirtyTrackingSupportEnabled(Boolean enabled) {
		manualDirtyTrackingSupportEnabled = enabled;
	}

	protected static String manualDirtyTrackingAttributeKey = "__changed__";

	public static void setManualDirtyTrackingAttributeKey(String key) {
		manualDirtyTrackingAttributeKey = key;
	}

	protected  Map<String, Object> changedAttributes;
	protected Boolean dirty;

	public RedisSession(Manager manager) {
		super(manager);
		resetDirtyTracking();
	}

	public Boolean isDirty() {
		return dirty || !changedAttributes.isEmpty();
	}

	public  Map<String, Object> getChangedAttributes() {
		return changedAttributes;
	}

	public void resetDirtyTracking() {
		changedAttributes = new java.util.concurrent.ConcurrentHashMap<String, Object>();
		dirty = false;
	}

	@Override
	public void setAttribute(String key, Object value) {
		if (manualDirtyTrackingSupportEnabled
				&& manualDirtyTrackingAttributeKey.equals(key)) {
			dirty = true;
			return;
		}

		Object oldValue = getAttribute(key);
		if ((value == null && oldValue != null)
				|| (oldValue == null && value != null)
				|| (value != null && !value.getClass().isInstance(oldValue))
				|| (value != null && !value.equals(oldValue))) {
			changedAttributes.put(key, value);
		}

		super.setAttribute(key, value);
	}

	@Override
	public void removeAttribute(String name) {
		dirty = true;
		super.removeAttribute(name);
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void setPrincipal(Principal principal) {
		dirty = true;
		super.setPrincipal(principal);
	}

}
