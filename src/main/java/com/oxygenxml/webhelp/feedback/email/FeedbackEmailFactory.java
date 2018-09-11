package com.oxygenxml.webhelp.feedback.email;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.oxygenxml.webhelp.feedback.event.FeedbackEventType;
import com.oxygenxml.webhelp.feedback.event.IFeedbackEvent;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Factory for {@link FeedbackEmail} objects.
 */
public class FeedbackEmailFactory {
	/**
	 * Configuration settings bean for template loading service.
	 */
	@Autowired
	private Configuration freemarkerConfig;
	
	/**
	 * Convert a feedback event to an email.
	 * 
	 * 
	 * @param feedbackEvent The feedback event to analyze.
	 * @return The new created feedback email.
	 * @throws IOException	If the template wasn't found or could not be read.
	 * @throws TemplateException If the template could not be rendered.
	 */
	public FeedbackEmail getFeedbackEmail(IFeedbackEvent feedbackEvent) throws IOException, TemplateException {
		FeedbackEventType eventType = feedbackEvent.getType();
		FeedbackEmail feedbackEmail = new FeedbackEmail();
		Template t;
		switch (eventType) {
			case NEW_MESSAGE_WAS_ADDED:
				t = freemarkerConfig.getTemplate("new-message-template.ftl");
				break;
			case MESSAGE_APPROVED:
				t = freemarkerConfig.getTemplate("message-approved-template.ftl");
				break;
			default:
				t = freemarkerConfig.getTemplate("email-template.ftl");
				break;
		}
		String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, feedbackEmail.getModel());
		feedbackEmail.setTemplateName(html);
		return feedbackEmail;
	}
}
