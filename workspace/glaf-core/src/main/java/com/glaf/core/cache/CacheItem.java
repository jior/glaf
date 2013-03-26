package com.glaf.core.cache;

public class CacheItem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	protected String key;
	protected long lastModified;
	protected int size;

	public CacheItem() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CacheItem other = (CacheItem) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}

	public String getKey() {
		return key;
	}

	public long getLastModified() {
		return lastModified;
	}

	public int getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setSize(int size) {
		this.size = size;
	}

}
