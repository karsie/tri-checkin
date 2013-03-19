package com.tricode.checkin.config;

import org.apache.commons.lang.CharEncoding;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;

@Configuration
@PropertySource("classpath:/checkin.properties")
@EnableScheduling
@EnableWebMvc
public class CheckinConfig {

    @Value("${checkin.xml.file}")
    private String xmlFile;

    @Value("${checkin.xml.file.encoding}")
    private String xmlFileEncoding;

    @Value("${checkin.xml.date.format}")
    private String xmlDateFormat;

    public String getXmlFile() {
        return xmlFile;
    }

    protected void setXmlFile(String xmlFile) {
        this.xmlFile = xmlFile;
    }

    public String getXmlFileEncoding() {
        return xmlFileEncoding;
    }

    protected void setXmlFileEncoding(String xmlFileEncoding) {
        this.xmlFileEncoding = xmlFileEncoding;
    }

    public String getXmlDateFormat() {
        return xmlDateFormat;
    }

    protected void setXmlDateFormat(String xmlDateFormat) {
        this.xmlDateFormat = xmlDateFormat;
    }

    @PostConstruct
    private void loadConfig() throws IOException {
        if (!CharEncoding.isSupported(xmlFileEncoding)) {
            throw new UnsupportedEncodingException(xmlFileEncoding);
        }

        // throws IllegalArgumentException if format is incorrect
        new SimpleDateFormat(xmlDateFormat);
    }
}
