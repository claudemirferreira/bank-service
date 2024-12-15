package com.bank.balance.domain.enumarations;

public enum OperationTypeEnum {
    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String description;

    OperationTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
