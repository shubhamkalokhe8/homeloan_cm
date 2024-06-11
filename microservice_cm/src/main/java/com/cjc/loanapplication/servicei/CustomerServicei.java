package com.cjc.loanapplication.servicei;

import java.util.List;

import com.cjc.loanapplication.model.Customer;
import com.cjc.loanapplication.model.EmailForEnquiry;
import com.cjc.loanapplication.model.SanctionLetter;

public interface CustomerServicei {
public List<Customer> getAllApprovedDocumentCustomer();

public List<Customer> getAllRejectedDocumentCustomer();

public Customer getsingleCustomer(Integer custId);

public Customer sanctionLoan(SanctionLetter s, Integer custoId,EmailForEnquiry email);

public List<Customer> getAllSanction();
}
