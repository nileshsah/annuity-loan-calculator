package com.finance.loancalculator.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnnuityInterestCalculationRequest {
  private Double nominalRate;
  private Double initialOutstandingPrincipal;
  private Integer daysInMonth;
  private Integer daysInYear;
}
