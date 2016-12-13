package org.max.vo;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlMail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(HtmlMail.class);

	private String sender;

	private String senderEmail;

	private String[] recipients;

	private String[] cc;

	private String[] bcc;

	private String bodyText;

	private String subject;

	private Map<String, String> attachments;
	
	private Map<String, String> qaCodes;

	private boolean templateEnable = false;

	private String templateName;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		if (cc != null && cc.length > 0) {
			Set<String> set = new HashSet<String>(Arrays.asList(cc));
			String[] array2 = (String[]) (set.toArray(new String[set.size()]));
			this.cc = array2;
		} else {
			this.cc = cc;
		}
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		if (bcc != null && bcc.length > 0) {
			Set<String> set = new HashSet<String>(Arrays.asList(bcc));
			String[] array2 = (String[]) (set.toArray(new String[set.size()]));
			this.bcc = array2;
		} else {
			this.bcc = bcc;
		}
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

	public boolean isTemplateEnable() {
		return templateEnable;
	}

	public void setTemplateEnable(boolean templateEnable) {
		this.templateEnable = templateEnable;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, String> getQaCodes() {
		return qaCodes;
	}

	public void setQaCodes(Map<String, String> qaCodes) {
		this.qaCodes = qaCodes;
	}
 
}
