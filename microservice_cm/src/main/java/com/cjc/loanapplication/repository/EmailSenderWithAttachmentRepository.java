package com.cjc.loanapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cjc.loanapplication.model.Customer;

public interface EmailSenderWithAttachmentRepository extends JpaRepository<Customer, Integer>{

}
