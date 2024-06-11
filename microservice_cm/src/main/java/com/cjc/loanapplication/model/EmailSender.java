package com.cjc.loanapplication.model;

import org.springframework.beans.factory.annotation.Value;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSender {
@Value("$spring.mail.username")
private String fromEmail;
private String toEmail;
private String subject;
private String textMessage;

@Lob
@Column(length=690000)
private byte[] attachment;
}
