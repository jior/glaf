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

package com.glaf.core.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.glaf.core.id.Dbid;

@Component
public interface IdMapper {

	Dbid getNextDbId(String name);
	
	long getMaxId(Map<String, Object> params);

	void inertNextDbId(Dbid dbid);

	void updateNextDbId(Dbid dbid);

	List<Dbid> getAllDbids();

}
