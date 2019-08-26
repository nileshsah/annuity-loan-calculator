package com.finance.loancalculator.service;

import com.finance.loancalculator.dto.AnnuityLoanRepaymentPlan;
import com.finance.loancalculator.dto.AnnuityLoanRequest;
import com.finance.loancalculator.dto.AnnuityRepayment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

public class AnnuityLoanCalculatorServiceTest {

  private static LocalDateTime START_DATE = LocalDateTime.of(2018, 01, 01, 0, 0);

  private AnnuityLoanCalculatorService loanCalculatorService;

  @Before
  public void setup() {
    this.loanCalculatorService = new AnnuityLoanCalculatorService();
  }

  @Test
  public void shouldCalculateRepaymentPlanForValidRequest() {
    AnnuityLoanRequest loanRequest =
        AnnuityLoanRequest.builder()
            .loanAmount(5000.0)
            .duration(24)
            .nominalRate(5.0)
            .startDate(START_DATE)
            .build();

    AnnuityLoanRepaymentPlan repaymentPlan = loanCalculatorService.plan(loanRequest);
    List<AnnuityRepayment> repayments = repaymentPlan.getRepayments();

    Assert.assertEquals(24, repayments.size());

    AnnuityRepayment firstRepayment = repayments.get(0);

    Assert.assertEquals(219.36, firstRepayment.getBorrowerPaymentAmount(), 0.001);
    Assert.assertEquals(5000.0, firstRepayment.getInitialOutstandingPrincipal(), 0.001);
    Assert.assertEquals(20.83, firstRepayment.getInterest(), 0.001);
    Assert.assertEquals(198.53, firstRepayment.getPrincipal(), 0.001);
    Assert.assertEquals(4801.47, firstRepayment.getRemainingOutstandingPrincipal(), 0.001);
    Assert.assertEquals(START_DATE, firstRepayment.getDate());

    AnnuityRepayment secondRepayment = repayments.get(1);

    Assert.assertEquals(219.36, secondRepayment.getBorrowerPaymentAmount(), 0.001);
    Assert.assertEquals(4801.47, secondRepayment.getInitialOutstandingPrincipal(), 0.001);
    Assert.assertEquals(20.01, secondRepayment.getInterest(), 0.001);
    Assert.assertEquals(199.35, secondRepayment.getPrincipal(), 0.001);
    Assert.assertEquals(4602.12, secondRepayment.getRemainingOutstandingPrincipal(), 0.001);
    Assert.assertEquals(START_DATE.plusMonths(1), secondRepayment.getDate());

    AnnuityRepayment lastRepayment = repayments.get(repayments.size() - 1);

    Assert.assertEquals(219.28, lastRepayment.getBorrowerPaymentAmount(), 0.001);
    Assert.assertEquals(218.37, lastRepayment.getInitialOutstandingPrincipal(), 0.001);
    Assert.assertEquals(0.91, lastRepayment.getInterest(), 0.001);
    Assert.assertEquals(218.37, lastRepayment.getPrincipal(), 0.001);
    Assert.assertEquals(0.0, lastRepayment.getRemainingOutstandingPrincipal(), 0.001);
    Assert.assertEquals(START_DATE.plusMonths(23), lastRepayment.getDate());
  }

}
