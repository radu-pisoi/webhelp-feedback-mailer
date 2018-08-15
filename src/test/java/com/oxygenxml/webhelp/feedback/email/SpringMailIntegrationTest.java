package com.oxygenxml.webhelp.feedback.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

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

@SpringBootTest
@ActiveProfiles("test")
//@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {EmailService.class, MailConfiguration.class})
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
    public void shouldSendSingleMail() throws MessagingException, IOException {
        Mail mail = new Mail();
        mail.setFrom("no-reply@memorynotfound.com");
        mail.setTo("info@memorynotfound.com");
        mail.setSubject("Spring Mail Integration Testing with JUnit and GreenMail Example");
        mail.setContent("We show how to write Integration Tests using Spring and GreenMail.");

        emailService.sendSimpleMessage(mail);

        MimeMessage[] receivedMessages = getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(mail.getSubject(), current.getSubject());
        assertEquals(mail.getTo(), current.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current.getContent()).contains(mail.getContent()));
    }

}