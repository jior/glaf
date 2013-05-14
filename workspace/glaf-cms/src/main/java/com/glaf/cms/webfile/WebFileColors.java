package com.glaf.cms.webfile;

import java.util.ListResourceBundle;

public class WebFileColors extends ListResourceBundle {

	private Object contents[][] = { { "FRAME_BORDER", "#eeeeee" },
			{ "TOOLBAR_BG", "#999999" }, { "TOOLBAR_LINK_ENABLE", "#0000FF" },
			{ "TOOLBAR_LINK_DISABLE", "#CE8E00" },
			{ "TOOLBAR_LINK_VISITED", "#000000" },
			{ "TOOLBAR_LINK_HOVER", "#0000FF" },
			{ "TOOLBAR_LINK_ACTIVE", "#0000FF" },
			{ "FOLDERSELECT_BG", "#eeeeee" }, { "COLHEADER_BG", "#FFFFFF" },
			{ "COLHEADER_CAPTION_BG", "#999999" },
			{ "COLHEADER_CAPTION_FONT", "#FFFFFF" }, { "LIST_BG", "#FFFFFF" },
			{ "LIST_SUBJECT_FONT", "#000000" },
			{ "LIST_SUBJECT_SELECTED", "#FFFFFF" },
			{ "LIST_SUBJECT_SELECTED_BG", "#000084" },
			{ "LIST_LINK_ENABLE", "#0000FF" }, { "PROP_BG", "#eeeeee" },
			{ "PROP_CAPTION_BG", "#cccccc" }, { "PROP_CAPTION", "#000000" },
			{ "PROP_VALUE_BG", "#eeeeee" }, { "PROP_VALUE", "#000000" },
			{ "PROP_LINK_ENABLE", "#0000FF" },
			{ "PROP_LINK_DISABLE", "#CE8E00" },
			{ "PROP_LINK_VISITED", "#000000" },
			{ "PROP_LINK_HOVER", "#0000FF" },
			{ "PROP_LINK_ACTIVE", "#0000FF" }, { "ERR_BG", "#FFFFFF" },
			{ "ERR_TITLE", "#CD3333" }, { "ERR_TITLE_BG", "#FFCC99" },
			{ "ERR_MSG", "#FF0000" }, { "ERR_MSG_BG", "#FFFFFF" },
			{ "POPUP_BG", "#FFFFFF" }, { "POPUP_FONT", "#000000" } };

	public WebFileColors() {
	}

	public Object[][] getContents() {
		return contents;
	}
}
