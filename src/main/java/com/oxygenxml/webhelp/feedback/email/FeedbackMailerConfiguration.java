package com.oxygenxml.webhelp.feedback.email;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Main configuration for mailer module. 
 */
@Configuration
@Import({EmailServerConfiguration.class, FreemarkerConfig.class})
public class FeedbackMailerConfiguration {
	
	/**
	 * @return Creates the factory for {@link FeedbackEmail}.
	 */
	@Bean
	public FeedbackEmailFactory getFeedbackEmailFactory() {
		return new FeedbackEmailFactory();
	}
	
	/**
	 * @return Handle feedback events and convert them to email notifications. 
	 */
	@Bean
	public FeedbackEmailNotifier getFeedbackEmailNotifier() {
		return new FeedbackEmailNotifier();
	}
	
	/**
	 * @return Service for Java emails.
	 */
	@Bean
	public EmailService getEmailService() {
		return new EmailService();
	}
}
