package com.groupeun.order.infrastructure.output.service;

import com.groupeun.order.application.ports.output.SettingOutputPort;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SettingAdapter implements SettingOutputPort {

    @Override
    public Map<String, String> importSetting() {
        return null;
    }

    @Override
    public void exportSetting(Map<String, String> settings) {

    }
}
