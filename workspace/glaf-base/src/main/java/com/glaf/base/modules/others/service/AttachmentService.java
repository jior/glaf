package com.glaf.base.modules.others.service;

import java.util.List;
import java.util.Map;

import com.glaf.base.modules.others.model.Attachment;

public interface AttachmentService {

	/**
	 * 保存
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	boolean create(Attachment bean);

	/**
	 * 更新
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	boolean update(Attachment bean);

	/**
	 * 删除
	 * 
	 * @param bean
	 *            Attachment
	 * @return boolean
	 */
	boolean delete(Attachment bean);

	/**
	 * 删除
	 * 
	 * @param id
	 *            int
	 * @return boolean
	 */
	boolean delete(long id);

	/**
	 * 批量删除
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteAll(long[] id);

	/**
	 * 获取对象
	 * 
	 * @param id
	 * @return
	 */
	Attachment find(long id);

	/**
	 * 返回所有附件列表
	 * 
	 * @param parent
	 * @return
	 */
	List getAttachmentList(long referId, int referType);

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long referId, int referType);

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @return Attachment
	 */
	Attachment find(long id, long referId, int referType);

	/**
	 * 返回附件
	 * 
	 * @param referId
	 * @param referType
	 * @param name
	 * @return Attachment
	 */
	Attachment find(long referId, int referType, String name);

	/**
	 * 返回所有附件名称列表
	 * 
	 * @param parent
	 * @return
	 */
	Map getNameMap(long referId, int referType);

	/**
	 * 返回所有附件列表
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	List getAttachmentList(long[] referIds, int referType);

	/**
	 * 返回附件个数
	 * 
	 * @param referId
	 * @param referType
	 * @return
	 */
	int getAttachmentCount(long[] referIds, int referType);

	/**
	 * 返回对应的附件列表
	 * 
	 * @param parent
	 * @return
	 */
	List getPurchaseAttachmentList(long[] ids, int referType);
}
