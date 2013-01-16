package com.tricode.checkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@PropertySource("classpath:/checkin.properties")
@EnableScheduling
@EnableWebMvc
public class CheckinConfig {

    @Value("${checkin.xml.file}")
    private String xmlFile;

    public String getXmlFile() {
        return xmlFile;
    }

}
