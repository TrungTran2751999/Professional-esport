package com.cg.domain.esport.enums;

public enum EnumGenders {
    MALE("Nam"),
    FEMALE("Nữ");

    private final String value;

    EnumGenders(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
