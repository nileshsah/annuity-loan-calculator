package com.finance.loancalculator.service;

import com.finance.loancalculator.dto.AnnuityLoanRepaymentPlan;
import com.finance.loancalculator.dto.AnnuityLoanRequest;
import com.finance.loancalculator.dto.AnnuityRepayment;
import com.finance.loancalculator.model.AnnuityInterestCalculationRequest;
import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnnuityLoanCalculatorService {

  private static final int DAYS_IN_MONTH = 30;
  private static final int DAYS_IN_YEAR = 360;

  public AnnuityLoanRepaymentPlan plan(AnnuityLoanRequest loanRequest) {
    final Double annuityAmount = calculateAnnuityAmount(
        loanRequest.getLoanAmount(),
        loanRequest.getNominalRate() / 1200,
        loanRequest.getDuration()
    );

    Double currentOutstandingPrincipal = loanRequest.getLoanAmount();
    List<AnnuityRepayment> repayments = new ArrayList<>();

    for (Integer currentMonth = 0; currentMonth < loanRequest.getDuration(); currentMonth = currentMonth + 1) {
      final LocalDateTime currentDateTime = loanRequest.getStartDate().plusMonths(currentMonth);
      final Double interest = calculateInterest(
          AnnuityInterestCalculationRequest.builder()
              .daysInMonth(DAYS_IN_MONTH)
              .daysInYear(DAYS_IN_YEAR)
              .initialOutstandingPrincipal(currentOutstandingPrincipal)
              .nominalRate(loanRequest.getNominalRate() / 100)
              .build()
      );
      final Double principal = calculatePrincipal(annuityAmount, interest, currentOutstandingPrincipal);
      final Double borrowerPaymentAmount = calculateBorrowerPaymentAmount(principal, interest);
      final Double remainingOutstandingPrincipal = Precision.round(currentOutstandingPrincipal - principal, 2);

      repayments.add(
          AnnuityRepayment.builder()
              .date(currentDateTime)
              .borrowerPaymentAmount(borrowerPaymentAmount)
              .principal(principal)
              .interest(interest)
              .initialOutstandingPrincipal(currentOutstandingPrincipal)
              .remainingOutstandingPrincipal(remainingOutstandingPrincipal)
              .build()
      );
      currentOutstandingPrincipal = remainingOutstandingPrincipal;
    }

    return new AnnuityLoanRepaymentPlan(repayments);
  }

  private Double calculateBorrowerPaymentAmount(Double principal, Double interest) {
    return Precision.round(principal + interest, 2);
  }

  private Double calculatePrincipal(Double annuityAmount, Double interest, Double initialOutstandingPrincipal) {
    return Precision.round(Math.min(annuityAmount - interest, initialOutstandingPrincipal), 2);
  }

  private Double calculateInterest(AnnuityInterestCalculationRequest request) {
    return Precision.round(
        request.getNominalRate() * request.getDaysInMonth() * request.getInitialOutstandingPrincipal() / request.getDaysInYear(),
        2
    );
  }

  private Double calculateAnnuityAmount(Double presentValue, Double ratePerPeriod, Integer numberOfPeriods) {
    return Precision.round((ratePerPeriod * presentValue) / (1.0 - Math.pow(1 + ratePerPeriod, -numberOfPeriods)), 2);
  }
}