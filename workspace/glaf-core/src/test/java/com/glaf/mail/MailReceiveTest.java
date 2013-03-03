package com.glaf.mail;

import java.util.List;

import org.junit.Test;
import com.glaf.test.AbstractTest;
import com.glaf.core.util.Paging;
import com.glaf.mail.Mail;
import com.glaf.mail.business.ReceiveMailBean;
import com.glaf.mail.query.MailQuery;
import com.glaf.mail.service.IMailService;

public class MailReceiveTest extends AbstractTest {

	protected IMailService mailService;

	@Test
	public void testList() {
		mailService = super.getBean("mailService");
		ReceiveMailBean rm = new ReceiveMailBean();
		rm.receiveMail("pop3.163.com", 110, "cinsoft2013@163.com",
				"cinsoft@2013", "/temp");
		MailQuery query = new MailQuery();
		Paging page = mailService.getPage(query);
		List<Object> rows = page.getRows();
		for (Object row : rows) {
			Mail mail = (Mail) row;
			logger.debug(mail.toJsonObject().toJSONString());
		}
	}

}
