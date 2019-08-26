package com.finance.loancalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AnnuityLoanRepaymentPlan {
  private List<AnnuityRepayment> repayments;
}
