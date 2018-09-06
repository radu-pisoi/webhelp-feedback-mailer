package com.oxygenxml.webhelp.feedback.email;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

import freemarker.template.TemplateException;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {EmailService.class, MailConfiguration.class, FreemarkerConfig.class})
public class SpringMailIntegrationTest extends AbstractTestNGSpringContextTests  {

    @Autowired
    private EmailService emailService;

    private GreenMail smtpServer;
    
    @BeforeClass
    public void beforeClass() {
    	System.out.println("Start server...");    	
    	
    	smtpServer = new GreenMail(new ServerSetup(2525, null, "smtp"));
    	smtpServer.start();
	}
    


    public MimeMessage[] getMessages() {
        return smtpServer.getReceivedMessages();
    }

    @AfterClass
    protected void afterClass() {
    	System.out.println("Stop server...");
        smtpServer.stop();
    }
    
    @Test
    public void shouldSendSingleMail() throws MessagingException, IOException, TemplateException {
        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("info@memorynotfound.com");
        mail.setSubject("Spring Mail Integration Testing with JUnit and GreenMail Example");
        Map<String, String> model = new HashMap<String, String>();
		model.put("name", "Test Name");
		model.put("signature", "https://www.test.ro");
		mail.setModel(model);
        emailService.sendSimpleMessage(mail, "email-template.ftl");

        MimeMessage[] receivedMessages = getMessages();
        assertEquals(1, receivedMessages.length);
        
        MimeMessage current = receivedMessages[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        current.writeTo(stream);
        String finalString = new String(stream.toByteArray());

        System.out.println(finalString);
        System.out.println(current.getContent() + "coccocasdoa");
        assertEquals(mail.getSubject(), current.getSubject());
        assertEquals(mail.getTo(), current.getAllRecipients()[0].toString());
        assertNull(current);
        System.out.println("iiiiiiiiii" + String.valueOf(current.getContentType()));
       
        assertTrue(finalString.contains("Test"));
    }

}