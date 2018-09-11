package com.oxygenxml.webhelp.feedback.email;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * Configuration for {@link JavaMailSender}.
 */
@Configuration
@PropertySource("file:${mail.config.path}/mail.properties")
public class EmailServerConfiguration {	
	/**
	 * Logger for logging.
	 */
	Logger logger = LoggerFactory.getLogger(EmailServerConfiguration.class);
	 
	/**
	 * Mail protocol
	 */
	@Value("${mail.store.protocol}")
    private String protocol;
	
	/**
	 * Mail host.
	 */
    @Value("${mail.host}")
    private String host;
    
    /**
	 * Mail port.
	 */
    @Value("${spring.mail.port}")
    private int port;
        
    /**
	 * Mail user.
	 */
    @Value("${mail.smtp.user}")
    private String username;
    
    /**
	 * Mail password. 
	 */
    @Value("${mail.smtp.password}")
    private String password;
    
    
    /**
	 * Main configuration path. 
	 */
    @Value("${mail.config.path}")
    private String mailConfigurationPath;
    
    /**
     * Configures the {@link JavaMailSender} from an external configuration file
     * @return {@link JavaMailSender}
     */
	@Bean
	JavaMailSender getJavaMailSender() {
		JavaMailSenderImpl mailSender = null;
		
		//try-with-resources statement 
		try (InputStream resourceAsStream = new FileInputStream(mailConfigurationPath + "/mail.properties")) {
			
			mailSender = new JavaMailSenderImpl();
			Properties mailProperties = new Properties();
			mailProperties.load(resourceAsStream);
			
			if (logger.isDebugEnabled()) {
				logger.debug(String.format("Mail properties: %s", mailProperties));
			}
			
			mailSender.setJavaMailProperties(mailProperties);
			mailSender.setProtocol(protocol);
			mailSender.setHost(host);
			mailSender.setPort(port);
			mailSender.setUsername(username);
			mailSender.setPassword(password);
			
		} catch (IOException e) {
			throw new BeanCreationException("Cannot read mail properties.", e);
		}
		
        return mailSender;
	}
}
