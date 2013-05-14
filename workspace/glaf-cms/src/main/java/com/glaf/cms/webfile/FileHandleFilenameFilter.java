package com.glaf.cms.webfile;

import java.io.File;
import java.io.FilenameFilter;

public class FileHandleFilenameFilter implements FilenameFilter {

	protected String normalizedFilter = null;

	protected String originalFilter = null;

	public FileHandleFilenameFilter(String filter) {
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < filter.length(); i++)
			if (filter.charAt(i) != '*' || buffer.length() <= 0
					|| buffer.charAt(buffer.length() - 1) != '*')
				buffer.append(filter.charAt(i));

		normalizedFilter = buffer.toString();
		originalFilter = filter;
	}

	public boolean accept(File dir, String name) {
		if (normalizedFilter.length() > 0)
			return accept(name, 0, 0);
		else
			return true;
	}

	protected boolean accept(String name, int posName, int posFilter) {
		for (; posFilter < normalizedFilter.length(); posFilter++)
			switch (normalizedFilter.charAt(posFilter)) {
			case 63: // '?'
				if (posName < name.length())
					posName++;
				else
					return false;
				break;

			case 42: // '*'
				if (posFilter == normalizedFilter.length() - 1)
					return true;
				char next = normalizedFilter.charAt(posFilter + 1);
				if (next == '?') {
					for (int i = posName; i < name.length(); i++)
						if (accept(name, i, posFilter + 1))
							return true;

					return false;
				}
				for (int i = name.indexOf(next, posName); i < name.length()
						&& i != -1; i = name.indexOf(next, i + 1))
					if (accept(name, i, posFilter + 1))
						return true;

				return false;

			default:
				if (posName < name.length()
						&& normalizedFilter.charAt(posFilter) == name
								.charAt(posName))
					posName++;
				else
					return false;
				break;

			}

		return posName == name.length();
	}

	public String getFilter() {
		return originalFilter;
	}
}
