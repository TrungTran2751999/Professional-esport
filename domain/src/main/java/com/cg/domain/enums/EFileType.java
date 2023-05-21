package com.cg.domain.enums;

public enum EFileType {
    IMAGE("image"),
    VIDEO("video");

    private final String value;

    EFileType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
