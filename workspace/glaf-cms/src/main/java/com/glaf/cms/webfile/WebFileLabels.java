package com.glaf.cms.webfile;

import java.util.ListResourceBundle;

public class WebFileLabels extends ListResourceBundle {

	private Object contents[][] = {
			{ "1005", "Cannot upload this file." },
			{ "1010", "Unauthorized file." },
			{ "1015", "File cannot be processed." },
			{ "1105", "File size exceeds upper limit." },
			{ "1110", "Overall size of files exceeds upper limit." },
			{ "1120", "Cannot save file." },
			{ "1125", "Path is not a virtual path." },
			{ "1400", "You have exceeded your quota on the server." },
			{
					"1405",
					"A problem occurred while attempting to create the folder. The operation has been cancelled." },
			{ "1410", "Cannot copy file." },
			{ "1415", "Cannot delete file : access denied." },
			{ "1420", "Cannot delete folder : access denied." },
			{ "1425", "File no longer available." },
			{ "1426", "Cannot rename file." },
			{ "1430",
					"Root folder unavailable. Application cannot be executed." },
			{ "1435", "Folder no longer available." },
			{ "1436", "Cannot rename folder." },
			{
					"1440",
					"A problem occurred while attempting to delete the file. The operation has been cancelled." },
			{ "1445", "File already exists." },
			{ "1450", "File does not exist." },
			{ "1455", "You must provide a name!" },
			{ "1460",
					"Name must include at least one character other than a space!" },
			{
					"1465",
					"Name cannot contain the following characters :\\n \\\\, /, :, *, ?, \\\", <, >, |" },
			{ "1470", "You must provide a path!" },
			{ "1475", "Use Cut or Copy before pasting a file or a folder!" },
			{
					"1480",
					"Cannot paste folder : destination folder is a sub-folder of the source folder!" },
			{ "1485", "Cannot move object!" },
			{ "1486", "Cannot copy object!" },
			{ "1490",
					"A file of the same name already exists - choose a different name." },
			{ "1495",
					"A folder of the same name already exists - choose a different name." },
			{ "1500", "You must enter a virtual path." },
			{ "1501", "The specified path is not a valid path." },
			{ "5000", "File" }, { "5001", "Folder" }, { "5008", "Rename" },
			{ "5009", "New file" }, { "5010", "New folder" },
			{ "5011", "Send file" }, { "5012", "Save folder" },
			{ "5013", "Save new name" }, { "5014", "Paste object" },
			{ "6000", "Name" }, { "6001", "folder" }, { "6002", "file" },
			{ "6003", "folders" }, { "6004", "files" }, { "6005", "Size" },
			{ "6006", "Type" }, { "6007", "Modified" },
			{ "7001", "Parent folder" }, { "7002", "Kb" }, { "7003", "Open" },
			{ "7004", "Copy of " }, { "8000", "Name" }, { "8001", "Size" },
			{ "8002", "Link" }, { "8003", "URL" }, { "8004", "Type" },
			{ "8005", "MS-Dos name" }, { "8006", "Location" },
			{ "8007", "Contains" }, { "8008", "Created" },
			{ "8009", "Modified" }, { "8010", "Accessed" },
			{ "8011", "Attributes" }, { "8012", "Read-only" },
			{ "8013", "Hidden" }, { "8014", "System" }, { "8015", "Archive" },
			{ "8016", "Download" }, { "8017", "UpLoad file..." },
			{ "8018", "byte" }, { "8019", "bytes" },
			{ "8020", "Select a file :" }, { "8021", "Name :" },
			{ "8022", "New name :" }, { "8023", "inaccessible" },
			{ "8024", "OverWrite :" }, { "9000", "Authentication required." },
			{ "9001", "Select a file or a folder." },
			{ "9002", "Are you sure you want to delete ?" },
			{ "9999", "INFORMATION" } };

	public WebFileLabels() {
	}

	public Object[][] getContents() {
		return contents;
	}
}
