package com.cjc.loanapplication.servicei;

import java.io.ByteArrayInputStream;

import com.cjc.loanapplication.model.Customer;

public interface PdfServiceI {
public Customer createPdf(Integer customerId);
}
