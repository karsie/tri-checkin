package com.tricode.checkin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PanelController {

    @RequestMapping("index")
    public String index() {
        return "panel";
    }

}
