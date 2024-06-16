 package com.cjc.loanapplication.controller;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.loanapplication.model.Customer;
import com.cjc.loanapplication.servicei.PdfServiceI;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/generatepdf")
class PdfController {
	@Autowired
PdfServiceI pdfservicei;
	
@PutMapping(value="/generatepdf/{customerId}" ,produces = MediaType.APPLICATION_PDF_VALUE)
public ResponseEntity<Customer> createPdf(@PathVariable Integer customerId) {
	System.out.println(customerId);
	Customer pdf=pdfservicei.createPdf(customerId);
log.info("Pdf Generate Of Sanction Letter");
	return new  ResponseEntity<Customer>(pdf,HttpStatus.OK);
}
}
