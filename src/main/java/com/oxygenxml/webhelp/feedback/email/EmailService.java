package com.oxygenxml.webhelp.feedback.email;


import java.io.IOException;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
/**
 * Email service
 */
@Service
public class EmailService {

	/**
	 * Bean used for sending email
	 */
	@Autowired
	private JavaMailSender emailSender;
	
	/**
	 * Configuration bean
	 */
	@Autowired
	private Configuration freemarkerConfig;
	
	/**
	 * Builds up and sends an email using Freemarker template.
	 * 
	 * @param mail 	The mail object to be sent.
	 * 
	 * @throws MessagingException If message creation failed.
	 * @throws IOException	If the template wasn't found or could not be read.
	 * @throws TemplateException If the template could not be rendered.
	 */
	public void sendMessage(FeedbackEmail mail) throws MessagingException, IOException, TemplateException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		
		//Completes the template's fields using the mail's model.
		Template t = freemarkerConfig.getTemplate(mail.getTemplateName());
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, mail.getModel());
		
		//Fills the message's fields
		helper.setTo(mail.getTo());
		helper.setText(html, true);
		helper.setSubject(mail.getSubject());
		helper.setFrom(mail.getFrom());
		emailSender.send(message);
	}

}