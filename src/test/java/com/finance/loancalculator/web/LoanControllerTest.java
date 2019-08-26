package com.finance.loancalculator.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.finance.loancalculator.dto.AnnuityLoanRepaymentPlan;
import com.finance.loancalculator.dto.AnnuityLoanRequest;
import com.finance.loancalculator.service.AnnuityLoanCalculatorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerTest {

  private static final String ANNUITY_PLAN_ENDPOINT = "/loans/annuity/plan";

  private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AnnuityLoanCalculatorService annuityLoanCalculatorService;

  @Test
  public void shouldReturnSuccessForValidRequest() throws Exception {
    AnnuityLoanRequest request = AnnuityLoanRequest.builder()
        .loanAmount(5000.0)
        .duration(24)
        .nominalRate(5.0)
        .startDate(LocalDateTime.now())
        .build();

    Mockito.when(annuityLoanCalculatorService.plan(ArgumentMatchers.eq(request))).thenReturn(
        new AnnuityLoanRepaymentPlan(Collections.EMPTY_LIST)
    );

    mockMvc.perform(
        post(ANNUITY_PLAN_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().is2xxSuccessful());

    Mockito.verify(annuityLoanCalculatorService, Mockito.times(1)).plan(ArgumentMatchers.eq(request));
  }

  @Test
  public void shouldReturnBadRequestOnInvalidRequest() throws Exception {
    AnnuityLoanRequest request = AnnuityLoanRequest.builder()
        .loanAmount(0.0)
        .duration(24)
        .nominalRate(5.0)
        .startDate(LocalDateTime.now())
        .build();

    mockMvc.perform(
        post(ANNUITY_PLAN_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    Mockito.verifyZeroInteractions(annuityLoanCalculatorService);
  }

  @Test
  public void shouldReturnBadRequestOnMissingRequestParams() throws Exception {
    AnnuityLoanRequest request = AnnuityLoanRequest.builder()
        .loanAmount(10.0)
        .duration(24)
        .startDate(LocalDateTime.now())
        .build();

    mockMvc.perform(
        post(ANNUITY_PLAN_ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(mapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());

    Mockito.verifyZeroInteractions(annuityLoanCalculatorService);
  }

}
