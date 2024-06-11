package com.cjc.loanapplication.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.cjc.loanapplication.model.EmailSender;
import com.cjc.loanapplication.servicei.emailserviccei;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements emailserviccei{

@Autowired
private JavaMailSender jms;
@Override
public void sendEmailAttachment(EmailSender es1, String originalFilename) {
MimeMessage mm = jms.createMimeMessage();
try {
	MimeMessageHelper	helper=new MimeMessageHelper(mm,true);
	helper.setTo(es1.getToEmail());
    helper.setSubject(es1.getSubject());
    helper.setText(es1.getTextMessage());
    helper.addAttachment(originalFilename, new ByteArrayResource(es1.getAttachment()));
    jms.send(mm);
} catch (MessagingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
}

}
