package top.aries.kind.controller;

import top.aries.kind.model.User;
import top.aries.kind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2017/8/16.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int addUser(User user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/all/{pageNum}/{pageSize}", produces = {"application/json;charset=UTF-8"})
    public Object findAllUser(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize) {

        return userService.findAllUser(pageNum, pageSize);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, /*consumes = "application/json", */produces = {"application/json;charset=UTF-8"})
    public User getUserById(@PathVariable("id") long id) {
        return userService.getUserById(id);
    }


    @ResponseBody
    @RequestMapping(value = "/", produces = {"application/json;charset=UTF-8"})
    public String hello() {
        return "hello,kindaries!";
    }
}