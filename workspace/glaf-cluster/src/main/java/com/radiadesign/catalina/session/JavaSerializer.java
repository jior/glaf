package com.radiadesign.catalina.session;

import org.apache.catalina.util.CustomObjectInputStream;

import javax.servlet.http.HttpSession;

import java.io.*;

public class JavaSerializer implements Serializer {
	
	private ClassLoader loader;

	public HttpSession deserializeInto(byte[] data, HttpSession session)
			throws IOException  {
		RedisSession redisSession = (RedisSession) session;

		BufferedInputStream bis = null;
		ObjectInputStream ois = null;
		try {
			bis = new BufferedInputStream(new ByteArrayInputStream(data));
			ois = new CustomObjectInputStream(bis, loader);
			redisSession.setCreationTime(ois.readLong());
			redisSession.readObjectData(ois);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bis != null) {
				bis.close();
			}
			if (ois != null) {
				ois.close();
			}
		}
		return session;
	}

	public byte[] serializeFrom(HttpSession session) throws IOException {
		RedisSession redisSession = (RedisSession) session;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream oos = null;
		try {
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(new BufferedOutputStream(bos));
			oos.writeLong(redisSession.getCreationTime());
			redisSession.writeObjectData(oos);
		} catch (IOException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (oos != null) {
				oos.close();
			}
		}

		return bos.toByteArray();
	}

	public void setClassLoader(ClassLoader loader) {
		this.loader = loader;
	}
}
