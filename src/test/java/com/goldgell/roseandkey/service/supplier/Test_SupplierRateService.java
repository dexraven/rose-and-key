package com.goldgell.roseandkey.service.supplier;

import com.goldgell.roseandkey.supplier.gateway.SupplierGateway;
import com.goldgell.roseandkey.translation.SupplierRateTranslator;
import com.goldgell.roseandkey.validation.SupplierCommandValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class Test_SupplierRateService {

    @Mock
    protected SupplierGateway supplierGateway;

    @Mock
    protected SupplierCommandValidator supplierCommandValidator;

    @Mock
    protected SupplierRateTranslator supplierRateTranslator;

    @InjectMocks
    protected SupplierRateService supplierRateService;
}