package com.tricode.checkin.web;

import com.tricode.checkin.task.RunnableTaskMetadata;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@RequestMapping("rest/task")
public interface RestTaskInterface {

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    Collection<RunnableTaskMetadata> list();

    @RequestMapping(value = "run", method = RequestMethod.POST)
    @ResponseBody
    boolean run(@RequestBody RunnableTaskMetadata taskMetadata);
}
