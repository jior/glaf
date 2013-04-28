package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SerialNumberQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String moduleNo;
	protected String moduleNoLike;
	protected List<String> moduleNos;
	protected Date lastDateGreaterThanOrEqual;
	protected Date lastDateLessThanOrEqual;
	protected Integer intervelNo;
	protected Integer intervelNoGreaterThanOrEqual;
	protected Integer intervelNoLessThanOrEqual;
	protected Integer currentSerail;
	protected Integer currentSerailGreaterThanOrEqual;
	protected Integer currentSerailLessThanOrEqual;

	public SerialNumberQuery() {

	}

	public String getModuleNo() {
		return moduleNo;
	}

	public String getModuleNoLike() {
		if (moduleNoLike != null && moduleNoLike.trim().length() > 0) {
			if (!moduleNoLike.startsWith("%")) {
				moduleNoLike = "%" + moduleNoLike;
			}
			if (!moduleNoLike.endsWith("%")) {
				moduleNoLike = moduleNoLike + "%";
			}
		}
		return moduleNoLike;
	}

	public List<String> getModuleNos() {
		return moduleNos;
	}

	public Date getLastDateGreaterThanOrEqual() {
		return lastDateGreaterThanOrEqual;
	}

	public Date getLastDateLessThanOrEqual() {
		return lastDateLessThanOrEqual;
	}

	public Integer getIntervelNo() {
		return intervelNo;
	}

	public Integer getIntervelNoGreaterThanOrEqual() {
		return intervelNoGreaterThanOrEqual;
	}

	public Integer getIntervelNoLessThanOrEqual() {
		return intervelNoLessThanOrEqual;
	}

	public Integer getCurrentSerail() {
		return currentSerail;
	}

	public Integer getCurrentSerailGreaterThanOrEqual() {
		return currentSerailGreaterThanOrEqual;
	}

	public Integer getCurrentSerailLessThanOrEqual() {
		return currentSerailLessThanOrEqual;
	}

	public void setModuleNo(String moduleNo) {
		this.moduleNo = moduleNo;
	}

	public void setModuleNoLike(String moduleNoLike) {
		this.moduleNoLike = moduleNoLike;
	}

	public void setModuleNos(List<String> moduleNos) {
		this.moduleNos = moduleNos;
	}

	public void setLastDateGreaterThanOrEqual(Date lastDateGreaterThanOrEqual) {
		this.lastDateGreaterThanOrEqual = lastDateGreaterThanOrEqual;
	}

	public void setLastDateLessThanOrEqual(Date lastDateLessThanOrEqual) {
		this.lastDateLessThanOrEqual = lastDateLessThanOrEqual;
	}

	public void setIntervelNo(Integer intervelNo) {
		this.intervelNo = intervelNo;
	}

	public void setIntervelNoGreaterThanOrEqual(
			Integer intervelNoGreaterThanOrEqual) {
		this.intervelNoGreaterThanOrEqual = intervelNoGreaterThanOrEqual;
	}

	public void setIntervelNoLessThanOrEqual(Integer intervelNoLessThanOrEqual) {
		this.intervelNoLessThanOrEqual = intervelNoLessThanOrEqual;
	}

	public void setCurrentSerail(Integer currentSerail) {
		this.currentSerail = currentSerail;
	}

	public void setCurrentSerailGreaterThanOrEqual(
			Integer currentSerailGreaterThanOrEqual) {
		this.currentSerailGreaterThanOrEqual = currentSerailGreaterThanOrEqual;
	}

	public void setCurrentSerailLessThanOrEqual(
			Integer currentSerailLessThanOrEqual) {
		this.currentSerailLessThanOrEqual = currentSerailLessThanOrEqual;
	}

	public SerialNumberQuery moduleNo(String moduleNo) {
		if (moduleNo == null) {
			throw new RuntimeException("moduleNo is null");
		}
		this.moduleNo = moduleNo;
		return this;
	}

	public SerialNumberQuery moduleNoLike(String moduleNoLike) {
		if (moduleNoLike == null) {
			throw new RuntimeException("moduleNo is null");
		}
		this.moduleNoLike = moduleNoLike;
		return this;
	}

	public SerialNumberQuery moduleNos(List<String> moduleNos) {
		if (moduleNos == null) {
			throw new RuntimeException("moduleNos is empty ");
		}
		this.moduleNos = moduleNos;
		return this;
	}

	public SerialNumberQuery lastDateGreaterThanOrEqual(
			Date lastDateGreaterThanOrEqual) {
		if (lastDateGreaterThanOrEqual == null) {
			throw new RuntimeException("lastDate is null");
		}
		this.lastDateGreaterThanOrEqual = lastDateGreaterThanOrEqual;
		return this;
	}

	public SerialNumberQuery lastDateLessThanOrEqual(
			Date lastDateLessThanOrEqual) {
		if (lastDateLessThanOrEqual == null) {
			throw new RuntimeException("lastDate is null");
		}
		this.lastDateLessThanOrEqual = lastDateLessThanOrEqual;
		return this;
	}

	public SerialNumberQuery intervelNo(Integer intervelNo) {
		if (intervelNo == null) {
			throw new RuntimeException("intervelNo is null");
		}
		this.intervelNo = intervelNo;
		return this;
	}

	public SerialNumberQuery intervelNoGreaterThanOrEqual(
			Integer intervelNoGreaterThanOrEqual) {
		if (intervelNoGreaterThanOrEqual == null) {
			throw new RuntimeException("intervelNo is null");
		}
		this.intervelNoGreaterThanOrEqual = intervelNoGreaterThanOrEqual;
		return this;
	}

	public SerialNumberQuery intervelNoLessThanOrEqual(
			Integer intervelNoLessThanOrEqual) {
		if (intervelNoLessThanOrEqual == null) {
			throw new RuntimeException("intervelNo is null");
		}
		this.intervelNoLessThanOrEqual = intervelNoLessThanOrEqual;
		return this;
	}

	public SerialNumberQuery currentSerail(Integer currentSerail) {
		if (currentSerail == null) {
			throw new RuntimeException("currentSerail is null");
		}
		this.currentSerail = currentSerail;
		return this;
	}

	public SerialNumberQuery currentSerailGreaterThanOrEqual(
			Integer currentSerailGreaterThanOrEqual) {
		if (currentSerailGreaterThanOrEqual == null) {
			throw new RuntimeException("currentSerail is null");
		}
		this.currentSerailGreaterThanOrEqual = currentSerailGreaterThanOrEqual;
		return this;
	}

	public SerialNumberQuery currentSerailLessThanOrEqual(
			Integer currentSerailLessThanOrEqual) {
		if (currentSerailLessThanOrEqual == null) {
			throw new RuntimeException("currentSerail is null");
		}
		this.currentSerailLessThanOrEqual = currentSerailLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("moduleNo".equals(sortColumn)) {
				orderBy = "E.moduleNo" + a_x;
			}

			if ("lastDate".equals(sortColumn)) {
				orderBy = "E.lastDate" + a_x;
			}

			if ("intervelNo".equals(sortColumn)) {
				orderBy = "E.intervelNo" + a_x;
			}

			if ("currentSerail".equals(sortColumn)) {
				orderBy = "E.currentSerail" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("moduleNo", "moduleNo");
		addColumn("lastDate", "lastDate");
		addColumn("intervelNo", "intervelNo");
		addColumn("currentSerail", "currentSerail");
	}

}