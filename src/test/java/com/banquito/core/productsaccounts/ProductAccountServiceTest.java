package com.banquito.core.productsaccounts;

import com.banquito.core.productsaccounts.exception.CRUDException;
import com.banquito.core.productsaccounts.model.InterestRate;
import com.banquito.core.productsaccounts.model.ProductAccount;
import com.banquito.core.productsaccounts.repository.ProductAccountRepository;
import com.banquito.core.productsaccounts.service.ProductAccountService;
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
public class ProductAccountServiceTest {
    private ProductAccount productAccount;
    @InjectMocks
    private ProductAccountService productAccountService;

    @Mock
    private ProductAccountRepository productAccountRepository;

    @BeforeEach
    void setUp(){
        this.productAccountService = new ProductAccountService(this.productAccountRepository);
        this.productAccount = new ProductAccount();
        this.productAccount.setId("123");
        this.productAccount.setName("producto 1");
        this.productAccount.setDescription("descripcion 1");
        this.productAccount.setMinimunBalance(new BigDecimal(50));
        this.productAccount.setPayInterest("payInterest");
        this.productAccount.setAcceptsChecks("YES");
        this.productAccount.setState("ACT");
        this.productAccount.setCreationDate(new Date());
    }

    @Test
    void testListAllActives(){
        List<ProductAccount> products = new ArrayList<>();
        products.add(this.productAccount);
        when(this.productAccountRepository.findByState("ACT")).thenReturn(products);
        assertDoesNotThrow(() -> {
            this.productAccountService.listAllActives();
        });
    }
    @Test
    void testObtainById(){
        when(this.productAccountRepository.findById("123")).thenReturn(Optional.of(this.productAccount));
        assertDoesNotThrow(() -> {
            this.productAccountService.obtainById("123");
        });
        assertThrows(CRUDException.class, () -> {
            this.productAccountService.obtainById("1");
        });
    }


}
