package org.miejski.controller;

import org.miejski.domain.Group;
import org.miejski.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/group")
public class GroupsController {

    @Autowired
    private GroupService groupService;

    @RequestMapping(method = RequestMethod.GET)
    public Group getGroup(@RequestParam("id") String userId) {
        return groupService.getAssignedGroup(userId);
    }
}
