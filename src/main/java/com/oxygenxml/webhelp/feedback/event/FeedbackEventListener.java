package com.oxygenxml.webhelp.feedback.event;

/**
 * Listener for {@link IFeedbackEvent} events like: new comment was added, comment approved and others.
 */
public interface FeedbackEventListener {
	
	/**
	 * Handle a new feedback event. 
	 * 
	 * @param event The feedback event.
	 */
	void handleFeedbackEvent(IFeedbackEvent event);
}
