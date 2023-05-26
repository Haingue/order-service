package com.groupeun.application.output.implement;

import com.groupeun.order.application.ports.output.SettingOutputPort;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SettingOutPutPortImplement implements SettingOutputPort {

    private static SettingOutPutPortImplement instance = new SettingOutPutPortImplement();
    private static HashMap<String, String> store;

    private SettingOutPutPortImplement() {
        super();
        store = new HashMap<>();
    }

    public static SettingOutputPort getInstance() {
        return SettingOutPutPortImplement.instance;
    }

    public static HashMap<String, String> getStore() {
        return SettingOutPutPortImplement.store;
    }

    @Override
    public Map<String, String> importSetting() {
        return store.entrySet().stream()
            .collect(Collectors.toMap(
                setting -> setting.getKey(),
                setting -> setting.getValue()
        ));
    }

    @Override
    public void exportSetting(Map<String, String> settings) {

    }
}
