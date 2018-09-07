package com.oxygenxml.webhelp.feedback.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

/**
 * Configuration class that sets the path to the template
 */
public class FreemarkerConfig {

	/**
	 * Logger for logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FreemarkerConfig.class);
	
	/**
	 * The path to FreeMarker templates.
	 */
	@Value("${feedback.freemarker.templates.path}")
	private String templatesPath;
	
	/**
	 * Sets the path from where to load the templates
	 * @return bean
	 */
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		
		if (logger.isDebugEnabled()) { 
			logger.debug(String.format("Feedback email templates path %s: ", templatesPath));
		}
		
		if (templatesPath == null) {
			// Set default value
			templatesPath = "file:templates/";
		} else {
			templatesPath = "file:" + templatesPath;
		}
		
		bean.setTemplateLoaderPath(templatesPath);
		return bean;
	}
}