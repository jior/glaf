/*
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jpage.core.query.paging;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Paging implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * 空白页
     */
    public final static Paging EMPTY_PAGE = new Paging(Collections.EMPTY_LIST,
            0, 0, 0, false);

    /**
     * 结果集
     */
    private List rows = Collections.EMPTY_LIST;

    /**
     * 开始位置
     */
    private int start;

    /**
     * 一页显示的条数
     */
    private int pageSize;

    /**
     * 总记录条数
     */
    private int totalRecordCount;

    /**
     * 是否有下页
     */
    private boolean hasNext;

    public Paging() {

    }

    public Paging(List rows, int start, int pageSize, int totalRecordCount,
            boolean hasNext) {
        this.rows = rows;
        this.start = start;
        this.hasNext = hasNext;
        this.pageSize = pageSize;
        this.totalRecordCount = totalRecordCount;
    }

    /**
     * 得到当前的页码
     * 
     * @return int
     */
    public int getCurrentPage() {
        int currentPage = 1;
        if (totalRecordCount != 0 && start != 0) {
            currentPage = (start - 1) / pageSize + 1;
        }
        return currentPage;
    }

    /**
     * 得到总页数
     * 
     * @return
     */
    public int getTotalPage() {
        int last = 1;
        if (totalRecordCount != 0) {
            if (totalRecordCount % pageSize == 0) {
                last = totalRecordCount / pageSize;
            } else {
                last = totalRecordCount / pageSize + 1;
            }
        }
        return last;
    }

    /**
     * 得到下页在第几条记录开始
     * 
     * @return int
     */
    public int getStartOfNextPage() {
        return start + rows.size();
    }

    /**
     * 得到前页在第几条记录开始
     * 
     * @return int
     */
    public int getStartOfPreviousPage() {
        return Math.max(start - pageSize, 1);
    }

    /**
     * 得到列表的的第一页的开始位置
     * 
     * @return
     */
    public int getFirstPage() {
        return 1;
    }

    /**
     * 得到列表的的最后一页的开始位置
     * 
     * @return
     */
    public int getLastPage() {
        int last = 1;
        if (totalRecordCount > pageSize) {
            if (totalRecordCount % pageSize == 0) {
                last = totalRecordCount - pageSize + 1;
            } else {
                last = (totalRecordCount / pageSize) * pageSize + 1;
            }
        }
        return last;
    }

    /**
     * 获取记录集
     * 
     * @return
     */
    public List getRows() {
        if (rows == null) {
            rows = Collections.EMPTY_LIST;
        }
        return rows;
    }

    /**
     * 获取开始位置
     * 
     * @return
     */
    public int getStart() {
        if (start > 1) {
            return start;
        } else {
            return 1;
        }
    }

    /**
     * 是否有下一页
     * 
     * @return
     */
    public boolean hasNextPage() {
        return hasNext;
    }

    /**
     * 是否有上一页
     * 
     * @return
     */
    public boolean hasPreviousPage() {
        return start > 1;
    }

    /**
     * 获取记录集大小
     * 
     * @return
     */
    public int getRowsSize() {
        return rows != null ? rows.size() : 0;
    }

    /**
     * 获取分页条数
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取全部记录条数
     * 
     * @return
     */
    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    /**
     * @param pageSize
     *            分页记录条数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @param rows
     *            记录集
     */
    public void setRows(List rows) {
        this.rows = rows;
    }

    /**
     * @param start
     *            记录开始位置
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @param totalRecordCount
     *            全部记录条数
     */
    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }
}