package com.tricode.checkin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@PropertySource("classpath:/checkin.properties")
@EnableScheduling
@EnableWebMvc
public class CheckinConfig {

    @Autowired
    private Environment environment;

    public String getXmlFile() {
        return environment.getProperty("checkin.xml.file");
    }

}
