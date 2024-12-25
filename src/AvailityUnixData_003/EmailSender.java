package AvailityUnixData_003;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
	
	ReadProperties props = new ReadProperties();
	Properties prop;
	String address;

	void Email(String subject, String todayFile)
			throws MessagingException, IOException {

		try {
			prop = props.readPropertiesFile();
			Properties properties = new Properties();
			properties.put("mail.smtp.host", prop.getProperty("host"));
			properties.put("mail.smtp.port", prop.getProperty("port"));
			properties.put("mail.smtp.starttls.enable", "true");

			Session session = Session.getDefaultInstance(properties);
			MimeMessage mailMessage = new MimeMessage(session);

			mailMessage.setFrom(new InternetAddress(prop.getProperty("fromAddress")));
			String to = prop.getProperty("toAddress");
			String[] toAddress = to.split(prop.getProperty("comma"));
			InternetAddress[] toaddresses = new InternetAddress[toAddress.length];
			for (int i = 0; i < toAddress.length; i++) {
				toaddresses[i] = new InternetAddress(toAddress[i]);
			}
			for (int i = 0; i < toAddress.length; i++) {
				mailMessage.setRecipients(Message.RecipientType.TO, toaddresses);
			}

			String cc = prop.getProperty("ccAddress");
			String[] ccAddress = cc.split(prop.getProperty("comma"));
			InternetAddress[] ccaddresses = new InternetAddress[ccAddress.length];
			for (int i = 0; i < ccAddress.length; i++) {
				ccaddresses[i] = new InternetAddress(ccAddress[i]);
			}
			for (int i = 0; i < ccAddress.length; i++) {
				mailMessage.setRecipients(Message.RecipientType.CC, ccaddresses);
			}
			
			String body = prop.getProperty("body");
			String signature = prop.getProperty("signature");
			mailMessage.setSubject(subject);
			mailMessage.setText(body + todayFile + signature);
			Transport.send(mailMessage);
			System.out.println("Detailed Email message sent.");
			
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
