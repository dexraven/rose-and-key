package com.goldgell.roseandkey.validation;

import org.springframework.stereotype.Component;

import java.util.Currency;
import java.util.regex.Pattern;

@Component
public class PaymentTransactionValidator {

    // PCI Guard: Detects accidental raw PAN entry (13-19 consecutive digits)
    private static final Pattern RAW_CARD_NUMBER_PATTERN = Pattern.compile("^\\d{13,19}$");

    public void validatePaymentAuthorization(String paymentMethodToken, long amountCents, String currency) {
        validatePaymentMethodToken(paymentMethodToken);
        validateAmount(amountCents);
        validateCurrency(currency);
    }

    public void validatePaymentMethodToken(String token) {
        if (token == null || token.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method token cannot be null or empty.");
        }

        String cleaned = token.trim();

        // PCI Compliance Check: Reject if someone attempts to pass a raw credit card number
        if (RAW_CARD_NUMBER_PATTERN.matcher(cleaned.replaceAll("[\\s-]", "")).matches()) {
            throw new IllegalArgumentException("PCI Violation: Raw cardholder numbers cannot be processed. Tokenized reference required.");
        }

        if (cleaned.length() < 5 || cleaned.length() > 255) {
            throw new IllegalArgumentException("Payment method token length must be between 5 and 255 characters.");
        }
    }

    public void validateAmount(long amountCents) {
        if (amountCents <= 0) {
            throw new IllegalArgumentException("Payment amount must be strictly greater than zero cents.");
        }

        // Hard business ceiling: single transaction limit check ($250,000 USD equivalent)
        if (amountCents > 25_000_000L) {
            throw new IllegalArgumentException("Transaction amount exceeds single-charge authorized ceiling of $250,000.00.");
        }
    }

    public void validateCurrency(String currencyCode) {
        if (currencyCode == null || currencyCode.trim().length() != 3) {
            throw new IllegalArgumentException("Currency must be a valid 3-letter ISO-4217 code.");
        }

        try {
            Currency.getInstance(currencyCode.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unsupported or invalid currency code: " + currencyCode);
        }
    }
}