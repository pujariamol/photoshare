/**
 * 
 */
package com.photoshare.utility;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Amol
 *
 */
public class EmailModule {
	//TODO move these fields to a property file
	private static final String SENDER_EMAIL_ID = "amol.pujari@sjsu.edu";
	private static final String SENDER_PASSWORD = "Amol1988@";
	
	
	private static Session session = null;

	static {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(SENDER_EMAIL_ID,
								SENDER_PASSWORD);
					}
				});

	}

	public static void sendEmail(String receiverEmailId, String body,
			String subject) {

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(SENDER_EMAIL_ID));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(receiverEmailId));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	/*
	 * public void readGmail() {
	 * 
	 * 
	 * this will print subject of all messages in the inbox of sender@gmail.com
	 * 
	 * 
	 * this.receivingHost = "imap.gmail.com";// for imap protocol
	 * 
	 * Properties props2 = System.getProperties();
	 * 
	 * props2.setProperty("mail.store.protocol", "imaps"); // I used imaps
	 * protocol here
	 * 
	 * Session session2 = Session.getDefaultInstance(props2, null);
	 * 
	 * try {
	 * 
	 * Store store = session2.getStore("imaps");
	 * 
	 * store.connect(this.receivingHost, this.userName, this.password);
	 * 
	 * Folder folder = store.getFolder("INBOX");// get inbox
	 * 
	 * folder.open(Folder.READ_ONLY);// open folder only to read
	 * 
	 * Message message[] = folder.getMessages();
	 * 
	 * for (int i = 0; i < message.length; i++) {
	 * 
	 * // print subjects of all mails in the inbox
	 * 
	 * System.out.println(message[i].getSubject());
	 * 
	 * // anything else you want
	 * 
	 * }
	 * 
	 * // close connections
	 * 
	 * folder.close(true);
	 * 
	 * store.close();
	 * 
	 * } catch (Exception e) {
	 * 
	 * System.out.println(e.toString());
	 * 
	 * }
	 * 
	 * }
	 */

	public static void main(String[] args) {
		final String myaddr = "amol.pujari@sjsu.edu";
		final String mypass = "Amol1988@";
		String destaddr = "amol.pujari@sjsu.edu";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(myaddr, mypass);
					}
				});

		try {
			String msgtext = "This is the mail from Java Program";
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("amol.pujari@sjsu.edu"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(destaddr));
			message.setSubject("Mail Using Java Program. Reply Me ");
			message.setText(msgtext);

			Transport.send(message);

			System.out.println("-------->Done<---------");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}