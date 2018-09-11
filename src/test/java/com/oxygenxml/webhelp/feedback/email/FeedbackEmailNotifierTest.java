package com.oxygenxml.webhelp.feedback.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * Test email notifier for various feedback events.
 */
@TestPropertySource("file:src/test/resources/issue-6/application.properties")
@ContextConfiguration(classes = {FeedbackMailerConfiguration.class})
public class FeedbackEmailNotifierTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private FeedbackEmailNotifier notifier;
	
//	@Test
//	public void testHandleNewMessageEvent() {		
//		notifier.handleFeedbackEvent(new IFeedbackEvent() {			
//			@Override
//			public FeedbackEventType getType() {
//				return FeedbackEventType.MESSAGE_APPROVED;
//			}
//		});
//		
//	}
	
}
