package com.bsmtech.bsm.util;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {
	
	@Autowired
	private JavaMailSender sender;
	
	public boolean send(
			String to,
			String subject,
			String text
			)
	{
		boolean sent;
		try {
			//1.Create MIMEMessage Object
			MimeMessage message = sender.createMimeMessage();
			//2. Use Helper class object
			MimeMessageHelper helper = new MimeMessageHelper(message);
			//3.Set Details to object
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(text);
			//4.Send Email
			sender.send(message);
			
			sent = true;
		}catch (Exception e) {
			sent = false;
			e.printStackTrace();
		}

		return sent;
    }
}
