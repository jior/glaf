package com.glaf.base.modules.todo.service;

import java.util.*;
import com.glaf.base.modules.todo.model.TMembership;

public interface MembershipService {

	/**
	 * 获取某个成员的下级
	 * 
	 * @param superiorId
	 * @return
	 */
	List<TMembership> getChildMemberships(String superiorId);

	/**
	 * 获取某个成员的上级
	 * 
	 * @param actorId
	 * @return
	 */
	List<TMembership> getMemberships(String actorId);

	/**
	 * 保存某个成员的上级
	 * 
	 * @param actorId
	 * @param memberships
	 */
	void saveMembership(String actorId, List<TMembership> memberships);

}
