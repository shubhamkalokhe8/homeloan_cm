package com.cjc.loanapplication.serviceimpl;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.MimeHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.cjc.loanapplication.exceptions.ResourseNotFoundException;
import com.cjc.loanapplication.model.Customer;
import com.cjc.loanapplication.model.EmailForEnquiry;
import com.cjc.loanapplication.model.SanctionLetter;
import com.cjc.loanapplication.repository.CustomerRepository;
import com.cjc.loanapplication.servicei.CustomerServicei;
import com.cjc.loanapplication.utility.EmiCal;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import jakarta.mail.internet.MimeMessage;
@Service
public class CustomerServiceimpl implements CustomerServicei{
@Autowired
CustomerRepository crp;
@Autowired
JavaMailSender jms;
	@Override
	public List<Customer> getAllApprovedDocumentCustomer() {
		List<Customer> findByStatus = crp.findByStatus("ApprovedDocuments");
		if(!findByStatus.isEmpty()) {
			return findByStatus;
		}
		else {
			throw new ResourseNotFoundException("No Approves document Customer found");
		}
	}
	@Override
	public List<Customer> getAllRejectedDocumentCustomer() {
		List<Customer> rejectedCustomers = crp.findByStatus("RejectedDocuments");
		if(!rejectedCustomers.isEmpty())
		{
			return rejectedCustomers;
		}
		else {
			throw new ResourseNotFoundException("No Rejected document Customer found");
		}
	
	}
	@Override
	public Customer getsingleCustomer(Integer custId) {
	Optional<Customer> findById = crp.findById(custId);
	Customer customer = findById.get();
	if(findById.isPresent()) {
		return customer;
	}
	else {
		throw new ResourseNotFoundException("No Customer Found Of this Id");
	}
	}
	@Override
	public Customer sanctionLoan(SanctionLetter s, Integer custoId,EmailForEnquiry email) {
		
	SimpleMailMessage m=new SimpleMailMessage();
		
	
		Optional<Customer> optionalCustomer = crp.findById(custoId);
		
		if(optionalCustomer.isPresent()) {
	
			Customer customer = optionalCustomer.get();
			
			SanctionLetter sanction=customer.getSanctionLetter();
			//for store today's date
			LocalDate localDate = LocalDate.now();
			 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		     String formattedDate = localDate.format(formatter);
			sanction.setSanctionDate(formattedDate);
			
			String subject="Sanction Letter";
			String text="Dear,\r\n"
					+customer.getCustomerName()
					+ "\r\n"
					+ "We are delighted to inform you that your loan application has been approved as of "+ sanction.getSanctionDate() +"The loan amount of " + s.getLoanAmountSanctioned() +"has been sanctioned under the "+ s.getInterestType() + " interest scheme at a rate of "+ s.getRateOfInterest()+"%. The loan tenure is "
							+ ""+ s.getLoanTenure() +"years, resulting in a monthly EMI amount of "
							+ "" +sanction.getMonthlyEmiAmount() +" Please [[Download sanction Letter from Login]]For any queries or further assistance, please feel free to contact us at [7038543806].\r\n"
					+ "\r\n"
					+ "We sincerely appreciate your trust in us and look forward to supporting your financial goals.\r\n"
					+ "\r\n"
					+ "Best Regards,\r\n"
					+ "\r\n"
					+ "[Star Finance Limited]\r\n"
					+ "[7038543806]\r\n"
					+ "[Pune, Phase 3]";
			m.setSubject(subject);
			m.setText(text);
			m.setTo(customer.getCustomerEmailId());
			customer.setStatus("Sanction");
			
			sanction.setApplicantName(s.getApplicantName());
		
			sanction.setLoanAmountSanctioned(s.getLoanAmountSanctioned());
			sanction.setRateOfInterest(s.getRateOfInterest());
			sanction.setInterestType(s.getInterestType());
			sanction.setLoanTenure(s.getLoanTenure());
			sanction.setApplicantName(customer.getCustomerName());
			sanction.setContact(customer.getCustomerMobileNo());
			sanction.setMonthlyEmiAmount(EmiCal.emiCalculator(s.getLoanAmountSanctioned(), s.getRateOfInterest(), s.getLoanTenure()));
			customer.setSanctionLetter(sanction);
			Customer cust=crp.save(customer);
			if(cust!=null) {
				jms.send(m);
				 return cust;
			}
			else {
				throw new ResourseNotFoundException("Failed to genearte sanction letter");
			}
			
           
}
		else {
			throw new ResourseNotFoundException("No customer For Sanction Loan");
		}
	}
	@Override
	public List<Customer> getAllSanction() {
		List<Customer> findByStatus = crp.findByStatus("Sanction");
		return findByStatus;
	}
	
}
	
