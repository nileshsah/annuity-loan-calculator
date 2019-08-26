package com.finance.loancalculator.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Data
@Builder
public class AnnuityLoanRequest {

  @DecimalMin(value = "0.01", message = "The loan amount must be at least 0.01")
  private Double loanAmount;

  @DecimalMin(value = "0", message = "The nominal rate must be >= 0%")
  private Double nominalRate;

  @Min(value = 1, message = "The duration for the loan must be greater than 0")
  private Integer duration;

  private LocalDateTime startDate;
}
