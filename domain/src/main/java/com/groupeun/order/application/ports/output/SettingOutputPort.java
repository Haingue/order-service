package com.groupeun.order.application.ports.output;

import java.util.Map;

public interface SettingOutputPort {

    Map<String, String> importSetting ();
    void exportSetting (Map<String, String> settings);

}
