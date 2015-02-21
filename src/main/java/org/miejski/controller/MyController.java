package org.miejski.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/group")
public class MyController {

    @RequestMapping(method = RequestMethod.GET)
    public String getGroup(@RequestParam("id") String id) {
        return id;
    }

}
