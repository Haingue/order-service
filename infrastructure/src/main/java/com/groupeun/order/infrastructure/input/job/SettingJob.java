package com.groupeun.order.infrastructure.input.job;

import com.groupeun.order.application.ports.input.SettingInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SettingJob {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SettingInputPort settingInputPort;

    @Scheduled()
    private void reloadSetting () {
        try {
            settingInputPort.reloadSetting();
        } catch (Exception ex) {
            logger.error("Error to refresh setting from datastore: {}", ex.getMessage());
        }
    }
}
