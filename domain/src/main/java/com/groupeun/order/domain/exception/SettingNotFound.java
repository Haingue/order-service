package com.groupeun.order.domain.exception;

public class SettingNotFound extends DomainException {
    public SettingNotFound() {
        super("Setting not found");
    }

    public SettingNotFound(String name) {
        super(String.format("Setting[name=%s] not found", name));
    }
}
