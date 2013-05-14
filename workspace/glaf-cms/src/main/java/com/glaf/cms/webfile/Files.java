package com.glaf.cms.webfile;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;

public class Files {
	private int m_counter;

	private Hashtable<Integer, MyFile> m_files;

	Files() {
		m_files = new Hashtable<Integer, MyFile>();
		m_counter = 0;
	}

	protected void addFile(MyFile newFile) {
		if (newFile == null) {
			throw new IllegalArgumentException("newFile cannot be null.");
		} else {
			m_files.put(new Integer(m_counter), newFile);
			m_counter++;
			return;
		}
	}

	public Collection<MyFile> getCollection() {
		return m_files.values();
	}

	public int getCount() {
		return m_counter;
	}

	public Enumeration<MyFile> getEnumeration() {
		return m_files.elements();
	}

	public MyFile getFile(int index) {
		if (index < 0) {
			throw new IllegalArgumentException(
					"File's index cannot be a negative value (1210).");
		}
		MyFile retval = (MyFile) m_files.get(new Integer(index));
		if (retval == null) {
			throw new IllegalArgumentException(
					"Files' name is invalid or does not exist (1205).");
		} else {
			return retval;
		}
	}

	public long getSize() throws IOException {
		long tmp = 0L;
		for (int i = 0; i < m_counter; i++) {
			tmp += getFile(i).getSize();
		}

		return tmp;
	}
}
