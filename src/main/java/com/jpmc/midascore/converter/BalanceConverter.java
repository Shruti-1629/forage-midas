package com.jpmc.midascore.converter;

import com.jpmc.midascore.foundation.Balance;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class BalanceConverter implements AttributeConverter<Balance, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(Balance balance) {
        return (balance == null) ? null : balance.getAmount();
    }

    @Override
    public Balance convertToEntityAttribute(BigDecimal dbData) {
        return (dbData == null) ? null : new Balance(dbData);
    }
}
