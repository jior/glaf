package com.glaf.base.modules.todo.service;

import java.util.*;
import com.glaf.base.modules.todo.model.TMembership;

public interface MembershipService {

	/**
	 * ��ȡĳ����Ա���¼�
	 * 
	 * @param superiorId
	 * @return
	 */
	List<TMembership> getChildMemberships(String superiorId);

	/**
	 * ��ȡĳ����Ա���ϼ�
	 * 
	 * @param actorId
	 * @return
	 */
	List<TMembership> getMemberships(String actorId);

	/**
	 * ����ĳ����Ա���ϼ�
	 * 
	 * @param actorId
	 * @param memberships
	 */
	void saveMembership(String actorId, List<TMembership> memberships);

}
