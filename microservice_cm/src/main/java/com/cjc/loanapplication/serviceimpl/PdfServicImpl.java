package com.cjc.loanapplication.serviceimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cjc.loanapplication.exceptions.ResourseNotFoundException;
import com.cjc.loanapplication.model.Customer;
import com.cjc.loanapplication.model.SanctionLetter;
import com.cjc.loanapplication.repository.PdfRepository;
import com.cjc.loanapplication.servicei.PdfServiceI;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
@Service
public class PdfServicImpl implements PdfServiceI{
@Autowired
PdfRepository pdfrepo;
	@Override
	public Customer createPdf(Integer customerId) {
		Date d=new Date();
		Optional<Customer> findById = pdfrepo.findById(customerId);
		Customer cus=findById.get();
		if(cus!=null)
		{
			
			SanctionLetter sanctionLetter = cus.getSanctionLetter();
			
String title="Loan Sanction";
String content="[Star Finance Limited]\r\n"
		+ "[Pune, Maharashtra,161385"+ "\r\n"
		+ "Date:"+sanctionLetter.getSanctionDate()
		+ "\r\n"
		+ "To,\r\n"
	    + "Dear," +sanctionLetter.getApplicantName()
		+ "\r\n"
		+ "We are pleased to inform you that your loan application has been approved. The sanctioned amount " +sanctionLetter.getLoanAmountSanctioned() +"The loan will be disbursed at an interest rate of " +sanctionLetter.getRateOfInterest()+ " per annum, on a "+sanctionLetter.getInterestType() +" basis. The tenure for the loan is "+ sanctionLetter.getLoanTenure()+" , and the monthly EMI amount will be "+ sanctionLetter.getMonthlyEmiAmount()+ " The mode of payment for the EMIs will be "+ sanctionLetter.getModeOfPayment()
		+ "\r\n"
		+ "The EMI payments will start from the month following the disbursement of the loan. You can prepay the loan according to the terms specified in the loan agreement.\r\n"
		+ "\r\n"
		+ "Please visit our branch to complete the formalities and sign the loan agreement. For any queries, you can reach us at " +sanctionLetter.getContact()
		+ "\r\n"
		+ "Thank you for choosing [Star Finance Limited].\r\n"
		+ "\r\n"
		+ "Yours sincerely,\r\n"
		+ "\r\n"
		+ "[7038543806]\r\n"
		+ "[mail2shubhamkalokhe04@gmail]\r\n"
		+ "\r\n";

 ByteArrayOutputStream out=new ByteArrayOutputStream();
 
		Document document=new Document();
		 
		PdfWriter.getInstance(document, out);
		
		document.open();
		
		Font titleFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		
		Paragraph titlePara=new Paragraph(title,titleFont);
		
		titlePara.setAlignment(Element.ALIGN_CENTER);
		
		document.add(titlePara);
		
		Font paraFont=FontFactory.getFont(FontFactory.HELVETICA);
		 
		Paragraph paragraph=new Paragraph(content);
		
		document.add(paragraph);
		
		document.close();
	ByteArrayInputStream byt= new ByteArrayInputStream(out.toByteArray());
		byte[] readAllBytes = byt.readAllBytes();
       cus.getSanctionLetter().setSanctionLetter(readAllBytes);
     return pdfrepo.save(cus);
	}
		throw new ResourseNotFoundException("Customer Not Found For Generate Pdf");
	}
}
