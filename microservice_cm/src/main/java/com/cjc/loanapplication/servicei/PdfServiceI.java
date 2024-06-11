package com.cjc.loanapplication.servicei;

import java.io.ByteArrayInputStream;

public interface PdfServiceI {
public ByteArrayInputStream createPdf(Integer customerId);
}
