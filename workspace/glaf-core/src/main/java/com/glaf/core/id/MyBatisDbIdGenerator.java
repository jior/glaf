/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.dao.EntityDAO;

public class MyBatisDbIdGenerator implements IdGenerator {
	protected final static Log logger = LogFactory
			.getLog(MyBatisDbIdGenerator.class);

	protected volatile EntityDAO entityDAO;

	protected volatile long lastId = -1;

	protected volatile long nextId = 0;

	public MyBatisDbIdGenerator() {
		logger.info("----------------MyBatis3DbIdGenerator--------------");
	}

	protected synchronized void getNewBlock() {
		IdBlock idBlock = entityDAO.nextDbidBlock();
		this.nextId = idBlock.getNextId();
		this.lastId = idBlock.getLastId();
		if (logger.isDebugEnabled()) {
			logger.debug("----------------NEXTID------------------------");
			logger.debug("nextId:" + nextId);
			logger.debug("lastId:" + lastId);
		}
	}

	public synchronized String getNextId() {
		return Long.toString(this.nextId());
	}

	public synchronized String getNextId(String name) {
		return Long.toString(this.nextId(name));
	}

	public synchronized Long nextId() {
		if (lastId < nextId) {
			getNewBlock();
		}
		return nextId++;
	}

	public synchronized Long nextId(String name) {
		IdBlock idBlock = entityDAO.nextDbidBlock(name);
		return idBlock.getNextId();
	}

	public synchronized void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
}
