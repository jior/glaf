/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.watcher;

import java.io.File;

/**
 * Callback interface that file changes File Watcher is using to notify
 * listeners about changes.
 */
public class FileChangesListener {
	/**
	 * Called for every file found in the watched directory during
	 * initialization
	 */
	public void onFileInit(File file) {

	}

	/**
	 * Called for every subdirectory found in the watched directory during
	 * initialization
	 */
	public void onDirectoryInit(File file) {

	}

	/**
	 * Called for every new file found in the watched directory
	 */
	public void onFileCreated(File file) {

	}

	/**
	 * Called for every file that disappeared in the watched directory
	 */
	public void onFileDeleted(File file) {

	}

	/**
	 * Called for every file that was changed in the watched directory
	 */
	public void onFileChanged(File file) {

	}

	/**
	 * Called for every new subdirectory found in the watched directory
	 */
	public void onDirectoryCreated(File file) {

	}

	/**
	 * Called for every file that disappeared in the watched directory
	 */
	public void onDirectoryDeleted(File file) {

	}
}
