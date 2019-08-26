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

  @NotNull
  @DecimalMin(value = "0.01", message = "The loan amount must be at least 0.01")
  private Double loanAmount;

  @NotNull
  @DecimalMin(value = "0", message = "The nominal rate must be >= 0%")
  private Double nominalRate;

  @NotNull
  @Min(value = 1, message = "The duration for the loan must be greater than 0")
  private Integer duration;

  @NotNull
  private LocalDateTime startDate;
}
