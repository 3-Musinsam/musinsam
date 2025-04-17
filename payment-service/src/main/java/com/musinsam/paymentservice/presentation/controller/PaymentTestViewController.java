package com.musinsam.paymentservice.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payments")
public class PaymentTestViewController {

  @GetMapping
  public String indexPage() {
    return "index";
  }

  @GetMapping("/success")
  public String successPage() {
    return "success";
  }

  @GetMapping("/fail")
  public String failPage() {
    return "fail";
  }
}
