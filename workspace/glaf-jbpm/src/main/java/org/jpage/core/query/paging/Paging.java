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

package org.jpage.core.query.paging;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Paging implements Serializable {
	private static final long serialVersionUID = 1L;
    /**
     * �հ�ҳ
     */
    public final static Paging EMPTY_PAGE = new Paging(Collections.EMPTY_LIST,
            0, 0, 0, false);

    /**
     * �����
     */
    private List rows = Collections.EMPTY_LIST;

    /**
     * ��ʼλ��
     */
    private int start;

    /**
     * һҳ��ʾ������
     */
    private int pageSize;

    /**
     * �ܼ�¼����
     */
    private int totalRecordCount;

    /**
     * �Ƿ�����ҳ
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
     * �õ���ǰ��ҳ��
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
     * �õ���ҳ��
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
     * �õ���ҳ�ڵڼ�����¼��ʼ
     * 
     * @return int
     */
    public int getStartOfNextPage() {
        return start + rows.size();
    }

    /**
     * �õ�ǰҳ�ڵڼ�����¼��ʼ
     * 
     * @return int
     */
    public int getStartOfPreviousPage() {
        return Math.max(start - pageSize, 1);
    }

    /**
     * �õ��б�ĵĵ�һҳ�Ŀ�ʼλ��
     * 
     * @return
     */
    public int getFirstPage() {
        return 1;
    }

    /**
     * �õ��б�ĵ����һҳ�Ŀ�ʼλ��
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
     * ��ȡ��¼��
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
     * ��ȡ��ʼλ��
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
     * �Ƿ�����һҳ
     * 
     * @return
     */
    public boolean hasNextPage() {
        return hasNext;
    }

    /**
     * �Ƿ�����һҳ
     * 
     * @return
     */
    public boolean hasPreviousPage() {
        return start > 1;
    }

    /**
     * ��ȡ��¼����С
     * 
     * @return
     */
    public int getRowsSize() {
        return rows != null ? rows.size() : 0;
    }

    /**
     * ��ȡ��ҳ����
     * 
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * ��ȡȫ����¼����
     * 
     * @return
     */
    public int getTotalRecordCount() {
        return totalRecordCount;
    }

    /**
     * @param pageSize
     *            ��ҳ��¼����
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * @param rows
     *            ��¼��
     */
    public void setRows(List rows) {
        this.rows = rows;
    }

    /**
     * @param start
     *            ��¼��ʼλ��
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * @param totalRecordCount
     *            ȫ����¼����
     */
    public void setTotalRecordCount(int totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }
}