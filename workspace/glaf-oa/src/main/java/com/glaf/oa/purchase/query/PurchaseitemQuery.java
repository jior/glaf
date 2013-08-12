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
package com.glaf.oa.purchase.query;

import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class PurchaseitemQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> purchaseitemids;
	protected Long purchaseid;
	protected Long purchaseidGreaterThanOrEqual;
	protected Long purchaseidLessThanOrEqual;
	protected List<Long> purchaseids;
	protected String content;
	protected String contentLike;
	protected List<String> contents;
	protected String specification;
	protected String specificationLike;
	protected List<String> specifications;
	protected Double quantity;
	protected Double quantityGreaterThanOrEqual;
	protected Double quantityLessThanOrEqual;
	protected List<Double> quantitys;
	protected Double referenceprice;
	protected Double referencepriceGreaterThanOrEqual;
	protected Double referencepriceLessThanOrEqual;
	protected List<Double> referenceprices;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<Date> createDates;
	protected Date updateDate;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected List<Date> updateDates;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;

	public PurchaseitemQuery() {

	}

	public Long getPurchaseid() {
		return purchaseid;
	}

	public Long getPurchaseidGreaterThanOrEqual() {
		return purchaseidGreaterThanOrEqual;
	}

	public Long getPurchaseidLessThanOrEqual() {
		return purchaseidLessThanOrEqual;
	}

	public List<Long> getPurchaseids() {
		return purchaseids;
	}

	public String getContent() {
		return content;
	}

	public String getContentLike() {
		if (contentLike != null && contentLike.trim().length() > 0) {
			if (!contentLike.startsWith("%")) {
				contentLike = "%" + contentLike;
			}
			if (!contentLike.endsWith("%")) {
				contentLike = contentLike + "%";
			}
		}
		return contentLike;
	}

	public List<String> getContents() {
		return contents;
	}

	public String getSpecification() {
		return specification;
	}

	public String getSpecificationLike() {
		if (specificationLike != null && specificationLike.trim().length() > 0) {
			if (!specificationLike.startsWith("%")) {
				specificationLike = "%" + specificationLike;
			}
			if (!specificationLike.endsWith("%")) {
				specificationLike = specificationLike + "%";
			}
		}
		return specificationLike;
	}

	public List<String> getSpecifications() {
		return specifications;
	}

	public Double getQuantity() {
		return quantity;
	}

	public Double getQuantityGreaterThanOrEqual() {
		return quantityGreaterThanOrEqual;
	}

	public Double getQuantityLessThanOrEqual() {
		return quantityLessThanOrEqual;
	}

	public List<Double> getQuantitys() {
		return quantitys;
	}

	public Double getReferenceprice() {
		return referenceprice;
	}

	public Double getReferencepriceGreaterThanOrEqual() {
		return referencepriceGreaterThanOrEqual;
	}

	public Double getReferencepriceLessThanOrEqual() {
		return referencepriceLessThanOrEqual;
	}

	public List<Double> getReferenceprices() {
		return referenceprices;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public List<Date> getCreateDates() {
		return createDates;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public List<Date> getUpdateDates() {
		return updateDates;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getUpdateByLike() {
		if (updateByLike != null && updateByLike.trim().length() > 0) {
			if (!updateByLike.startsWith("%")) {
				updateByLike = "%" + updateByLike;
			}
			if (!updateByLike.endsWith("%")) {
				updateByLike = updateByLike + "%";
			}
		}
		return updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public void setPurchaseid(Long purchaseid) {
		this.purchaseid = purchaseid;
	}

	public void setPurchaseidGreaterThanOrEqual(
			Long purchaseidGreaterThanOrEqual) {
		this.purchaseidGreaterThanOrEqual = purchaseidGreaterThanOrEqual;
	}

	public void setPurchaseidLessThanOrEqual(Long purchaseidLessThanOrEqual) {
		this.purchaseidLessThanOrEqual = purchaseidLessThanOrEqual;
	}

	public void setPurchaseids(List<Long> purchaseids) {
		this.purchaseids = purchaseids;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public void setSpecificationLike(String specificationLike) {
		this.specificationLike = specificationLike;
	}

	public void setSpecifications(List<String> specifications) {
		this.specifications = specifications;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public void setQuantityGreaterThanOrEqual(Double quantityGreaterThanOrEqual) {
		this.quantityGreaterThanOrEqual = quantityGreaterThanOrEqual;
	}

	public void setQuantityLessThanOrEqual(Double quantityLessThanOrEqual) {
		this.quantityLessThanOrEqual = quantityLessThanOrEqual;
	}

	public void setQuantitys(List<Double> quantitys) {
		this.quantitys = quantitys;
	}

	public void setReferenceprice(Double referenceprice) {
		this.referenceprice = referenceprice;
	}

	public void setReferencepriceGreaterThanOrEqual(
			Double referencepriceGreaterThanOrEqual) {
		this.referencepriceGreaterThanOrEqual = referencepriceGreaterThanOrEqual;
	}

	public void setReferencepriceLessThanOrEqual(
			Double referencepriceLessThanOrEqual) {
		this.referencepriceLessThanOrEqual = referencepriceLessThanOrEqual;
	}

	public void setReferenceprices(List<Double> referenceprices) {
		this.referenceprices = referenceprices;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setCreateDates(List<Date> createDates) {
		this.createDates = createDates;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public void setUpdateDates(List<Date> updateDates) {
		this.updateDates = updateDates;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public PurchaseitemQuery purchaseid(Long purchaseid) {
		if (purchaseid == null) {
			throw new RuntimeException("purchaseid is null");
		}
		this.purchaseid = purchaseid;
		return this;
	}

	public PurchaseitemQuery purchaseidGreaterThanOrEqual(
			Long purchaseidGreaterThanOrEqual) {
		if (purchaseidGreaterThanOrEqual == null) {
			throw new RuntimeException("purchaseid is null");
		}
		this.purchaseidGreaterThanOrEqual = purchaseidGreaterThanOrEqual;
		return this;
	}

	public PurchaseitemQuery purchaseidLessThanOrEqual(
			Long purchaseidLessThanOrEqual) {
		if (purchaseidLessThanOrEqual == null) {
			throw new RuntimeException("purchaseid is null");
		}
		this.purchaseidLessThanOrEqual = purchaseidLessThanOrEqual;
		return this;
	}

	public PurchaseitemQuery purchaseids(List<Long> purchaseids) {
		if (purchaseids == null) {
			throw new RuntimeException("purchaseids is empty ");
		}
		this.purchaseids = purchaseids;
		return this;
	}

	public PurchaseitemQuery content(String content) {
		if (content == null) {
			throw new RuntimeException("content is null");
		}
		this.content = content;
		return this;
	}

	public PurchaseitemQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public PurchaseitemQuery contents(List<String> contents) {
		if (contents == null) {
			throw new RuntimeException("contents is empty ");
		}
		this.contents = contents;
		return this;
	}

	public PurchaseitemQuery specification(String specification) {
		if (specification == null) {
			throw new RuntimeException("specification is null");
		}
		this.specification = specification;
		return this;
	}

	public PurchaseitemQuery specificationLike(String specificationLike) {
		if (specificationLike == null) {
			throw new RuntimeException("specification is null");
		}
		this.specificationLike = specificationLike;
		return this;
	}

	public PurchaseitemQuery specifications(List<String> specifications) {
		if (specifications == null) {
			throw new RuntimeException("specifications is empty ");
		}
		this.specifications = specifications;
		return this;
	}

	public PurchaseitemQuery quantity(Double quantity) {
		if (quantity == null) {
			throw new RuntimeException("quantity is null");
		}
		this.quantity = quantity;
		return this;
	}

	public PurchaseitemQuery quantityGreaterThanOrEqual(
			Double quantityGreaterThanOrEqual) {
		if (quantityGreaterThanOrEqual == null) {
			throw new RuntimeException("quantity is null");
		}
		this.quantityGreaterThanOrEqual = quantityGreaterThanOrEqual;
		return this;
	}

	public PurchaseitemQuery quantityLessThanOrEqual(
			Double quantityLessThanOrEqual) {
		if (quantityLessThanOrEqual == null) {
			throw new RuntimeException("quantity is null");
		}
		this.quantityLessThanOrEqual = quantityLessThanOrEqual;
		return this;
	}

	public PurchaseitemQuery quantitys(List<Double> quantitys) {
		if (quantitys == null) {
			throw new RuntimeException("quantitys is empty ");
		}
		this.quantitys = quantitys;
		return this;
	}

	public PurchaseitemQuery referenceprice(Double referenceprice) {
		if (referenceprice == null) {
			throw new RuntimeException("referenceprice is null");
		}
		this.referenceprice = referenceprice;
		return this;
	}

	public PurchaseitemQuery referencepriceGreaterThanOrEqual(
			Double referencepriceGreaterThanOrEqual) {
		if (referencepriceGreaterThanOrEqual == null) {
			throw new RuntimeException("referenceprice is null");
		}
		this.referencepriceGreaterThanOrEqual = referencepriceGreaterThanOrEqual;
		return this;
	}

	public PurchaseitemQuery referencepriceLessThanOrEqual(
			Double referencepriceLessThanOrEqual) {
		if (referencepriceLessThanOrEqual == null) {
			throw new RuntimeException("referenceprice is null");
		}
		this.referencepriceLessThanOrEqual = referencepriceLessThanOrEqual;
		return this;
	}

	public PurchaseitemQuery referenceprices(List<Double> referenceprices) {
		if (referenceprices == null) {
			throw new RuntimeException("referenceprices is empty ");
		}
		this.referenceprices = referenceprices;
		return this;
	}

	public PurchaseitemQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public PurchaseitemQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public PurchaseitemQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public PurchaseitemQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public PurchaseitemQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public PurchaseitemQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public PurchaseitemQuery createDates(List<Date> createDates) {
		if (createDates == null) {
			throw new RuntimeException("createDates is empty ");
		}
		this.createDates = createDates;
		return this;
	}

	public PurchaseitemQuery updateDate(Date updateDate) {
		if (updateDate == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDate = updateDate;
		return this;
	}

	public PurchaseitemQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public PurchaseitemQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public PurchaseitemQuery updateDates(List<Date> updateDates) {
		if (updateDates == null) {
			throw new RuntimeException("updateDates is empty ");
		}
		this.updateDates = updateDates;
		return this;
	}

	public PurchaseitemQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public PurchaseitemQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public PurchaseitemQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("purchaseid".equals(sortColumn)) {
				orderBy = "E.purchaseid" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.content" + a_x;
			}

			if ("specification".equals(sortColumn)) {
				orderBy = "E.specification" + a_x;
			}

			if ("quantity".equals(sortColumn)) {
				orderBy = "E.quantity" + a_x;
			}

			if ("referenceprice".equals(sortColumn)) {
				orderBy = "E.referenceprice" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.createBy" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.createDate" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.updateDate" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.updateBy" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("purchaseitemid", "purchaseitemid");
		addColumn("purchaseid", "purchaseid");
		addColumn("content", "content");
		addColumn("specification", "specification");
		addColumn("quantity", "quantity");
		addColumn("referenceprice", "referenceprice");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

}