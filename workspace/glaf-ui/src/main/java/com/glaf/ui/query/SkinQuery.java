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

package com.glaf.ui.query;

import java.util.*;
import com.glaf.core.query.*;

public class SkinQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String titleLike;
	protected String descriptionLike;
	protected String imageLike;
	protected String styleClassLike;

	public SkinQuery() {

	}

	public SkinQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public String getDescriptionLike() {
		return descriptionLike;
	}

	public String getImageLike() {
		return imageLike;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		return nameLike;
	}

	public List<String> getNames() {
		return names;
	}

	public String getStyleClassLike() {
		return styleClassLike;
	}

	public String getTitleLike() {
		return titleLike;
	}

	public SkinQuery imageLike(String imageLike) {
		if (imageLike == null) {
			throw new RuntimeException("image is null");
		}
		this.imageLike = imageLike;
		return this;
	}

	public SkinQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SkinQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SkinQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setImageLike(String imageLike) {
		this.imageLike = imageLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setStyleClassLike(String styleClassLike) {
		this.styleClassLike = styleClassLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public SkinQuery styleClassLike(String styleClassLike) {
		if (styleClassLike == null) {
			throw new RuntimeException("styleClass is null");
		}
		this.styleClassLike = styleClassLike;
		return this;
	}

	public SkinQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}