package com.banquito.core.productsaccounts;

import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.model.InterestRate;
import com.banquito.core.productsaccounts.repository.InterestRateRepository;
import com.banquito.core.productsaccounts.service.InterestRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class InterestRateServiceTest {

    private InterestRate interestRate = new InterestRate();
    @InjectMocks
    private InterestRateService interestRateService;

    @Mock
    private InterestRateRepository interestRateRepository;

    @BeforeEach
    void setUp(){
    this.interestRateService = new InterestRateService(this.interestRateRepository);
    this.interestRate.setId(1);
    this.interestRate.setName("rate 1");
    this.interestRate.setInterestRate(new BigDecimal(20));
    this.interestRate.setState("ACT");
    this.interestRate.setStart(new Date());
    this.interestRate.setEnd(null);
    }

    @Test
    void testListAllActives(){
        List<InterestRate> rates = new ArrayList<>();
        rates.add(this.interestRate);
        when(this.interestRateRepository.findByState("ACT")).thenReturn(rates);
        assertDoesNotThrow(() -> {
            this.interestRateService.listAllActives();
        });
    }

    @Test
    void testObtainById(){
        when(this.interestRateRepository.findById(1)).thenReturn(Optional.of(this.interestRate));
        assertDoesNotThrow(() -> {
            this.interestRateService.obtainById(1);
        });
        assertThrows(CRUDException.class, () -> {
            this.interestRateService.obtainById(2);
        });
    }

    @Test
    void testCreate(){
        InterestRate interestRateCreate = new InterestRate();
        interestRateCreate.setId(1);
        interestRateCreate.setName("rate 1");
        interestRateCreate.setInterestRate(new BigDecimal(20));
        interestRateCreate.setState("ACT");
        interestRateCreate.setStart(new Date());
        interestRateCreate.setEnd(null);
        when(this.interestRateRepository.save(interestRateCreate)).thenReturn(this.interestRate);
        assertDoesNotThrow(() -> {
            this.interestRateService.create(interestRateCreate);
        });
    }

    @Test
    void testUpdate(){
        InterestRate interestRateCreate = new InterestRate();
        interestRateCreate.setId(1);
        interestRateCreate.setName("rate 2");
        interestRateCreate.setInterestRate(new BigDecimal(30));
        interestRateCreate.setState("ACT");
        interestRateCreate.setStart(new Date());
        interestRateCreate.setEnd(null);
        when(this.interestRateRepository.findById(1)).thenReturn(Optional.of(this.interestRate));
        assertDoesNotThrow(() -> {
            this.interestRateService.update(1, interestRateCreate);
        });
    }

    @Test
    void testInactivate(){
        when(this.interestRateRepository.findById(1)).thenReturn(Optional.of(this.interestRate));
        assertDoesNotThrow(() -> {
            this.interestRateService.inactivate(1);
        });
    }
}
