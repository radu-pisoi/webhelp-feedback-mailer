package com.oxygenxml.webhelp.feedback.email;

/**
 * Options for Feedback Email Notification module.
 */
public final class FeedbackMailOptions {
	
	/**
	 * Option for FreeMarker templates path.
	 */
	public static final String FREEMARKER_TEMPLATES_PATH = "feedback.freemarker.templates.path";
	
	/**
	 * Default private constructor.
	 */
	 private FeedbackMailOptions() {
		 throw new IllegalStateException("Utility class");
	 }
}
