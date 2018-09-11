package com.oxygenxml.webhelp.feedback.email;

import com.oxygenxml.webhelp.feedback.event.IFeedbackEvent;

/**
 * Factory for {@link FeedbackEmail} objects.
 */
public class FeedbackEmailFactory {
	
	/**
	 * Convert a feedback event to an email.
	 * 
	 * TODO: instantiate the template 
	 * 
	 * @param feedbackEvent The feedback event to analyze.
	 * @return The new created feedback email.
	 */
	public FeedbackEmail getFeedbackEmail(IFeedbackEvent feedbackEvent) {
		// TODO Auto-generated method stub
		FeedbackEmail feedbackEmail = new FeedbackEmail();
		feedbackEmail.setTemplateName("email-template.ftl");
		return feedbackEmail;
	}
}
