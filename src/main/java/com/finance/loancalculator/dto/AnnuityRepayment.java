package com.finance.loancalculator.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AnnuityRepayment {
  private Double borrowerPaymentAmount;
  private LocalDateTime date;
  private Double initialOutstandingPrincipal;
  private Double interest;
  private Double principal;
  private Double remainingOutstandingPrincipal;
}