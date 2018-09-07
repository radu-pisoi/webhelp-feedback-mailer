package com.oxygenxml.webhelp.feedback.email;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * Test case for {@link EmailService}.
 */
@TestPropertySource("file:src/test/resources/issue-6/application.properties")
@ContextConfiguration(classes = {EmailService.class, MailConfiguration.class, FreemarkerConfig.class})
public class EmailServiceTest extends AbstractTestNGSpringContextTests  {
	
	/**
	 * Logger for logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(EmailServiceTest.class);
	
	/**
	 * Email service
	 */
    @Autowired
    private EmailService emailService;

    private GreenMail smtpServer;
    
    @BeforeClass
    public void beforeClass() {
    	logger.debug("Start server...");    	    	
    	smtpServer = new GreenMail(new ServerSetup(2525, null, "smtp"));
    	smtpServer.start();
	}

    @AfterClass
    protected void afterClass() {
    	System.out.println("Stop server...");
        smtpServer.stop();
    }
    
    /**
     * Test for sending a simple message.
     * 
     * @throws Exception
     */
    @Test
    public void testSendingSimpleMessage() throws Exception {
        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("info@memorynotfound.com");
        mail.setSubject("Spring Mail Integration Testing with JUnit and GreenMail Example");
        Map<String, String> model = new HashMap<String, String>();
		model.put("name", "Test Name");
		model.put("signature", "https://www.test.ro");
		mail.setModel(model);
        emailService.sendMessage(mail, "email-template.ftl");

        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        assertEquals("We need to receive the email sent.", 1, receivedMessages.length);
        
        // Check the message content
        MimeMessage current = receivedMessages[0];        
        String htmlContent = getHTMLPartContent((Multipart) current.getContent());        
        logger.debug("Email content: " + htmlContent);
        assertTrue(htmlContent.contains("<p class=\"hello\">Hello Test Name,</p>"));
        
        assertEquals("Email subject should be preserved", mail.getSubject(), current.getSubject());
        assertEquals("Email 'to' info should be preserved", mail.getTo(), current.getAllRecipients()[0].toString());
       
    }
    
    /**
     * Extract from email multipart the content of first part with HTML content type.
     * 
     * @param multipart The email multipart to check.
     * @return The content of the first HTML part.
     * @throws Exception When HTL part cannot be extracted. 
     */
    private String getHTMLPartContent(Multipart multipart) throws Exception {    	    	
    	String htmlContent = null;
    	
    	// Iterate over parts to find HTML part
    	int count = multipart.getCount();
    	for (int i = 0; i < count; i++) {
			BodyPart bodyPart = multipart.getBodyPart(i);
			
			if (bodyPart.isMimeType("text/html")) {
				 htmlContent = (String) bodyPart.getContent();
			} else if (bodyPart.getContent() instanceof Multipart){
				htmlContent = getHTMLPartContent((Multipart) bodyPart.getContent());
			}			
		}
    	return htmlContent;
    }

}