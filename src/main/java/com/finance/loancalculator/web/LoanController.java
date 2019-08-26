package com.finance.loancalculator.web;

import com.finance.loancalculator.dto.AnnuityLoanRepaymentPlan;
import com.finance.loancalculator.dto.AnnuityLoanRequest;
import com.finance.loancalculator.service.AnnuityLoanCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/loans/")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanController {

  private final AnnuityLoanCalculatorService annuityLoanCalculatorService;

  @PostMapping(path = "/annuity/plan")
  public AnnuityLoanRepaymentPlan planAnnuityRepayment(@RequestBody @Valid AnnuityLoanRequest loanRequest) {
    return annuityLoanCalculatorService.plan(loanRequest);
  }
}
