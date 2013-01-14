package com.tricode.checkin.task;

import com.tricode.checkin.CheckinConfig;
import com.tricode.checkin.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PersonXmlLoaderTask {

    private final CheckinConfig checkinConfig;
    private final PersonService personService;

    @Autowired
    public PersonXmlLoaderTask(CheckinConfig checkinConfig, PersonService personService) {
        this.checkinConfig = checkinConfig;
        this.personService = personService;
    }

//    @Scheduled(cron = "0 3 * * * ?")
    @Scheduled(cron = "5 * * * * ?")
    public void load() {
        try {
            checkinConfig.getXmlFile();
        } catch (Exception e) {
            reSchedule();
        }
    }

    private void reSchedule() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                load();
            }
        });
    }
}
