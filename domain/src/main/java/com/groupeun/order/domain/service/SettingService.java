package com.groupeun.order.domain.service;

import com.groupeun.order.application.ports.input.SettingInputPort;
import com.groupeun.order.application.ports.output.SettingOutputPort;
import com.groupeun.order.domain.exception.SettingNotFound;
import com.groupeun.order.domain.model.Setting;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collections;
import java.util.Map;

@Data
@AllArgsConstructor
public class SettingService implements SettingInputPort {

    private SettingOutputPort settingOutputPort;

    @Override
    public Setting getSetting(String name) {
        Setting setting = Setting.valueOf(name);
        if (setting == null) throw new SettingNotFound(name);
        return setting;
    }

    @Override
    public Setting updateSetting(String name, String value) {
        Setting setting = Setting.valueOf(name);
        if (setting == null) throw new SettingNotFound(name);
        setting.setValue(value);
        settingOutputPort.exportSetting(Collections.singletonMap(name, value));
        return setting;
    }

    @Override
    public void reloadSetting() {
        Map<String, String> settingsImported = settingOutputPort.importSetting();
        for (Map.Entry<String, String> settingImported : settingsImported.entrySet()) {
            Setting setting = Setting.valueOf(settingImported.getKey());
            if (setting != null) {
                setting.setValue(settingImported.getValue());
            }
        }
    }
}
