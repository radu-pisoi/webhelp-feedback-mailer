package com.oxygenxml.webhelp.feedback.email;

import java.io.IOException;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.oxygenxml.webhelp.feedback.event.IFeedbackEvent;
import com.oxygenxml.webhelp.feedback.event.IFeedbackEventListener;

import freemarker.template.TemplateException;

/**
 * Send email notifications for various feedback events.
 */
public class FeedbackEmailNotifier implements IFeedbackEventListener {
	/**
	 * Logger for logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FeedbackEmailNotifier.class);

	/**
	 * Email service.
	 */
	@Autowired
	private EmailService emailService;
	
	/**
	 * Email service.
	 */
	@Autowired
	private FeedbackEmailFactory feedbackEmailFactory;


	@Override
	public void handleFeedbackEvent(IFeedbackEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Handle feedback event %s: ", event));
		}

		try {
			FeedbackEmail feedbackEmail = feedbackEmailFactory.getFeedbackEmail(event);
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("feedback email %s: ", feedbackEmail));
			}
			emailService.sendMessage(feedbackEmail);
		} catch (MessagingException | IOException | TemplateException e) {
			logger.error("Cannot send message. Cause: " + e.getMessage(), e);
		}
	}
}
