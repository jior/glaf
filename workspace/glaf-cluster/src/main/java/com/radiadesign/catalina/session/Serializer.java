package com.radiadesign.catalina.session;

import javax.servlet.http.HttpSession;
import java.io.IOException;

public interface Serializer {
	
	HttpSession deserializeInto(byte[] data, HttpSession session)
			throws IOException;

	byte[] serializeFrom(HttpSession session) throws IOException;

	void setClassLoader(ClassLoader loader);
}
