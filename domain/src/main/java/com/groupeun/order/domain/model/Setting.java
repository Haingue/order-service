package com.groupeun.order.domain.model;

public enum Setting {

    CHECK_INGREDIENT("false");

    private String value;

    Setting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
