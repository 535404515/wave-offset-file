package com.nannar.bean;

import com.nannar.conf.QuartzConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Caiyuhui
 * @date 2020/5/15
 * @time 9:00
 */
@Component
public class QuartzRunner implements ApplicationRunner {
    private final QuartzConfig quartzConfig;
    @Autowired
    public QuartzRunner(QuartzConfig quartzConfig) {
        this.quartzConfig = quartzConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        quartzConfig.startJob();
    }
}
