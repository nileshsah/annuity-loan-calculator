package com.finance.loancalculator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnuityLoanRequest {

  @NotNull(message = "Loan Amount must be specified")
  @DecimalMin(value = "100", message = "The loan amount must be at least 100")
  private Double loanAmount;

  @NotNull(message = "Nominal Rate must be specified")
  @DecimalMin(value = "0", message = "The nominal rate must be >= 0%")
  private Double nominalRate;

  @NotNull(message = "Loan duration must be specified")
  @Min(value = 1, message = "The duration for the loan must be greater than 0")
  private Integer duration;

  @NotNull(message = "Start date for the loan is required")
  private LocalDateTime startDate;
}
