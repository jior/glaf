package com.glaf.cms.webfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileHandle {

	public static void copyFile(String srcPath, String dstPath)
			throws IOException {
		if (!isPathExact(srcPath)
				|| dstPath.charAt(dstPath.length() - 1) == File.separatorChar) {
			if (!folderExists(dstPath))
				throw new IOException();
			File fDstParent = new File(dstPath);
			if (!isPathExact(srcPath)) {
				File fSrcParent = getAbsoluteParent(srcPath);
				String sFileSpec = getFilename(srcPath);
				if (fSrcParent.isDirectory()) {
					String files[] = fSrcParent
							.list(new FileHandleFilenameFilter(sFileSpec));
					if (files.length == 0)
						throw new FileNotFoundException();
					for (int i = 0; i < files.length; i++) {
						File fSrcPending = new File(fSrcParent, files[i]);
						File fDstPending = new File(fDstParent, files[i]);
						if (!fDstPending.isDirectory()) {
							FileUtils.copyFile(fSrcPending, fDstPending);
						} else
							throw new IOException();
					}

				} else {
					throw new FileNotFoundException();
				}
			} else {
				File fSrcPending = new File(srcPath);
				File fDstPending = new File(fDstParent, getFilename(srcPath));
				if (!fDstPending.isDirectory()) {
					FileUtils.copyFile(fSrcPending, fDstPending);
				} else
					throw new IOException();
			}
		} else {
			File fSrcPending = new File(srcPath);
			File fDstPending = new File(dstPath);
			if (!fDstPending.isDirectory()) {
				FileUtils.copyFile(fSrcPending, fDstPending);
			} else
				throw new IOException();
		}
	}

	private static void copyFolder(File srcFolder, File dstFolder)
			throws IOException {
		if (!dstFolder.exists() && !dstFolder.mkdirs())
			throw new IOException();
		if (!dstFolder.isDirectory())
			throw new IOException();
		String files[] = srcFolder.list();
		for (int i = 0; i < files.length; i++) {
			File srcFile = new File(srcFolder, files[i]);
			File dstFile = new File(dstFolder, files[i]);
			if (srcFile.isDirectory()) {
				copyFolder(srcFile, dstFile);
			} else {
				FileUtils.copyFile(srcFile, dstFile);
			}
		}
	}

	public static void copyFolder(String srcPath, String dstPath)
			throws IOException {
		if (!isPathExact(srcPath)
				|| dstPath.charAt(dstPath.length() - 1) == File.separatorChar) {
			if (!folderExists(dstPath))
				throw new IOException();
			File fDstParent = new File(dstPath);
			if (!isPathExact(srcPath)) {
				File fSrcParent = getAbsoluteParent(srcPath);
				String sFileSpec = getFilename(srcPath);
				if (fSrcParent.isDirectory()) {
					String files[] = fSrcParent
							.list(new FileHandleFilenameFilter(sFileSpec));
					if (files.length == 0)
						throw new FileNotFoundException();
					for (int i = 0; i < files.length; i++) {
						File fSrcPending = new File(fSrcParent, files[i]);
						File fDstPending = new File(fDstParent, files[i]);
						if (!fDstPending.exists() || fDstPending.isDirectory()) {
							copyFolder(fSrcPending, fDstPending);
						} else
							throw new IOException();
					}

				} else {
					throw new FileNotFoundException();
				}
			} else {
				File fSrcPending = new File(srcPath);
				File fDstPending = new File(fDstParent, getFilename(srcPath));
				if (!fDstPending.exists() || fDstPending.isDirectory()) {
					copyFolder(fSrcPending, fDstPending);
				} else
					throw new IOException();
			}
		} else {
			File fSrcPending = new File(srcPath);
			File fDstPending = new File(dstPath);
			if (!fDstPending.exists() || fDstPending.isDirectory()) {
				copyFolder(fSrcPending, fDstPending);
			} else
				throw new IOException();
		}
	}

	public static void deleteFile(String path) throws IOException {
		File fParent = getAbsoluteParent(path);
		String sFileSpec = getFilename(path);
		if (fParent.isDirectory()) {
			String files[] = fParent.list(new FileHandleFilenameFilter(
					sFileSpec));
			if (files.length == 0)
				throw new FileNotFoundException();
			for (int i = 0; i < files.length; i++) {
				File fPending = new File(fParent, files[i]);
				if (!fPending.delete())
					throw new IOException();
			}

		} else {
			throw new FileNotFoundException();
		}
	}

	private static boolean deleteFolder(File folder) {
		String files[] = folder.list();
		for (int i = 0; i < files.length; i++) {
			File file = new File(folder, files[i]);
			if (file.isDirectory()) {
				if (!deleteFolder(file))
					return false;
				continue;
			}
			if (!file.delete())
				return false;
		}

		return folder.delete();
	}

	public static void deleteFolder(String path) throws IOException {
		File fParent = getAbsoluteParent(path);
		String sFileSpec = getFilename(path);
		if (fParent.isDirectory()) {
			String files[] = fParent.list(new FileHandleFilenameFilter(
					sFileSpec));
			if (files.length == 0)
				throw new FileNotFoundException();
			for (int i = 0; i < files.length; i++) {
				File fPending = new File(fParent, files[i]);
				if (!fPending.isDirectory() || !deleteFolder(fPending))
					throw new IOException();
			}

		} else {
			throw new FileNotFoundException();
		}
	}

	public static boolean fileExists(String path) {
		return (new File(path)).isFile();
	}

	public static boolean folderExists(String path) {
		File f = new File(path);
		return f.exists() && f.isDirectory();
	}

	private static File getAbsoluteParent(String path) {
		File fRequested = new File(path);
		File fAbsolute = new File(fRequested.getAbsolutePath());
		File fParent = new File(fAbsolute.getParent());
		return fParent;
	}

	public static String getFilename(String path) {
		return (new File(path)).getName();
	}

	private static boolean isPathExact(String path) {
		return path.indexOf(42) == -1 && path.indexOf(63) == -1;
	}

	public static void moveFile(String srcPath, String dstPath)
			throws IOException {
		moveObject(srcPath, dstPath);
	}

	public static void moveFolder(String srcPath, String dstPath)
			throws IOException {
		moveObject(srcPath, dstPath);
	}

	private static void moveObject(String srcPath, String dstPath)
			throws IOException {
		if (!isPathExact(srcPath)
				|| dstPath.charAt(dstPath.length() - 1) == File.separatorChar) {
			if (!folderExists(dstPath))
				throw new IOException();
			File fDstParent = new File(dstPath);
			if (!isPathExact(srcPath)) {
				File fSrcParent = getAbsoluteParent(srcPath);
				String sFileSpec = getFilename(srcPath);
				if (fSrcParent.isDirectory()) {
					String files[] = fSrcParent
							.list(new FileHandleFilenameFilter(sFileSpec));
					if (files.length == 0)
						throw new FileNotFoundException();
					for (int i = 0; i < files.length; i++) {
						File fSrcPending = new File(fSrcParent, files[i]);
						File fDstPending = new File(fDstParent, files[i]);
						if (fDstPending.exists()
								|| !fSrcPending.renameTo(fDstPending))
							throw new IOException();
					}

				} else {
					throw new FileNotFoundException();
				}
			} else {
				File fSrcPending = new File(srcPath);
				File fDstPending = new File(fDstParent, getFilename(srcPath));
				if (fDstPending.exists() || !fSrcPending.renameTo(fDstPending))
					throw new IOException();
			}
		} else {
			File fSrcPending = new File(srcPath);
			File fDstPending = new File(dstPath);
			if (fDstPending.exists() || !fSrcPending.renameTo(fDstPending))
				throw new IOException();
		}
	}

	private FileHandle() {
	}
}
