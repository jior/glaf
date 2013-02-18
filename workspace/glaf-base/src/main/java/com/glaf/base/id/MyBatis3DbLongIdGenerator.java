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

package com.glaf.base.id;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.base.entity.EntityDAO;

public class MyBatis3DbLongIdGenerator implements LongIdGenerator {
	protected final static Log logger = LogFactory
			.getLog(MyBatis3DbLongIdGenerator.class);

	protected long nextId = 0;

	protected long lastId = -1;

	protected EntityDAO entityDAO;

	public MyBatis3DbLongIdGenerator() {
		logger.info("----------------MyBatis3DbLongIdGenerator--------------");
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

	public synchronized Long getNextId() {
		return Long.valueOf(this.nextId());
	}

	protected synchronized long nextId() {
		if (lastId < nextId) {
			getNewBlock();
		}
		return nextId++;
	}

	public synchronized void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}
}
