package org.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.max.SimpleMail;
import org.max.vo.HtmlMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class SendEmailUtil {

	private static Logger logger = LoggerFactory.getLogger(SendEmailUtil.class);

	private static MailSender mailSender;

	private static String defaultFrom;

	private static String EMAIL_DEFAULT_SENDER = "solekubi@sina.com";

	public SendEmailUtil() {
		Properties props = new Properties();
		try {
			props.load(SendEmailUtil.class.getClassLoader().getResourceAsStream("email.properties"));
			this.defaultFrom = props.getProperty("mail.user");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.info("email.properties文件找不到");
		}
	}

	public void sendEmail(HtmlMail htmlMail) {
		System.out.println("Sending mail...");
		MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();
		// use the true flag to indicate you need a multipart message
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(htmlMail.getRecipients());
			if (htmlMail.getCc() != null) {
				helper.setCc(htmlMail.getCc());
			}
			if (htmlMail.getBcc() != null) {
				helper.setBcc(htmlMail.getBcc());
			}
			if (StringUtils.isEmpty(htmlMail.getSenderEmail())) {
				if (defaultFrom != null && !defaultFrom.equals("")) {
					helper.setFrom(defaultFrom);
				} else {
					helper.setFrom(EMAIL_DEFAULT_SENDER);
				}
			} else {
				helper.setFrom(htmlMail.getSenderEmail(), htmlMail.getSender());
			}

			Map<String, String> attachments = htmlMail.getAttachments();
			if (null != attachments) {
				for (Iterator<Map.Entry<String, String>> it = attachments.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (null == cid || null == filePath) {
						logger.error("The attachment file undefined.");
						throw new RuntimeException("The attachment file undefined.");
					}
					File file = FileUtils.getFile(filePath);
					String fileName = FilenameUtils.getName(filePath);
					if (!file.exists() && !file.isDirectory()) {
						logger.error("Cannot found the attachment file [ " + filePath + " ].");
						throw new RuntimeException("Cannot found the attachment file [ " + filePath + " ].");
					}
					FileSystemResource fileResource = new FileSystemResource(file);
					helper.addAttachment(fileName, fileResource);
				}
			}

			MimeMultipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			String bodyText = htmlMail.getBodyText();
			Map<String, String> qrCode = htmlMail.getQaCodes();
			if (qrCode != null) {
				for (Iterator<Map.Entry<String, String>> it = qrCode.entrySet().iterator(); it.hasNext();) {
					Map.Entry<String, String> entry = it.next();
					String cid = entry.getKey();
					String filePath = entry.getValue();
					if (bodyText.contains("cid:image")) {
						messageBodyPart.setContent(bodyText, "text/html");
						// add it
						multipart.addBodyPart(messageBodyPart);
						messageBodyPart = new MimeBodyPart();
						File file = FileUtils.getFile(filePath);
						if (!file.exists() && !file.isDirectory()) {
							logger.error("Cannot found the qrCode file [ " + filePath + " ].");
							throw new RuntimeException("Cannot found the qrCode file [ " + filePath + " ].");
						}
						DataSource fds = new FileDataSource(filePath);
						messageBodyPart.setDataHandler(new DataHandler(fds));
						messageBodyPart.setHeader("Content-ID", "<" + cid + ">");
						// add it
						multipart.addBodyPart(messageBodyPart);
						message.setContent(multipart);
					} else {
						helper.setText(bodyText, true);
					}
				}
			} else {
				helper.setText(bodyText, true);
			}

			helper.setSubject(htmlMail.getSubject());
			((JavaMailSenderImpl) mailSender).send(message);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
