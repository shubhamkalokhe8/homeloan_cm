package com.cjc.loanapplication.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cjc.loanapplication.exceptions.ResourseNotFoundException;
import com.cjc.loanapplication.model.Customer;
import com.cjc.loanapplication.model.EmailSender;
import com.cjc.loanapplication.repository.EmailSenderWithAttachmentRepository;
import com.cjc.loanapplication.servicei.emailserviccei;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/mail")
public class SanctionEmailWithAttachment {
	@Autowired
	emailserviccei emailservice;
	@Autowired
	EmailSenderWithAttachmentRepository esi;
	@PostMapping(value = "/mail/{custoId}")
	public ResponseEntity<String> sendAttachment(@PathVariable Integer custoId) {
		EmailSender es1=new EmailSender();
		Optional<Customer> optionCustoId = esi.findById(custoId);
		Customer customer = optionCustoId.get();
		if(customer!=null) {
			
		
		byte[] sanctionLetter = customer.getSanctionLetter().getSanctionLetter();

		try {
			
			String text="We are pleased to inform you that your loan application has been approved. "
					+ "Please find your Loan Santnction Letter attached to this email in PDF format."
					+ "Please review the document carefully. If you have any questions or need further assistance, do not hesitate to contact us.\r\n"
					+ "\r\n"
					+ "Thank you for choosing [Star Finance Limited] for your financial needs.";
			es1.setToEmail(customer.getCustomerEmailId());
		    es1.setAttachment(sanctionLetter);
			es1.setSubject("Sanction Pdf");
			es1.setTextMessage(text);
		    emailservice.sendEmailAttachment(es1,"sanctionletter.pdf");
		    log.info("Email Send With Attachment");
		    return new ResponseEntity<String>("mail send with attachment",HttpStatus.OK);
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		}
		else {
			throw new ResourseNotFoundException("Customer Not Found For Send Email");
		}
		
		return null;
		
		
	}
}
