package com.glaf.cluster.catalina.session;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class TestMain {
	
	private static Log log = LogFactory.getLog(TestMain.class);

	public static void save(String filename, byte[] bytes) {
		ByteArrayInputStream bais = null;
		try {
			bais = new ByteArrayInputStream(bytes);
			save(filename, bais);
			bais.close();
			bais = null;
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				if (bais != null) {
					bais.close();
				}
			} catch (IOException ex) {
			}
		}
	}

	public static void save(String filename, InputStream inputStream) {
		if (filename == null || inputStream == null) {
			return;
		}

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(inputStream);
			bos = new BufferedOutputStream(new FileOutputStream(filename));
			int bytesRead = 0;
			byte[] buffer = new byte[4096];
			while ((bytesRead = bis.read(buffer, 0, 4096)) != -1) {
				bos.write(buffer, 0, bytesRead);
			}
			bos.flush();
			bis.close();
			bos.close();
			bis = null;
			bos = null;
		} catch (IOException ex) {
			bis = null;
			bos = null;
			throw new RuntimeException(ex);
		} finally {
			try {
				if (bis != null) {
					bis.close();
					bis = null;
				}
				if (bos != null) {
					bos.close();
					bos = null;
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void main(String[] args) {
		save("c:\\start", String.valueOf(System.currentTimeMillis()).getBytes());
		log.debug("start");
		do {
			try {
				Thread.sleep(1000);
				log.debug("run");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (true);
	}

	public static void close(String[] args) {
		save("c:\\stop", String.valueOf(System.currentTimeMillis()).getBytes());
		System.out.println("stop");
		log.debug("stop");
		System.exit(3);
	}

}
