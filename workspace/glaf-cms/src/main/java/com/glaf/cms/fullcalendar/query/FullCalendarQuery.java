package com.glaf.cms.fullcalendar.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FullCalendarQuery extends DataQuery {
	private static final long serialVersionUID = 1L;

	protected String titleLike;

	protected String contentLike;

	protected String addressLike;

	protected String remarkLike;

	protected Integer shareFlag;

	protected Integer status;

	protected Date dateStartGreaterThanOrEqual;
	protected Date dateStartLessThanOrEqual;
	protected Date dateEndGreaterThanOrEqual;
	protected Date dateEndLessThanOrEqual;
	protected String ext1;
	protected String ext1Like;

	protected String ext2;
	protected String ext2Like;

	protected String createBy;
	protected String createByLike;
	protected String createByNot;
	protected List<String> createBys;

	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	protected String updateBy;
	protected String updateByLike;

	protected List<String> updateBys;

	public FullCalendarQuery() {

	}

	public FullCalendarQuery addressLike(String addressLike) {
		if (addressLike == null) {
			throw new RuntimeException("address is null");
		}
		this.addressLike = addressLike;
		return this;
	}

	public FullCalendarQuery contentLike(String contentLike) {
		if (contentLike == null) {
			throw new RuntimeException("content is null");
		}
		this.contentLike = contentLike;
		return this;
	}

	public FullCalendarQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public FullCalendarQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public FullCalendarQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FullCalendarQuery createDate(Date createDate) {
		if (createDate == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDate = createDate;
		return this;
	}

	public FullCalendarQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FullCalendarQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FullCalendarQuery dateEndGreaterThanOrEqual(
			Date dateEndGreaterThanOrEqual) {
		if (dateEndGreaterThanOrEqual == null) {
			throw new RuntimeException("dateEnd is null");
		}
		this.dateEndGreaterThanOrEqual = dateEndGreaterThanOrEqual;
		return this;
	}

	public FullCalendarQuery dateEndLessThanOrEqual(Date dateEndLessThanOrEqual) {
		if (dateEndLessThanOrEqual == null) {
			throw new RuntimeException("dateEnd is null");
		}
		this.dateEndLessThanOrEqual = dateEndLessThanOrEqual;
		return this;
	}

	public FullCalendarQuery dateStartGreaterThanOrEqual(
			Date dateStartGreaterThanOrEqual) {
		if (dateStartGreaterThanOrEqual == null) {
			throw new RuntimeException("dateStart is null");
		}
		this.dateStartGreaterThanOrEqual = dateStartGreaterThanOrEqual;
		return this;
	}

	public FullCalendarQuery dateStartLessThanOrEqual(
			Date dateStartLessThanOrEqual) {
		if (dateStartLessThanOrEqual == null) {
			throw new RuntimeException("dateStart is null");
		}
		this.dateStartLessThanOrEqual = dateStartLessThanOrEqual;
		return this;
	}

	public FullCalendarQuery ext1(String ext1) {
		if (ext1 == null) {
			throw new RuntimeException("ext1 is null");
		}
		this.ext1 = ext1;
		return this;
	}

	public FullCalendarQuery ext1Like(String ext1Like) {
		if (ext1Like == null) {
			throw new RuntimeException("ext1 is null");
		}
		this.ext1Like = ext1Like;
		return this;
	}

	public FullCalendarQuery ext2(String ext2) {
		if (ext2 == null) {
			throw new RuntimeException("ext2 is null");
		}
		this.ext2 = ext2;
		return this;
	}

	public FullCalendarQuery ext2Like(String ext2Like) {
		if (ext2Like == null) {
			throw new RuntimeException("ext2 is null");
		}
		this.ext2Like = ext2Like;
		return this;
	}

	public String getAddressLike() {
		if (addressLike != null && addressLike.trim().length() > 0) {
			if (!addressLike.startsWith("%")) {
				addressLike = "%" + addressLike;
			}
			if (!addressLike.endsWith("%")) {
				addressLike = addressLike + "%";
			}
		}
		return addressLike;
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

	public String getCreateByNot() {
		return createByNot;
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

	public Date getDateEndGreaterThanOrEqual() {
		return dateEndGreaterThanOrEqual;
	}

	public Date getDateEndLessThanOrEqual() {
		return dateEndLessThanOrEqual;
	}

	public Date getDateStartGreaterThanOrEqual() {
		return dateStartGreaterThanOrEqual;
	}

	public Date getDateStartLessThanOrEqual() {
		return dateStartLessThanOrEqual;
	}

	public String getExt1() {
		return ext1;
	}

	public String getExt1Like() {
		if (ext1Like != null && ext1Like.trim().length() > 0) {
			if (!ext1Like.startsWith("%")) {
				ext1Like = "%" + ext1Like;
			}
			if (!ext1Like.endsWith("%")) {
				ext1Like = ext1Like + "%";
			}
		}
		return ext1Like;
	}

	public String getExt2() {
		return ext2;
	}

	public String getExt2Like() {
		if (ext2Like != null && ext2Like.trim().length() > 0) {
			if (!ext2Like.startsWith("%")) {
				ext2Like = "%" + ext2Like;
			}
			if (!ext2Like.endsWith("%")) {
				ext2Like = ext2Like + "%";
			}
		}
		return ext2Like;
	}

	public String getMemoLike() {
		if (remarkLike != null && remarkLike.trim().length() > 0) {
			if (!remarkLike.startsWith("%")) {
				remarkLike = "%" + remarkLike;
			}
			if (!remarkLike.endsWith("%")) {
				remarkLike = remarkLike + "%";
			}
		}
		return remarkLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.title" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.content" + a_x;
			}

			if ("address".equals(sortColumn)) {
				orderBy = "E.address" + a_x;
			}

			if ("remark".equals(sortColumn)) {
				orderBy = "E.remark" + a_x;
			}

			if ("shareFlag".equals(sortColumn)) {
				orderBy = "E.shareFlag" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.status" + a_x;
			}

			if ("dateStart".equals(sortColumn)) {
				orderBy = "E.dateStart" + a_x;
			}

			if ("dateEnd".equals(sortColumn)) {
				orderBy = "E.dateEnd" + a_x;
			}

			if ("ext1".equals(sortColumn)) {
				orderBy = "E.ext1" + a_x;
			}

			if ("ext2".equals(sortColumn)) {
				orderBy = "E.ext2" + a_x;
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

	public Integer getShare() {
		return shareFlag;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
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

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("title", "title");
		addColumn("content", "content");
		addColumn("address", "address");
		addColumn("remark", "remark");
		addColumn("shareFlag", "shareFlag");
		addColumn("status", "status");
		addColumn("dateStart", "dateStart");
		addColumn("dateEnd", "dateEnd");
		addColumn("ext1", "ext1");
		addColumn("ext2", "ext2");
		addColumn("createBy", "createBy");
		addColumn("createDate", "createDate");
		addColumn("updateDate", "updateDate");
		addColumn("updateBy", "updateBy");
	}

	public FullCalendarQuery remarkLike(String remarkLike) {
		if (remarkLike == null) {
			throw new RuntimeException("remark is null");
		}
		this.remarkLike = remarkLike;
		return this;
	}

	public void setAddressLike(String addressLike) {
		this.addressLike = addressLike;
	}

	public void setContentLike(String contentLike) {
		this.contentLike = contentLike;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateByNot(String createByNot) {
		this.createByNot = createByNot;
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

	public void setDateEndGreaterThanOrEqual(Date dateEndGreaterThanOrEqual) {
		this.dateEndGreaterThanOrEqual = dateEndGreaterThanOrEqual;
	}

	public void setDateEndLessThanOrEqual(Date dateEndLessThanOrEqual) {
		this.dateEndLessThanOrEqual = dateEndLessThanOrEqual;
	}

	public void setDateStartGreaterThanOrEqual(Date dateStartGreaterThanOrEqual) {
		this.dateStartGreaterThanOrEqual = dateStartGreaterThanOrEqual;
	}

	public void setDateStartLessThanOrEqual(Date dateStartLessThanOrEqual) {
		this.dateStartLessThanOrEqual = dateStartLessThanOrEqual;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public void setExt1Like(String ext1Like) {
		this.ext1Like = ext1Like;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public void setExt2Like(String ext2Like) {
		this.ext2Like = ext2Like;
	}

	public void setMemoLike(String remarkLike) {
		this.remarkLike = remarkLike;
	}

	public void setShare(Integer shareFlag) {
		this.shareFlag = shareFlag;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
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

	public void setUpdateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public FullCalendarQuery shareFlag(Integer shareFlag) {
		if (shareFlag == null) {
			throw new RuntimeException("shareFlag is null");
		}
		this.shareFlag = shareFlag;
		return this;
	}

	public FullCalendarQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public FullCalendarQuery statusGreaterThanOrEqual(
			Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public FullCalendarQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public FullCalendarQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public FullCalendarQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public FullCalendarQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public FullCalendarQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public FullCalendarQuery updateDateGreaterThanOrEqual(
			Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public FullCalendarQuery updateDateLessThanOrEqual(
			Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

}