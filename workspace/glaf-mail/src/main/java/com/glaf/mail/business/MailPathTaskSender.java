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

package com.glaf.mail.business;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.glaf.core.base.DataFile;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.BlobItemEntity;
import com.glaf.core.service.IBlobService;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.UUID32;
import com.glaf.core.util.ZipUtils;
import com.glaf.template.util.TemplateUtils;
import com.glaf.mail.MailMessage;
import com.glaf.mail.MailSender;
import com.glaf.mail.domain.MailPathSender;
import com.glaf.mail.service.IMailPathSenderService;

public class MailPathTaskSender {
	protected static final Log logger = LogFactory
			.getLog(MailPathTaskSender.class);

	/**
	 * 获取报表的邮件信息
	 * 
	 * @param id
	 * @return
	 */
	public MailMessage getMailTaskMailMessage(MailPathSender mailTask) {
		if (mailTask != null) {
			MailMessage mailMessage = new MailMessage();
			String subject = mailTask.getTextTitle();
			String content = mailTask.getTextContent();
			if (subject == null) {
				subject = mailTask.getSubject();
			}
			if (content == null) {
				content = mailTask.getSubject();
			}

			Map<String, Object> contextMap = SystemConfig.getContextMap();

			String json = mailTask.getJsonParameter();
			if (StringUtils.isNotEmpty(json)) {
				Map<String, Object> jsonMap = JsonUtils.decode(json);
				if (jsonMap != null && !jsonMap.isEmpty()) {
					Set<Entry<String, Object>> entrySet = jsonMap.entrySet();
					for (Entry<String, Object> entry : entrySet) {
						String key = entry.getKey();
						Object value = entry.getValue();
						if (!contextMap.containsKey(key)) {
							contextMap.put(key, value);
						}
					}
				}
			}

			subject = TemplateUtils.process(contextMap, subject);
			content = TemplateUtils.process(contextMap, content);

			mailMessage.setEncoding("GBK");
			mailMessage.setTo(StringTools.splitToArray(
					mailTask.getMailRecipient(), ","));
			mailMessage.setMailId(UUID32.getUUID());
			mailMessage.setMessageId(UUID32.getUUID());
			mailMessage.setSaveMessage(true);
			mailMessage.setSubject(subject);
			mailMessage.setContent(content);
			Collection<Object> dataFiles = new java.util.ArrayList<Object>();
			try {
				if (mailTask.getMailFilePath() != null) {
					logger.debug("mail send path:" + mailTask.getMailFilePath());
					List<String> mailPaths = StringTools.split(mailTask
							.getMailFilePath());
					for (String path : mailPaths) {
						File directory = new File(path);
						long totalSize = 0;
						this.readFiles(totalSize, mailTask, path, directory,
								dataFiles);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			logger.debug("附件数量：" + dataFiles.size());

			// mailMessage.setDataFiles(dataFiles);
			mailMessage.setFiles(dataFiles);

			return mailMessage;
		}
		return null;
	}

	/**
	 * 递归读取目录文件
	 * 
	 * @param totalSize
	 * @param mailTask
	 * @param directory
	 * @param dataFiles
	 */
	public void readFiles(long totalSize, MailPathSender mailTask, String root,
			File directory, Collection<Object> dataFiles) {
		if (directory.exists() && directory.isDirectory()) {
			IBlobService blobService = ContextFactory.getBean("blobService");
			byte[] bytes = null;
			File[] entries = directory.listFiles();
			for (int i = 0; i < entries.length; i++) {
				File file = entries[i];
				if (file.exists() && file.isFile()) {
					if (file.getName().equals("mail.properties")) {
						continue;
					}
					if (file.getName().equals("hibernate.properties")) {
						continue;
					}
					if (file.getName().endsWith("jdbc.properties")) {
						continue;
					}
					String ext = FileUtils.getFileExt(file.getName());
					if (StringUtils.isNotEmpty(mailTask.getExcludes())) {
						if (StringUtils.containsIgnoreCase(
								mailTask.getExcludes(), ext)) {
							continue;
						}
					}
					if (StringUtils.isNotEmpty(mailTask.getIncludes())) {
						if (!StringUtils.containsIgnoreCase(
								mailTask.getIncludes(), ext)) {
							continue;
						}
					}
					if (blobService.getBlobByFilename(file.getAbsolutePath()) == null) {
						if (totalSize < 1024 * 1024 * mailTask.getSize()
								&& totalSize < 1024 * 1024 * 50) {
							bytes = FileUtils.getBytes(file);
							if (bytes != null) {
								totalSize = totalSize + bytes.length;
								DataFile dataFile = new BlobItemEntity();
								dataFile.setServiceKey("MAIL");
								dataFile.setFileId(file.getAbsolutePath());
								dataFile.setLastModified(file.lastModified());
								dataFile.setFilename(file.getName());
								dataFile.setName(file.getName());
								dataFile.setData(bytes);
								String dir = file.getAbsolutePath();
								dir = StringTools.replace(dir, root, "");
								dataFile.setPath(dir);
								logger.debug("添加附件：" + dataFile.getFilename());
								dataFiles.add(dataFile);
							}
						}
					}
				} else {
					if (file.exists() && file.isDirectory()) {
						this.readFiles(totalSize, mailTask, root, file,
								dataFiles);
					}
				}
			}
		}
	}

	/**
	 * 获取报表的邮件信息
	 * 
	 * @param id
	 * @return
	 */
	public MailMessage getMailTaskMailMessage(String id) {
		IMailPathSenderService mailPathSenderService = ContextFactory
				.getBean("mailPathSenderService");
		MailPathSender mailTask = mailPathSenderService.getMailPathSender(id);
		return this.getMailTaskMailMessage(mailTask);
	}

	/**
	 * 获取报表的邮件信息
	 * 
	 * @param taskId
	 * @return
	 */
	public MailMessage getMailTaskMailMessageByTaskId(String taskId) {
		IMailPathSenderService mailPathSenderService = ContextFactory
				.getBean("mailPathSenderService");
		MailPathSender mailTask = mailPathSenderService
				.getMailPathSenderByTaskId(taskId);
		return this.getMailTaskMailMessage(mailTask);
	}

	public void sendMail(MailPathSender mailTask) {
		MailSender mailSender = (MailSender) ContextFactory
				.getBean("mailSender");
		IBlobService blobService = ContextFactory.getBean("blobService");
		try {
			MailMessage mailMessage = this.getMailTaskMailMessage(mailTask);
			Collection<Object> dataFiles = mailMessage.getFiles();
			List<DataFile> files = new java.util.ArrayList<DataFile>();
			if (StringUtils.equalsIgnoreCase(mailTask.getCompressFlag(), "1")) {
				Map<String, byte[]> bytesMap = new java.util.HashMap<String, byte[]>();
				if (dataFiles != null && !dataFiles.isEmpty()) {
					for (Object obj : dataFiles) {
						if (obj instanceof DataFile) {
							DataFile blobData = (DataFile) obj;
							bytesMap.put(blobData.getPath(), blobData.getData());
							files.add(blobData);
						}
					}
				}
				if (!bytesMap.isEmpty()) {
					byte[] bytes = ZipUtils.toZipBytes(bytesMap);
					dataFiles = new java.util.ArrayList<Object>();
					DataFile blobData = new BlobItemEntity();
					blobData.setFilename(DateUtils.getDateTime(new Date())
							+ ".zip");
					blobData.setName(blobData.getFilename());
					blobData.setData(bytes);
					dataFiles.add(blobData);
					mailMessage.setFiles(dataFiles);
				}
			}

			mailSender.send(mailMessage);

			if (!files.isEmpty()) {
				for (DataFile dataFile : files) {
					dataFile.setData(null);
					dataFile.setFilename(dataFile.getFileId());
				}
				try {
					blobService.saveAll(files);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("发送邮件失败！", ex);
		}
	}

	public void sendMail(String id) {
		try {
			IMailPathSenderService mailPathSenderService = ContextFactory
					.getBean("mailPathSenderService");
			MailPathSender mailTask = mailPathSenderService
					.getMailPathSender(id);
			this.sendMail(mailTask);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("发送邮件失败！", ex);
		}
	}

	public void sendMailByTaskId(String taskId) {
		try {
			IMailPathSenderService mailPathSenderService = ContextFactory
					.getBean("mailPathSenderService");
			MailPathSender mailTask = mailPathSenderService
					.getMailPathSenderByTaskId(taskId);
			this.sendMail(mailTask);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("发送邮件失败！", ex);
		}
	}

}