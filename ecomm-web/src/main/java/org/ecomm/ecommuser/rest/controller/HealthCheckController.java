package org.ecomm.ecommuser.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("health-check")
public class HealthCheckController {

  // @Autowired InvoiceRepository invoiceRepository;

  @GetMapping
  public String healthCheck() {

    // invoiceRepository.save(EInvoices.builder().invoiceNo("ahahahha").url("sampleurl").build());
    return "Ecomm-web is running";
  }
}
