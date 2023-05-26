package com.groupeun.order.application.ports.input;

import com.groupeun.order.domain.model.Setting;

public interface SettingInputPort {

    Setting getSetting (String name);

    Setting updateSetting (String name, String value);

    void reloadSetting ();

}
