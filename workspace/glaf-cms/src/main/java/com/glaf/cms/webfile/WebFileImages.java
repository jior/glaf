package com.glaf.cms.webfile;

import java.util.ListResourceBundle;

public class WebFileImages extends ListResourceBundle {

	private Object contents[][] = { { "imageSortUp", "sort_up.gif" },
			{ "imageSortDown", "sort_down.gif" },
			{ "separatorHigh", "LineVout.gif" },
			{ "separatorHigh2", "lineVin.gif" },
			{ "separatorShort", "LineVshort.gif" },
			{ "separatorHz", "LineH.gif" },
			{ "newFileActive", "newFilea.gif" },
			{ "newFolderActive", "newFoldera.gif" },
			{ "saveActive", "savea.gif" },
			{ "saveFileActive", "saveFilea.gif" },
			{ "saveFolderActive", "saveFoldera.gif" },
			{ "cutActive", "cuta.gif" }, { "copyActive", "copya.gif" },
			{ "pasteActive", "pastea.gif" }, { "deleteActive", "deletea.gif" },
			{ "renameActive", "renamea.gif" },
			{ "newFileDesactive", "newFiled.gif" },
			{ "newFolderDesactive", "newFolderd.gif" },
			{ "saveDesactive", "saved.gif" },
			{ "saveFolderDesactive", "saveFolderd.gif" },
			{ "cutDesactive", "cutd.gif" }, { "copyDesactive", "copyd.gif" },
			{ "pasteDesactive", "pasted.gif" },
			{ "deleteDesactive", "deleted.gif" },
			{ "renameDesactive", "renamed.gif" }, { "imageNull", "null.gif" },
			{ "iconFolder", "folder.gif" },
			{ "iconBackFolder", "backfolder.gif" }, { "imageWait", "wait.gif" } };

	public WebFileImages() {
	}

	public Object[][] getContents() {
		return contents;
	}
}
