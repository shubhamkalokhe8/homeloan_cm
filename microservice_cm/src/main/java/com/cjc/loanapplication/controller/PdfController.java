 package com.cjc.loanapplication.controller;

import java.io.ByteArrayInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.loanapplication.servicei.PdfServiceI;


@CrossOrigin("*")
@RestController
@RequestMapping("/generatepdf")
class PdfController {
	@Autowired
PdfServiceI pdfservicei;
	
@GetMapping(value="/generatepdf/{customerId}" ,produces = MediaType.APPLICATION_PDF_VALUE)
public  ResponseEntity<InputStreamResource> createPdf(@PathVariable Integer customerId) {
	ByteArrayInputStream pdf=pdfservicei.createPdf(customerId);//
	HttpHeaders httpHeaders=new HttpHeaders();
	 httpHeaders.add("Content-Disposition", "homeloan.pdf");
	return ResponseEntity
			.ok()
			.headers(httpHeaders)
			.contentType(MediaType.APPLICATION_PDF)
			.body(new InputStreamResource(pdf));
}
}
