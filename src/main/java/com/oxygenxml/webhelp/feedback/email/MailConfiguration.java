package com.oxygenxml.webhelp.feedback.email;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.util.ClassUtils;

/**
 * Configuration for {@link JavaMailSender}.
 */
@Configuration
@PropertySource("classpath:mail.properties")
public class MailConfiguration {
	
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
    
    
    
	@Bean
	JavaMailSender getJavaMailSender() throws BeanCreationException {
		JavaMailSenderImpl mailSender = null;
		try {
			mailSender = new JavaMailSenderImpl();
			InputStream resourceAsStream = ClassUtils.getDefaultClassLoader().getResourceAsStream("mail.properties");
			
			Properties mailProperties = new Properties();
			mailProperties.load(resourceAsStream);
			System.out.println("Mail properties: " + mailProperties);
			
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
