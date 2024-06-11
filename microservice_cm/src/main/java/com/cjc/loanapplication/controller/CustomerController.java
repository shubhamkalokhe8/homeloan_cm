  package com.cjc.loanapplication.controller;
//Shubham Kalokhe
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cjc.loanapplication.model.Customer;
import com.cjc.loanapplication.model.EmailForEnquiry;
import com.cjc.loanapplication.model.SanctionLetter;
import com.cjc.loanapplication.servicei.CustomerServicei;

import lombok.extern.slf4j.Slf4j;
@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/customer")
public class CustomerController {
@Autowired
CustomerServicei customerservicei;
@GetMapping("/customer")
public ResponseEntity<List<Customer>> getAllApprovedDocumentCustomer(){
	List<Customer> customers=customerservicei.getAllApprovedDocumentCustomer();
	log.info("Found Document Approved Customer......");
	return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
}
@GetMapping("/customerrejected")
public ResponseEntity<List<Customer>> getAllRejectedDocumentCustomer(){
	List<Customer> customers=customerservicei.getAllRejectedDocumentCustomer();
	log.warn("Found Rejected Document Customer.....");
	return new ResponseEntity<List<Customer>>(customers,HttpStatus.OK);
}
@GetMapping("/getSingleCustomer/{custermerId}")
public ResponseEntity<Customer> getSingleCustomer(@PathVariable Integer custId){
	Customer customers=customerservicei.getsingleCustomer(custId);
	log.info("Find Customer Of given id......");
	return new ResponseEntity<Customer>(customers,HttpStatus.OK);
}
@PutMapping("/sanction/{custoId}")
public ResponseEntity<Customer>sanctionLoanToCustomer(@RequestBody SanctionLetter s,@PathVariable Integer custoId, EmailForEnquiry email){
	Customer custom=customerservicei.sanctionLoan(s,custoId,email);
	if(custom!=null) {
		log.info("Sanction Loan.....");
		return new ResponseEntity<Customer>(custom,HttpStatus.OK);
	}
	else {
		log.warn("No Customer For Sanction...");
		return new ResponseEntity<Customer>(HttpStatus.BAD_REQUEST);
	}
}
@GetMapping("/sanctionCustomer")
public List<Customer> getAllSanctionCostumer(){
	List<Customer> custList=customerservicei.getAllSanction();
	return custList;
}
}
